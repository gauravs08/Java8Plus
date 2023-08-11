package java8.features.Stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamExample {

    public static void main(String args[]) {

        StreamExample se = new StreamExample();
        List<Integer> numbers = new ArrayList();

        for (int i = 0; i < 100; i++)
            numbers.add(i);

        System.out.println(se.sumIterator(numbers));
        System.out.println(se.sumStreamLambda(numbers));
        se.findOddCollections(numbers).forEach(System.out::println);

        se.countEvenPairsInMap(new HashMap<>());
    }


    //Traditional Way
    public static int sumIterator(List<Integer> list) {
        Iterator<Integer> it = list.iterator();
        int sum = 0;
        while (it.hasNext()) {
            int num = it.next();
            if (num > 10) {
                sum += num;
            }
        }
        return sum;
    }

    public static int sumStreamLambda(List<Integer> list) {
        return list.stream()
                .filter(i -> i > 10)
                .mapToInt(i -> i)
                .sum();
    }

    public static List<Integer> findOddCollections(List<Integer> list) {

        List<Integer> result = list.stream()
                .filter(i -> (i % 2 != 0))
                .collect(Collectors.toList());

        return result;
    }

    public static int countEvenPairsInMap(Map<String, Integer> hm) {
        //see Class com.HackerRank.WarmUpChallenges.SockMerchant.java for more details

        String[] arItems = {"1", "2", "2", "1", "1", "3", "5", "1", "2"};

        hm.put("RED", 1);
        hm.put("Yellow", 3);
        hm.put("Green", 4);
        hm.put("Purple", 2);
        hm.put("Orange", 5);


        int sum = hm.entrySet().stream()
                .filter(m -> m.getValue() > 1)
                .map(m -> m.getValue() / 2)
                .mapToInt(i -> i)
                .sum();
        System.out.println("No of Even Pair Socks are:: " + sum);
        return sum;
    }
}

