package java8.features.Stream;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class HashMapStream {

    public static void main(String[] args) {

        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "a");
        hm.put(2, "b");
        hm.put(3, "c");
        hm.put(4, "b");
        if (hm.containsValue("b")) {
            System.out.println("Map has value");
            for (Entry<Integer, String> entry : hm.entrySet()) {
                System.out.println(entry);
                if (entry.getValue().equals("b")) {
                    System.out.println(entry.getKey());
                }
            }
        }

        //Using Streams

        Stream<Entry<Integer, String>> stream = hm.entrySet().stream();

        stream.filter(i -> i.getValue().equals("b"))  // filter out elements with values as 'b'
                .map(i -> i.getKey())
                .forEach(System.out::println);

        //.mapToInt(i -> i)
        //.collect(Collectors.toList())


    }

}
