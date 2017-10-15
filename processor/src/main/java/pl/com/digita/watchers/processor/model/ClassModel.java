package pl.com.digita.watchers.processor.model;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClassModel
{
    private String classPackage;
    private String className;
    private Set<SetterModel> fields = new HashSet<>();


    public ClassModel(String fullName)
    {
        int lastDot = fullName.lastIndexOf('.');

        this.classPackage = fullName.substring(0, lastDot);
        this.className = fullName.substring(lastDot + 1);
    }

    public String getClassPackage()
    {
        return classPackage;
    }

    public void setClassPackage(String classPackage)
    {
        this.classPackage = classPackage;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public Set<SetterModel> getSetters()
    {
        return fields;
    }

    public void setFields(Set<SetterModel> fields)
    {
        this.fields = fields;
    }

    @Override
    public String toString()
    {
        return classPackage + "." + className;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ClassModel)) return false;
        ClassModel that = (ClassModel) o;
        return Objects.equals(classPackage, that.classPackage) &&
                Objects.equals(className, that.className) &&
                Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(classPackage, className, fields);
    }
}
