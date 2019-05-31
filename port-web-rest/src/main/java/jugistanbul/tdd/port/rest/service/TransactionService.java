package jugistanbul.tdd.port.rest.service;

import jugistanbul.tdd.applicationlayer.*;
import jugistanbul.tdd.port.rest.controller.*;
import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.PurchasingAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionService {

    @Autowired
    private TransactionSaveCommand saveCommand;

    @Autowired
    private TransactionSearchCommand searchCommand;

    public TransactionSaveResponse save(TransactionSaveRequest request) {
        TransactionSaveInput input = new TransactionSaveInput();
        input.setFirstname(request.getFirstname());
        input.setLastname(request.getLastname());
        input.setEmail(request.getEmail());
        input.setProductName(request.getProductName());
        input.setProductPrice(request.getPrice());
        input.setTransactionCode(request.getTransactionCode());

        TransactionSaveOutput output = saveCommand.exec(input);

        TransactionSaveResponse response = new TransactionSaveResponse();
        response.setId(output.getTransactionId());
        response.setState(output.getState());
        return response;
    }

    public TransactionSearchResponse search(TransactionSearchRequest request) {
        TransactionSearchInput input = new TransactionSearchInput();
        PurchasingAgent agent = PurchasingAgent.aNew()
                                                   .firstname(request.getAgent().getFirstname())
                                                   .lastname(request.getAgent().getLastname())
                                                   .email(request.getAgent().getEmail())
                                                   .get();
        input.setAgentToSearch(agent);
        TransactionSearchOutput output = searchCommand.exec(input);
        List<AgentTransactions> transactions = output.getTransactions().entrySet().stream().map(e->new AgentTransactions(e.getKey(), e.getValue())).collect(Collectors.toList());
        return new TransactionSearchResponse(transactions);
    }
}
