package transactions;

import static transactions.TransactionState.APPROVED;
import static transactions.TransactionState.UNAPPROVED;
import static transactions.TransactionState.WAITING_APPROVE;

public class Transaction {

    private Integer id;

    private final PurchasingAgent agent;

    private final Product product;

    private final String code;

    private TransactionState state = WAITING_APPROVE;

    public Transaction(PurchasingAgent agent, Product product, String trxCode) {

        this.agent = agent;
        this.product = product;
        this.code = trxCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Money getPrice() {
        return this.product.getPrice();
    }

    public String getProductName() {
        return this.product.getName();
    }

    public PurchasingAgent getAgent() {
        return this.agent;
    }

    public static Builder aNew() {
        return new Builder();
    }

    public TransactionState getState() {
        return state;
    }

    public void approve() throws IllegalOperationException {
        if (this.state == UNAPPROVED) {
            throw new IllegalOperationException("Already unapproved transaction can't be approved!");
        }
        this.state = APPROVED;
    }

    public void unapprove() throws IllegalOperationException {
        if (this.state == APPROVED) {
            throw new IllegalOperationException("Already approved transaction can't be unapproved!");
        }
        this.state = UNAPPROVED;
    }

    public static class Builder {

        private Product product;

        private String trxCode;

        private PurchasingAgent agent;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder withCode(String trxCode) {
            this.trxCode = trxCode;
            return this;
        }

        public Builder agent(PurchasingAgent purchasingAgent) {
            this.agent = purchasingAgent;
            return this;
        }

        public Transaction get() {
            if (trxCode == null) {
                throw new PropertyRequiredException("Transaction", "code");
            } else
            if (product == null) {
                throw new PropertyRequiredException("Transaction", "product");
            }
            return new Transaction(agent, product, trxCode);
        }
    }
}