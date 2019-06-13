package jugistanbul.tdd.transactions;

import java.util.Objects;

import static java.lang.String.format;

public class PurchasingAgent {

    private final String firstname;

    private final String lastname;

    private final String email;

    private PurchasingAgent(String firstname, String lastname, String email) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getFullname() {
        return format("%s %s", firstname, lastname);
    }

    public String getEmail() {
        return email;
    }

    public Transaction.Builder buy(Product product) {
        return Transaction.aNew().product(product).agent(this);
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


    public static Builder aNew() {
        return new Builder();
    }

    public static class Builder {

        private String firstname;

        private String lastname;

        private String email;

        private Builder() {

        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public PurchasingAgent get() {

            if (firstname == null) {
                throw new PropertyRequiredException("PurchasingAgent", "firstname");
            } else if (lastname == null) {
                throw new PropertyRequiredException("PurchasingAgent", "lastname");
            } else if (email == null) {
                throw new PropertyRequiredException("PurchasingAgent", "email");
            }

            return new PurchasingAgent(firstname, lastname, email);
        }
    }
}
