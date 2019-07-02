package jugistanbul.tdd.applicationlayer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class TransactionSaveInput {

    private String firstname;

    private String lastname;

    private String email;

    private String transactionCode;

    private double productPrice;

    private String productName;

    public TransactionSaveInput() {

    }

    public TransactionSaveInput(String firstname, String lastname, String email, String transactionCode, double productPrice, String productName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.transactionCode = transactionCode;
        this.productPrice = productPrice;
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TransactionSaveInput input = (TransactionSaveInput) o;
        return Double.compare(input.productPrice, productPrice) == 0 && Objects.equals(firstname, input.firstname) && Objects.equals(lastname,
                                                                                                                                     input.lastname)
                && Objects.equals(email, input.email) && Objects.equals(transactionCode, input.transactionCode) && Objects.equals(productName,
                                                                                                                                  input.productName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstname, lastname, email, transactionCode, productPrice, productName);
    }
}
