package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.Money;
import jugistanbul.tdd.transactions.Product;
import jugistanbul.tdd.transactions.PurchasingAgent;

public class TransactionSaveCommand {

    private final Accounting accounting;

    public TransactionSaveCommand(Accounting accounting) {
        this.accounting = accounting;
    }

    public TransactionSaveOutput exec(TransactionSaveInput input) {
        var agent = PurchasingAgent.aNew()
                                    .firstname(input.getFirstname())
                                    .lastname(input.getLastname())
                                    .email(input.getEmail())
                                    .get();
        var product = Product.aNew()
                              .name(input.getProductName())
                              .price(Money.of(input.getProductPrice()))
                              .get();
        var transaction = agent.buy(product).withCode(input.getTransactionCode()).get();

        accounting.save(transaction);

        return new TransactionSaveOutput(transaction.getId(), transaction.getState());
    }
}
