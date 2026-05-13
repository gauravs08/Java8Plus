package Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AccountingProcessor {

    private AccountingProcessor() {
    }

    public static Map<String, Balance> calculateBalances(List<Transaction> transactions) {

        Map<String, Balance> result = new HashMap<>();

        for (Transaction tx : transactions) {

            result.computeIfAbsent(tx.account(), k -> new Balance())
                  .add(tx.type(), tx.amount());
        }

        return result;
    }
}
