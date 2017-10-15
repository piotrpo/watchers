# Watchers
This is annotation processing library aimed to auto generate observable wrappers for pojo classes. 

## Ussage
All you need to do is just place the `@observe` annotation one element you want to observe - whole class, set* method
or class field (must have public setter). Then all you have to do is just use one of generated classes:

```java
public class Main
{
    public static void main(String[] args)
    {
        PersonObservable personObservable = new PersonObservable();
        personObservable.setListener(() -> System.out.println("Person data was changed"));
        personObservable.setFirstName("Daenerys");
        personObservable.setLastName("Targaryen");
        System.out.println(personObservable);

        Person person = new Person();
        PersonWrapped personWrapped = new PersonWrapped(person);
        personWrapped.setListener(() -> System.out.println("Wrapperd person object data changed"));
        personWrapped.setFirstName("John");
        personWrapped.setLastName("Snow");

        System.out.println(person.toString());
    }
}
```