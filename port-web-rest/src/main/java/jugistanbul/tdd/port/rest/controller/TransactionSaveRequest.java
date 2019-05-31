package jugistanbul.tdd.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionSaveRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String productName;
    private Double price;
    private String transactionCode;
}
