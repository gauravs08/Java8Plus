package java8.ExceptionHandling;

import java.util.function.BiConsumer;

public class ExceptionhandlingBasics {

    public static void main(String[] args) {
        //https://www.youtube.com/watch?v=YLKMCPMLv60&list=PLqq-6Pq4lTTa9YGfyhyW2CqdtW9RtY-I3&index=18
        int[] big = {1, 2, 3, 4};
        int small = 2; //try 0

        //process(big,small,(v,k) -> System.out.println(v/k) );


        // ::ONE:: way to add try catch in  lambda block {}
        process(big, small, (v, k) -> {
            try {
                System.out.println(v / k);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }); // this make lambda expression bulky with try catch block instead we can separate out try catch in wrapper lambda

        System.out.println("Implementing Wrapper lamba and overriding operation ");

        // ::TWO:: 2nd way to implement is by using wrapper lambda means lambda inside lambda
        small = 0;
        process(big, small, wrapperLambda((v, k) -> System.out.println(v / k)));


    }

    private static BiConsumer<Integer, Integer> wrapperLambda(BiConsumer<Integer, Integer> biCon) {
        //return (v,k) ->	System.out.println(v*k); // override the action with other action

        return (v, k) -> {
            try {

                System.out.println("Entering Wrapper");
                System.out.println(v / k);

            } catch (ArithmeticException e) {
                System.out.println("Failed to perform due to :: " + e.getMessage());

            }
        };


    }

    private static void process(int[] big, int small, BiConsumer<Integer, Integer> biCon) {
        for (int i : big) {
            biCon.accept(i, small);
        }

    }

}
