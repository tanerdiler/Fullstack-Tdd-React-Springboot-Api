package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.PurchasingAgent;
import jugistanbul.tdd.transactions.Transaction;

import java.util.List;
import java.util.Map;

public class TransactionSearchCommand {

    private Accounting accounting;

    public TransactionSearchCommand(Accounting accounting) {
        this.accounting = accounting;
    }

    public TransactionSearchOutput exec(TransactionSearchInput searchInput) {
        Map<PurchasingAgent, List<Transaction>> transactions ;
        if (searchInput.hasAgentToSearch()){
            transactions = accounting.getTransactionsOf(searchInput.getAgentToSearch());
        }
        else {
            transactions = accounting.getTransactions();
        }
        return new TransactionSearchOutput(transactions);
    }
}
