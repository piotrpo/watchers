/*
 * Copyright (c) 2017. Piotr Potulski
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.com.digita.watchers.processor;

import com.google.auto.service.AutoService;
import pl.com.digita.watchers.library.annotations.Observe;
import pl.com.digita.watchers.processor.model.ClassModel;
import pl.com.digita.watchers.processor.model.SetterModel;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("pl.com.digita.watchers.library.annotations.Observe")
@AutoService(Processor.class)

public class WatchersProcessor extends AbstractProcessor
{

    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    private int counter = 0;

    /**
     * Initializes the processor with the processing environment by
     * setting the {@code processingEnv} field to the value of the
     * {@code processingEnv} argument.  An {@code
     * IllegalStateException} will be thrown if this method is called
     * more than once on the same object.
     *
     * @param processingEnv environment to access facilities the tool framework
     *                      provides to the processor
     * @throws IllegalStateException if this method is called more than once.
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        if (annotations.isEmpty())
        {
            return true;
        }

        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Observe.class);
        Map<String, ClassModel> annotatedClassesMap = annotatedElements
                .stream()
                .map(o -> new ClassModel(getClassElement(o).toString()))
                .distinct()
                .collect(Collectors.toMap(ClassModel::toString, o -> o));


        //createLog(annotatedClassesMap.values().stream().map(ClassModel::getClassName).collect(Collectors.toList()));

        for (Element annotatedElement : annotatedElements)
        {
            switch (annotatedElement.getKind())
            {

                case CLASS:
                case INTERFACE:
                    processClassElement(annotatedElement, annotatedClassesMap);
                    break;

                case FIELD:
                    processFieldElement(annotatedElement, annotatedClassesMap);
                    break;

                case METHOD:
                    processMethodElement(annotatedElement, annotatedClassesMap);
                    break;
            }
        }


        //createLog(annotatedClassesMap.values().stream().flatMap(classModel -> classModel.getSetters().stream()).map(SetterModel::toString).collect(Collectors.toList()));
        for (ClassModel classModel : annotatedClassesMap.values())
        {
            try
            {
                produceClass(classModel, filer);
            }
            catch (IOException e)
            {
                claimError(null, "error generating class " + classModel.toString());
                e.printStackTrace();
            }
        }
        return true;
    }

    private void processMethodElement(Element annotatedElement, Map<String, ClassModel> annotatedClassesMap)
    {
        ExecutableElement executableElement = (ExecutableElement) annotatedElement;
        //validate
        if (!executableElement.getSimpleName().toString().startsWith("set"))
        {
            claimError(annotatedElement, "This method is not a setter");
        }

        if (executableElement.getParameters().size() != 1)
        {
            claimError(annotatedElement, "Setters must have exaclty one parameter");
        }

        String className = getClassElement(annotatedElement).toString();
        ClassModel classModel = annotatedClassesMap.get(className);
        if (classModel == null)
        {
            claimError(annotatedElement, "Element not found %s", className);
            return;
        }
        classModel
                .getSetters()
                .add(new SetterModel(executableElement.getSimpleName().toString(),
                        executableElement.getParameters().get(0).asType().toString()));
    }

    private void processFieldElement(Element annotatedElement, Map<String, ClassModel> annotatedClassesMap)
    {
        VariableElement variableElement = (VariableElement) annotatedElement;

        //find  setter
        TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();

        String setterName = fieldToSetterName(variableElement);
        List<? extends Element> setters = classElement.getEnclosedElements().stream()
                .filter(o -> o.getKind() == ElementKind.METHOD)
                .filter(o -> o.getSimpleName().toString().equals(setterName))
                .collect(Collectors.toList());

        if (setters.size() != 1)
        {
            claimError(annotatedElement, "Setter not found " + setterName);
        }
        processMethodElement(setters.get(0), annotatedClassesMap);
    }

    private String fieldToSetterName(VariableElement variableElement)
    {
        String simpleName = variableElement.getSimpleName().toString();

        return "set" + simpleName.substring(0, 1).toUpperCase() + simpleName.substring(1);
    }

    private String setterToFieldName(ExecutableElement executableElement)
    {
        String setterName = executableElement.getSimpleName().toString();
        setterName = setterName.replace("set", "");
        return setterName.substring(0, 1).toLowerCase() + setterName.substring(1);
    }


    private void processClassElement(Element annotatedElement, Map<String, ClassModel> annotatedClassesMap)
    {
        Element classElement = getClassElement(annotatedElement);

        classElement.getEnclosedElements().stream()
                .filter(o -> o.getKind() == ElementKind.METHOD)
                .filter(o -> o.getSimpleName().toString().startsWith("set"))
                .filter(o -> ((ExecutableElement) o).getParameters().size() == 1)
                .forEach(o -> processMethodElement(o, annotatedClassesMap));
    }

    private void createLog(List<String> strings)
    {
        Stream<String> stringStream = strings.stream();


        try
        {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "pl.com.digita.resources", "" + counter++ + "processor_output.txt");
            OutputStream outputStream = fileObject.openOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            stringStream.forEach(
                    printWriter::println
            );
            printWriter.flush();
            printWriter.close();
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            claimError(null, "Error writing");
            throw new RuntimeException("Error generating class", e);
        }
    }

    private void claimError(Element element, String message, Object... args)
    {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, args), element);
    }

    private Element getClassElement(Element element)
    {
        if (element.getKind().isClass())
        {
            return element;
        }
        else
        {
            return getClassElement(element.getEnclosingElement());
        }
    }

    private void produceClass(ClassModel classModel, Filer filer) throws IOException
    {
        String producedClassName = classModel.getClassName() + "Observed";
        JavaFileObject sourceFile = filer.createSourceFile(classModel.getClassPackage() + "." + producedClassName);

        PrintWriter writer = new PrintWriter(sourceFile.openOutputStream());
        FilerUtils src = new FilerUtils(writer);
        int ident = 0;

        src.pl(ident, "package %s;", classModel.getClassPackage());
        src.ln();
        src.pl(ident, "import pl.com.digita.watchers.library.listeners.Observer;");
        src.ln();
        src.pl(ident, "public class %s extends  %s {", producedClassName, classModel.getClassName());
        src.ln();
//        src.pl(++ident, "private %s observed;", classModel.getClassName());
        src.pl(ident, "private Observer listener;");

        String listeners = "public Observer getListener()\n" +
                "{\n" +
                "    return listener;\n" +
                "}\n" +
                "\n" +
                "public void setListener(Observer listener)\n" +
                "{\n" +
                "    this.listener = listener;\n" +
                "}";
        src.pl(ident, listeners);

//        src.pl(ident, "public  %s (%s observed){", producedClassName, classModel.getClassName());
//        src.pl(++ident, "this.observed = observed;");
//        src.pl(--ident, "}");

        src.pl(ident, "private void notifyDataChanged(){");
        src.pl(++ident, "listener.dataChanged();");
        src.pl(--ident, "}");
        src.ln();

        //generate setters
        for (SetterModel setterModel : classModel.getSetters())
        {
            src.pl(ident, "public void %s(%s value){", setterModel.getSetterName(), setterModel.getParameterType());
            src.pl(++ident, "super.%s(value);", setterModel.getSetterName());
            src.pl(ident, "notifyDataChanged();");
            src.pl(--ident, "}");
            src.ln();
        }

        src.pl(0, "}");

        src.writer.flush();
        src.writer.close();
    }

    private static class FilerUtils
    {

        private final PrintWriter writer;

        FilerUtils(PrintWriter writer)
        {
            this.writer = writer;
        }

        void pl(int level, String string, Object... args)
        {
            String ident = new String(new char[level * 4]).replace("\0", " ");
            writer.println(ident + String.format(string, args));
        }

        void ln()
        {
            writer.println();
        }
    }
}
