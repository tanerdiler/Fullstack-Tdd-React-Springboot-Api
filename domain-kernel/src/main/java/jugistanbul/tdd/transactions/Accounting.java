package jugistanbul.tdd.transactions;

import java.util.ArrayList;
import java.util.List;

public class Accounting {

    private double limit;

    private List<Transaction> transactions = new ArrayList<>();

    public Accounting(double limit) {

        this.limit = limit;
    }

    public boolean save(Transaction transaction) {
        double total = transactions.stream().filter(t->t.isApproved() && t.getAgent().equals(transaction.getAgent())).mapToDouble(t->t.getPrice()).sum();
        if (total+transaction.getPrice()<=limit) {
            transaction.beApproved();
        } else {
            transaction.beDenied();
        }
        transactions.add(transaction);
        return transaction.isApproved();
    }
}
