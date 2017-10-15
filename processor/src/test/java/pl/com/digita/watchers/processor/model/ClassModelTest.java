package pl.com.digita.watchers.processor.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassModelTest
{
    @Test
    public void getClassName() throws Exception
    {
        String pckg = "pl.com.digita.test";
        String name = "Test";
        String fullName = pckg + "." + name;
        ClassModel classModel = new ClassModel(fullName);
        assertEquals(pckg, classModel.getClassPackage());
        assertEquals(name, classModel.getClassName());
        assertEquals(fullName, classModel.toString());
    }

}