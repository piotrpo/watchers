package pl.com.digita.watchers.example;

import pl.com.digita.watchers.library.annotations.Observe;

@Observe
public class ClassLevel
{
    private String classLevelField1;
    private int classLevelField2;

    public String getClassLevelField1()
    {
        return classLevelField1;
    }

    public void setClassLevelField1(String classLevelField1)
    {
        this.classLevelField1 = classLevelField1;
    }

    public int getClassLevelField2()
    {
        return classLevelField2;
    }

    public void setClassLevelField2(int classLevelField2)
    {
        this.classLevelField2 = classLevelField2;
    }
}
