package pl.com.digita.watchers.processor.model;

import java.util.Objects;

public class SetterModel
{
    private String setterName;
    private String parameterType;

    public SetterModel(String setterName, String parameterType)
    {
        this.setterName = setterName;
        this.parameterType = parameterType;
    }

    public String getSetterName()
    {
        return setterName;
    }

    public void setSetterName(String setterName)
    {
        this.setterName = setterName;
    }

    public String getParameterType()
    {
        return parameterType;
    }

    public void setParameterType(String parameterType)
    {
        this.parameterType = parameterType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof SetterModel)) return false;
        SetterModel that = (SetterModel) o;
        return Objects.equals(setterName, that.setterName) &&
                Objects.equals(parameterType, that.parameterType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(setterName, parameterType);
    }


    @Override
    public String toString()
    {
        return setterName + " (" + parameterType + ")";
    }
}
