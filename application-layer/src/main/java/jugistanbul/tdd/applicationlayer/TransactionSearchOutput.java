package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.PurchasingAgent;
import jugistanbul.tdd.transactions.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionSearchOutput {

    private Map<PurchasingAgent, List<Transaction>> transactions;

    public TransactionSearchOutput(Map<PurchasingAgent, List<Transaction>> transactions) {

        this.transactions = transactions;
    }

    public Map<PurchasingAgent, List<Transaction>> getTransactions() {
        return this.transactions;
    }

    public int getAgentsCount() {
        return transactions.size();
    }

    public int getTransactionsCount() {
        return transactions.values().stream().mapToInt(l->l.size()).sum();
    }
}
