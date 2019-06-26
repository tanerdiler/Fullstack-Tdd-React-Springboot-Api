package jugistanbul.tdd.transactions;

public class Transaction {

    private PurchasingAgent agent;

    private Product product;

    private String code;

    private boolean approved;

    private Transaction(PurchasingAgent agent, Product product, String code) {
        this.agent = agent;
        this.product = product;
        this.code = code;
    }

    public double getPrice() {
        return product.getPrice();
    }

    public boolean isApproved() {
        return approved;
    }

    public void beApproved() {
        this.approved = true;
    }

    public void beDenied() {
        this.approved = false;
    }

    public PurchasingAgent getAgent() {
        return agent;
    }

    public String getCode() {
        return code;
    }

    public static class TransactionBuilder {

        private PurchasingAgent agent;

        private Product product;

        private String code;

        public TransactionBuilder agent(PurchasingAgent agent) {
            this.agent = agent;
            return this;
        }

        public TransactionBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public TransactionBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public Transaction get() {
            return new Transaction(agent, product, code);
        }
    }

}
