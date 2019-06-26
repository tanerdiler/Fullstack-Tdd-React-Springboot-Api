package jugistanbul.tdd.transactions;

public class Transaction {

    private PurchasingAgent agent;

    private Product product;

    private boolean approved;

    public Transaction(PurchasingAgent agent, Product product) {
        this.agent = agent;

        this.product = product;
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
}
