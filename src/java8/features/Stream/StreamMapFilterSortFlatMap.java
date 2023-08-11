package java8.features.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMapFilterSortFlatMap {

    public static void main(String[] args) {

        List<Integer> myList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            myList.add(i);

        Stream<Integer> sequentialStream = myList.stream();

        Stream<Integer> highNums = sequentialStream
                .filter(p -> p > 90); // filter numbers greater than 90

        System.out.println("High Nums greater than 90 =");

        highNums.forEach(p -> System.out.print(p + " "));


        //2 |
        System.out.println("\n-- Stream .map --");

        Stream<String> names = Stream.of("aBc", "d", "ef");

        List<String> lt = (List<String>) names
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());

        System.out.println(lt);

        // 3 |
        System.out.println("-- Sorting --");
        Stream<String> names2 = Stream.of("aBc", "d", "ef", "123456");

        List<String> reverseSorted = names2
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(reverseSorted); // [ef, d, aBc, 123456]

        Stream<String> names3 = Stream.of("aBc", "d", "ef", "123456");

        List<String> naturalSorted = names3
                .sorted()
                .collect(Collectors.toList());

        System.out.println(naturalSorted); //[123456, aBc, d, ef]
        // 4 | // To iterate over Stream of Stream
        System.out.println("--flatMap--");
        Stream<List<String>> namesOriginalList = Stream.of(
                Arrays.asList("Pankaj", "Soni"),
                Arrays.asList("David", "Lisa"),
                Arrays.asList("Amit", "Roy"));
        //flat the stream from List<String> to String stream

        Stream<String> flatStream = namesOriginalList
                .flatMap(strList -> strList.stream());

        flatStream.forEach(System.out::println);
    }

}
