package jugistanbul.tdd.port.rest.service;

import jugistanbul.tdd.applicationlayer.*;
import jugistanbul.tdd.port.rest.controller.*;
import jugistanbul.tdd.transactions.PurchasingAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TransactionService {

    @Autowired
    private TransactionSaveCommand saveCommand;

    @Autowired
    private TransactionSearchCommand searchCommand;

    public TransactionSaveResponse save(TransactionSaveRequest request) {
        var input = requestToSaveInput(request);
        System.out.println("actual:"+input.toString());
        var output = saveCommand.exec(input);
        return outputToSaveResponse(output);
    }

    public TransactionSaveResponse save(TransactionSaveRequest request, TransactionSaveInput expected) {
        var input = requestToSaveInput(request);
        System.out.println("actual:"+input.toString());
        System.out.println(expected.equals(input));
        var output = saveCommand.exec(input);
        return outputToSaveResponse(output);
    }

    public TransactionSearchResponse search(TransactionSearchRequest request) {
        var input = requestToSearchInput(request);
        var output = searchCommand.exec(input);
        return outputToSearchResponse(output);
    }

    private TransactionSearchResponse outputToSearchResponse(TransactionSearchOutput output) {
        var transactions = output.getTransactions().entrySet().stream().map(e->new AgentTransactions(e.getKey(), e.getValue())).collect(Collectors.toList());
        return new TransactionSearchResponse(transactions);
    }

    private TransactionSearchInput requestToSearchInput(TransactionSearchRequest request) {
        TransactionSearchInput input = new TransactionSearchInput();
        PurchasingAgent agent = PurchasingAgent.aNew()
                                               .firstname(request.getAgent().getFirstname())
                                               .lastname(request.getAgent().getLastname())
                                               .email(request.getAgent().getEmail())
                                               .get();
        input.setAgentToSearch(agent);
        return input;
    }

    private TransactionSaveResponse outputToSaveResponse(TransactionSaveOutput output) {
        TransactionSaveResponse response = new TransactionSaveResponse();
        response.setId(output.getTransactionId());
        response.setState(output.getState());
        return response;
    }

    private TransactionSaveInput requestToSaveInput(TransactionSaveRequest request) {
        TransactionSaveInput input = new TransactionSaveInput();
        input.setFirstname(request.getFirstname());
        input.setLastname(request.getLastname());
        input.setEmail(request.getEmail());
        input.setProductName(request.getProductName());
        input.setProductPrice(request.getPrice());
        input.setTransactionCode(request.getTransactionCode());
        return input;
    }
}
