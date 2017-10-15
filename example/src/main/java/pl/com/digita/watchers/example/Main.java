package pl.com.digita.watchers.example;

public class Main
{
    public static void main(String[] args)
    {
        ClassLevel classLevel = new ClassLevel();
        ClassLevelObserved observed = new ClassLevelObserved(classLevel);
        observed.setListener(() -> System.out.println("Data changed"));

        observed.setClassLevelField1("new value");
        observed.setClassLevelField2(1);
    }
}
