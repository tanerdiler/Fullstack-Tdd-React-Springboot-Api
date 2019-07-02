package jugistanbul.tdd.port.rest.controller;

import jugistanbul.tdd.transactions.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSaveResponse {
    private Integer id;
    private TransactionState state;
}
