package transactions.port.rest.service;

import org.springframework.stereotype.Component;
import transactions.port.rest.controller.TransactionSaveRequest;
import transactions.port.rest.controller.TransactionSaveResponse;
import transactions.port.rest.controller.TransactionSearchRequest;
import transactions.port.rest.controller.TransactionSearchResponse;

@Component
public class TransactionService {

    public TransactionSaveResponse save(TransactionSaveRequest request) {
        return null;
    }

    public TransactionSearchResponse search(TransactionSearchRequest request) {
        return null;
    }
}