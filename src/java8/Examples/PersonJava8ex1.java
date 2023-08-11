package java8.Examples;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PersonJava8ex1 {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Llory", "Mane", 31),
                new Person("Doni", "More", 32),
                new Person("Paul", "Choey", 34),
                new Person("John", "Taylor", 30),
                new Person("Godin", "Cia", 30),
                new Person("Charle", "Chapplin", 30)
        );

        // Step 1 : 	Sort List by First name.
        Collections.sort(people, (Person o1, Person o2) -> o1.getFirstName().compareTo(o2.getFirstName()));


        // Step 2 : 	Create a method that print all elements in the list.
        //printall(people);
        performConditionally(people, p -> true);

        // Step 3 : 	Create a method that print all elements in list having last element as 'C'.
        System.out.println("\nLast name starting with 'C'");
        //printPersonWithLastNameasC(people);
        performConditionally(people, p -> p.getLastName().startsWith("C"));

        //Step 4 : Condition as behaviour
        System.out.println("\n First and last name start with 'C'");
        performConditionally(people, (Person p) -> p.getFirstName().startsWith("C") && p.getLastName().startsWith("C"));

        //Step 5 : Method with Condition & action as input
        System.out.println("\n Print using Predicate & Consumer interface ");
        ifConditionSatisfyDoAction(people, p -> true, p -> System.out.println(p));

        System.out.println("\n First name start with 'G'");
        ifConditionSatisfyDoAction(people, p -> p.getFirstName().startsWith("G"), p -> System.out.println(p.firstName));

    }


    /*Predicate is function interface under java.util.function
     *and this package has lots of function interface for different type and can be used without creating a interface
     *NOTE: this method accept an list of person and condition and by default prints all the person which satisfy the predicate condition
     *But what if we want to perform some specific operation and don't want to print after condition is satisfied.
     *try Consumer<T> follow ifConditionSatisfyDoAction(List,Condition,Action)  */
    private static void performConditionally(List<Person> people, Predicate<Person> predicate) {
        for (Person p : people) {
            if (predicate.test(p)) {
                System.out.println(p);
            }
        }
    }

    private static void ifConditionSatisfyDoAction(List<Person> people, Predicate<Person> predicate, Consumer<Person> consumer) {
        for (Person p : people) {
            if (predicate.test(p)) {
                consumer.accept(p);
            }
        }
    }

    /*//Condition is functional interface created in 7ex1.java and has only one boolean method test(Person p)
    private static void performConditionally(List<Person> people , Condition condition){
        for(Person p: people){
            if(condition.test(p)) {
                System.out.println(p);
            }
        }
    }*/
    private static void printPersonWithLastNameasC(List<Person> people) {
        for (Person p : people) {
            if (p.getLastName().startsWith("C")) {
                System.out.println(p);
            }
        }

    }

    private static void printall(List<Person> people) {
        for (Person p : people) {
            System.out.println(p);
        }
    }

}
