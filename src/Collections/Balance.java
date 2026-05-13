package Collections;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Balance {
    private double debit;
    private double credit;

    void add(String type, double amount) {
        if ("DEBIT".equalsIgnoreCase(type)) {
            debit += amount;
        } else if ("CREDIT".equalsIgnoreCase(type)) {
            credit += amount;
        }
    }

}
