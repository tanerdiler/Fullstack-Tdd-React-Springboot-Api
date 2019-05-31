package jugistanbul.tdd.transactions;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static jugistanbul.tdd.transactions.TransactionState.APPROVED;

public class Accounting {

    private Map<PurchasingAgent, List<Transaction>> agentTransactions = new HashMap<>();

    private final AtomicInteger idGenerator = new AtomicInteger(0);

    private Money limit;

    public Accounting(Money limit) {
        this.limit = limit;
    }

    public boolean save(Transaction trx) {
        var agent = trx.getAgent();
        var totalPriceOfApprovedTransactions = calcTotalPriceOfAgentTransactions(agent);

        if (totalPriceOfApprovedTransactions.plus(trx.getPrice()).isGreaterThan(limit)) {
            trx.unapprove();
        } else {
            trx.approve();
        }

        var transactions = agentTransactions.getOrDefault(trx.getAgent(), new ArrayList<>());
        transactions.add(trx);

        // TODO : setId brakes Immutable
        trx.setId(idGenerator.addAndGet(1));

        agentTransactions.putIfAbsent(agent, transactions);

        return trx.getState() == APPROVED;
    }

    public Money calcTotalPriceOfAgentTransactions(PurchasingAgent agent) {
        var transactions = agentTransactions.getOrDefault(agent, new ArrayList<>());
        var totalPrice = transactions.stream().filter(trx->APPROVED == trx.getState()).map(trx->trx.getPrice()).reduce(Money.of(0d), (m1, m2)->m1.plus(m2));
        return totalPrice;
    }

    public Map<PurchasingAgent, List<Transaction>> getTransactions() {
        return Collections.unmodifiableMap(agentTransactions);
    }

    public  Map<PurchasingAgent, List<Transaction>> getTransactionsOf(PurchasingAgent agent) {
        var result = new HashMap<PurchasingAgent, List<Transaction>>();
        var transactions = agentTransactions.getOrDefault(agent, new ArrayList<>());
        result.put(agent, transactions);
        return result;
    }
}
