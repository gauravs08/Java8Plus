package java8.features.Stream;

import java.util.Arrays;
import java.util.List;

public class ObjectFilteringStream {

    public static void main(String[] args) {

        List<Person> persons = Arrays.asList(
                new Person("mkyong", 30),
                new Person("jack", 20),
                new Person("lawrence", 40)
        );

        List<Person> persons1 = persons;

        if (persons == persons1) {
            System.out.println("both are equal");
            System.out.println(persons.hashCode() + " = " + persons1.hashCode());

        } else {
            System.out.println("both are diff");
            System.out.println(persons.hashCode() + " = " + persons1.hashCode());
        }

        String s1 = new String("abc");
        String s2 = s1;// new String("abc");

        if (s1 == s2) {
            System.out.println("both are same");

        } else {
            System.out.println("both are diff");

        }

        Person result1 = persons.stream()                        // Convert to steam
                .filter(x -> "jack".equals(x.getName()))        // we want "jack" only
                .findAny()                                      // If 'findAny' then return found
                .orElse(null);                                  // If not found, return null

        System.out.println(result1.getName());

        Person result2 = persons.stream()
                .filter(x -> "ahmook".equals(x.getName()))
                .findAny()
                .orElse(null);

        System.out.println(result2);

    }


}