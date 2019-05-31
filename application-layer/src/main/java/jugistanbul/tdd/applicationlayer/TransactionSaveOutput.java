package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.TransactionState;

public class TransactionSaveOutput {

    private Integer transactionId;

    private TransactionState state;

    public TransactionSaveOutput(Integer transactionId, TransactionState state) {

        this.transactionId = transactionId;
        this.state = state;
    }

    public TransactionState getState() {
        return state;
    }

    public Integer getTransactionId() {
        return transactionId;
    }
}
