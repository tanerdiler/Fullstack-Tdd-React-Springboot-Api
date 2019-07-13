package transactions.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchResponse {
    private Collection<TransactionResponse> transactions = new ArrayList<>();
}