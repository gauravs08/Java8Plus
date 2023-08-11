package java8.features.Stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TermincalOperationStream {

    public static void main(String[] args) {

        //-------- stream.reduce()---------------------------
        //1 | reduce ::
		/*reduce() to perform a reduction on the elements of the stream, 
		 * using an associative accumulation function, and return an Optional. 
		 * Let�s see how we can use it multiply the integers in a stream.
		Copy*/
        System.out.println("--reduce()--");
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);

        Optional<Integer> intOptional = numbers
                .reduce((i, j) -> i * j);
        if (intOptional.isPresent()) System.out.println("Multiplication of all elements = " + intOptional.get()); //120

        //2 | To get the longest string from list of string
        System.out.println("---Longest String in Array list::----");
        List<String> words = Arrays.asList("GFG", "Geeks", "for",
                "GeeksQuiz", "GeeksforGeeks");

        // The lambda expression passed to reduce() method takes two Strings
        // and returns the the longer String. The result of the reduce() method is 
        // an Optional because the list on which reduce() is called may be empty. 
        Optional<String> longestString = words
                .stream()
                .reduce((word1, word2) -> word1.length() > word2.length() ? word1 : word2);

        // Displaying the longest String 
        longestString.ifPresent(System.out::println);

        String[] welcome = {"Welcome", "Gaurav"};
        Optional<String> String_combine = Arrays.stream(welcome)
                .reduce((str1, str2) -> str1 + "-" + str2);
        if (String_combine.isPresent()) {
            System.out.println(String_combine.get());
        }
        //String_combine.ifPresent(System.out::println);

        // 3| reduce = to get sum of all Intstream

        List<Integer> array = Arrays.asList(-2, 0, 4, 6, 8);

        // Finding sum of all elements 
        int sum = array.stream().reduce(0,
                (element1, element2) -> element1 + element2);
        System.out.println("Sum of all numbers in Array:: " + sum);


        int positiveSum = array.parallelStream()
                .filter(n -> n > 0)
                .reduce(0, (e1, e2) -> e1 + e2);
        System.out.println("Sum of all elements greater then 0 ::" + positiveSum);

        // 4| To get range summation from start to end

        // To get the product of all elements 
        // in given range excluding the rightmost element 
        int rangeSum = IntStream.range(2, 5)
                .reduce((num1, num2) -> num1 + num2)
                .orElse(-1);

        // Displaying the sum of particular range 
        System.out.println("The Sum between range is : " + rangeSum);


        //----------------count()----------
        Stream<Integer> numbers1 = Stream.of(1, 2, 3, 5);

        System.out.println("Number of elements in stream=" + numbers1.count()); //5

        //---------forEach in Stream--------

        List<Integer> list = Arrays.asList(2, 4, 6, 8, 10);

        // Using forEach(Consumer action) to 
        // print the elements of stream in reverse order 
        list.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
        //or
        List<Integer> naturalSortLt = list.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(naturalSortLt);

        // Creating a Stream of Strings 
        Stream<String> stream = Stream.of("GFG", "Geeks",
                "for", "GeeksforGeeks");

        // Using forEach(Consumer action) to print 
        // Character at index 1 in reverse order 
        stream.sorted(Comparator.reverseOrder()) //First reverse sorting 
                .flatMap(str -> Stream.of(str.charAt(1))) // take only 1st index of rev sorted strings
                .forEach(System.out::println);

        //----------match() operation
        Stream<Integer> numbers3 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream contains 4? " + numbers3.anyMatch(i -> i == 4));
        //Stream contains 4? true

        Stream<Integer> numbers4 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream contains all elements less than 10? " + numbers4.allMatch(i -> i < 10));
        //Stream contains all elements less than 10? true

        Stream<Integer> numbers5 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream doesn't contain 10? " + numbers5.noneMatch(i -> i == 10));
        //Stream doesn't contain 10? true


        //---------- findFirst()----------- short circuiting Terminal operation
        Stream<String> names4 = Stream.of("Pankaj", "Amit", "David", "Lisa");
        Optional<String> firstNameWithD = names4
                .filter(i -> i.startsWith("D"))
                .findFirst();
        if (firstNameWithD.isPresent()) {
            System.out.println("First Name starting with D=" + firstNameWithD.get()); //David
        }
        //firstNameWithD.ifPresent(System.out::println);
    }

}
