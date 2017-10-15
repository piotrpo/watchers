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

package pl.com.digita.watchers.example;

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
