package jugistanbul.tdd.port.rest.controller;

import jugistanbul.tdd.transactions.PurchasingAgent;
import jugistanbul.tdd.transactions.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentTransactions {
    private PurchasingAgent agent;
    private Collection<Transaction> transactions;
}
