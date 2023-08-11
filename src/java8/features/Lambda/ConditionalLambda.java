package java8.features.Lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ConditionalLambda {
    public static int sumWithCondition(List<Integer> numbers, Predicate<Integer> predicate) {
        return numbers.parallelStream()
                .filter(predicate)
                .mapToInt(i -> i)
                .sum();
    }


    public static void main(String args[]) {

        ConditionalLambda cl = new ConditionalLambda();
        List<Integer> numbers = new ArrayList();

        for (int i = 0; i < 100; i++)
            numbers.add(i);

        //PART1:
        //sum of all numbers
        System.out.println(cl.sumWithCondition(numbers, n -> true));

        //sum of all even numbers
        System.out.println(cl.sumWithCondition(numbers, i -> i % 2 == 0));

        //sum of all numbers greater than 5
        System.out.println(cl.sumWithCondition(numbers, i -> i > 5));


        //PART 2:
        System.out.println("-------------------PART 2----------------------");
        long startTime = System.nanoTime();
        System.out.println(cl.findSquareOfMaxOdd(numbers));
        System.out.println(System.nanoTime() - startTime);

        startTime = System.nanoTime();
        System.out.println(cl.findSquareOfMaxOddLambda(numbers));
        System.out.println(System.nanoTime() - startTime);


    }

    //we need to write a method to find out the maximum odd number in the range 3 to 11 and return square of it.
    //Traditional way
    private static int findSquareOfMaxOdd(List<Integer> numbers) {
        int max = 0;
        for (int i : numbers) {
            if (i % 2 != 0 && i > 3 && i < 11 && i > max) {
                max = i;
            }
        }
        return max * max;
    }


    public static int findSquareOfMaxOddLambda(List<Integer> numbers) {
        List<String> lines = Arrays.asList("spring", "node", "mkyong");

        //:: is the syntactic sugar introduced by Java8, which ConditionalLambda::isGreaterThan3
        //is i -> ConditionalLambda.isGreaterThan3(i)shorthand.
        return numbers.stream()
                .filter(ConditionalLambda::isOdd)        //Predicate is functional interface and
                .filter(ConditionalLambda::isGreaterThan3)    // we are using lambdas to initialize it
                .filter(ConditionalLambda::isLessThan11)    // rather than anonymous inner classes
                .max(Comparator.naturalOrder())
                .map(i -> i * i)
                .get();
    }

    public static boolean isOdd(Integer i) {
        return i % 2 != 0;
    }

    public static boolean isGreaterThan3(Integer i) {
        return i > 3;
    }

    public static boolean isLessThan11(int i) {
        return i < 11;
    }

}

