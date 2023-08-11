package java8.features.Stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ParallelStreamDemo {

    public static int sumWithCondition(List<Integer> numbers, Predicate<Integer> predicate) {
        return numbers.parallelStream()
                .filter(predicate)
                .mapToInt(i -> i)
                .sum();

    }

    //find out the maximum odd number in the range 3 to 11 and return square of it.
    private static int findSquareOfMaxOddJava7(List<Integer> numbers) {
        int max = 0;
        for (int i : numbers) {
            if (i % 2 != 0 && i > 3 && i < 11 && i > max) {
                max = i;
            }
        }
        return max * max;
    }

    public static void main(String[] args) {
        ParallelStreamDemo obj = new ParallelStreamDemo();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 12, 13);


        //sum of all numbers
        System.out.println(obj.sumWithCondition(numbers, n -> true));
        //sum of all even numbers
        System.out.println(obj.sumWithCondition(numbers, i -> i % 2 == 0));
        //sum of all numbers greater than 5
        System.out.println(obj.sumWithCondition(numbers, i -> i > 5));


        System.out.println("FindSquare of MaxOdd Java 7:: " + obj.findSquareOfMaxOddJava7(numbers));
        System.out.println("FindSquare of MaxOdd Java 8:: " + obj.findSquareOfMaxOdd(numbers));
    }


    public static int findSquareOfMaxOdd(List<Integer> numbers) {
        return numbers.stream()
                .filter(ParallelStreamDemo::isOdd)            //Predicate is functional interface and
                .filter(ParallelStreamDemo::isGreaterThan3)    // we are using lambdas to initialize it
                //.filter(i -> i>3)
                .filter(ParallelStreamDemo::isLessThan11)    // rather than anonymous inner classes
                .max(Comparator.naturalOrder())
                .map(i -> i * i)
                .get();
    }

    public static boolean isOdd(int i) {
        return i % 2 != 0;
    }

    public static boolean isGreaterThan3(int i) {
        return i > 3;
    }

    public static boolean isLessThan11(int i) {
        return i < 11;
    }
}
