package pl.com.digita.watchers.example;

import pl.com.digita.watchers.library.annotations.Observe;

public class FieldLevel
{
    @Observe
    private String fieldLevelField1;

    @Observe
    private int fieldLevelField2;

    public String getFieldLevelField1()
    {
        return fieldLevelField1;
    }

    public void setFieldLevelField1(String fieldLevelField1)
    {
        this.fieldLevelField1 = fieldLevelField1;
    }

    public int getFieldLevelField2()
    {
        return fieldLevelField2;
    }

    public void setFieldLevelField2(int fieldLevelField2)
    {
        this.fieldLevelField2 = fieldLevelField2;
    }
}
