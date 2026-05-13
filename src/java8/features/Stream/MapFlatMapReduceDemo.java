package java8.features.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapFlatMapReduceDemo {

    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
                new Order("ORD-1", Arrays.asList(100, 250, 50)),
                new Order("ORD-2", Arrays.asList(80, 120)),
                new Order("ORD-3", Arrays.asList(300))
        );

        mapExample(orders);
        flatMapExample(orders);
        reduceExample(orders);
    }

    private static void mapExample(List<Order> orders) {
        List<String> orderIds = orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        List<List<Integer>> lineAmountsByOrder = orders.stream()
                .map(Order::getLineAmounts)
                .collect(Collectors.toList());

        System.out.println("-- map --");
        System.out.println("One input becomes one output.");
        System.out.println("Order ids: " + orderIds);
        System.out.println("Mapped line amounts still nested: " + lineAmountsByOrder);
    }

    private static void flatMapExample(List<Order> orders) {
        List<Integer> allLineAmounts = orders.stream()
                .flatMap(order -> order.getLineAmounts().stream())
                .collect(Collectors.toList());

        System.out.println("\n-- flatMap --");
        System.out.println("One input can become many outputs, then all outputs are flattened.");
        System.out.println("All line amounts in one list: " + allLineAmounts);
    }

    private static void reduceExample(List<Order> orders) {
        int totalAmount = orders.stream()
                .flatMap(order -> order.getLineAmounts().stream())
                .reduce(0, Integer::sum);

        int highestSingleLineAmount = orders.stream()
                .flatMap(order -> order.getLineAmounts().stream())
                .reduce(Integer.MIN_VALUE, Integer::max);

        System.out.println("\n-- reduce --");
        System.out.println("Many input values are combined into one final result.");
        System.out.println("Total amount: " + totalAmount);
        System.out.println("Highest single line amount: " + highestSingleLineAmount);
    }

    private static class Order {
        private final String id;
        private final List<Integer> lineAmounts;

        private Order(String id, List<Integer> lineAmounts) {
            this.id = id;
            this.lineAmounts = lineAmounts;
        }

        private String getId() {
            return id;
        }

        private List<Integer> getLineAmounts() {
            return lineAmounts;
        }
    }
}
