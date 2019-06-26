package jugistanbul.tdd.transactions;

import jugistanbul.tdd.transactions.Transaction.TransactionBuilder;

import java.lang.module.ModuleDescriptor;
import java.net.CookieHandler;
import java.util.Objects;

public class PurchasingAgent {

    private final String firstname;

    private final String lastname;

    private final String email;

    private PurchasingAgent(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public static PurchasingAgentBuilder aNew() {
        return new PurchasingAgentBuilder();
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

    public TransactionBuilder buy(Product product) {
        return new TransactionBuilder().agent(this).product(product);
    }

    public static class PurchasingAgentBuilder {

        private String firstname;

        private String lastname;

        private String email;

        public PurchasingAgentBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public PurchasingAgentBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public PurchasingAgentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PurchasingAgent get() {
            return new PurchasingAgent(firstname, lastname, email);
        }
    }
}
