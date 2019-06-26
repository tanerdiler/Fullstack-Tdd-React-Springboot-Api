package jugistanbul.tdd.transactions;

import java.util.Objects;

public class PurchasingAgent {

    private final String firstname;

    private final String lastname;

    private final String email;

    public PurchasingAgent(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PurchasingAgent that = (PurchasingAgent) o;
        return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstname, lastname, email);
    }

    public Transaction buy(Product product) {
        var trx = new Transaction(this, product);
        return trx;
    }
}
