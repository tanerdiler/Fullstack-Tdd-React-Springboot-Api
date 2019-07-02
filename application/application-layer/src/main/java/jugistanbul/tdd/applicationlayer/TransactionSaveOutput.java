package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.TransactionState;
import lombok.Data;

@Data
public class TransactionSaveOutput {

    private Integer transactionId;

    private TransactionState state;

    public TransactionSaveOutput(Integer transactionId, TransactionState state) {

        this.transactionId = transactionId;
        this.state = state;
    }

}
