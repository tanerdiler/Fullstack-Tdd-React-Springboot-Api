package transactions.port.rest.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionSaveRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String productName;
    private Double price;

    public TransactionSaveRequest(String firstname, String lastname, String email, String productName, Double price, String transactionCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.productName = productName;
        this.price = price;
        this.transactionCode = transactionCode;
    }

    private String transactionCode;
}