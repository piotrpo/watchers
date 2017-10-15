package pl.com.digita.watchers.example;

import pl.com.digita.watchers.library.annotations.Observe;

public class MethodLevel
{
    private String methodLevelField1;
    private int methodLevelField2;

    public String getMethodLevelField1()
    {
        return methodLevelField1;
    }

    @Observe
    public void setMethodLevelField1(String methodLevelField1)
    {
        this.methodLevelField1 = methodLevelField1;
    }

    public int getMethodLevelField2()
    {
        return methodLevelField2;
    }

    @Observe
    public void setMethodLevelField2(int methodLevelField2)
    {
        this.methodLevelField2 = methodLevelField2;
    }
}
