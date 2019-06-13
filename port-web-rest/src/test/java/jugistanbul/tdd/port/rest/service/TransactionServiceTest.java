package jugistanbul.tdd.port.rest.service;

import jugistanbul.tdd.applicationlayer.*;
import jugistanbul.tdd.port.rest.controller.*;
import jugistanbul.tdd.transactions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionSaveCommand saveCommand;

    @Mock
    private TransactionSearchCommand searchCommand;

    @InjectMocks
    private TransactionService service;

    @Test
    void save() {
        TransactionSaveInput input = new TransactionSaveInput("John", "Doe", "john.doe@gmail.com", "TR045", 15d, "Product 1");
        System.out.println("expected:"+input.toString());
        TransactionSaveOutput output = new TransactionSaveOutput(1, TransactionState.APPROVED);
        when(saveCommand.exec(input)).thenReturn(output);

        TransactionSaveRequest saveRequest = new TransactionSaveRequest("John", "Doe", "john.doe@gmail.com", "Product 1", 15d, "TR045");
        TransactionSaveResponse saveResponse = new TransactionSaveResponse(1, TransactionState.APPROVED);
        assertEquals(saveResponse,service.save(saveRequest, input));
    }

    @Test
    void search() {
        PurchasingAgent agent = PurchasingAgent.aNew().firstname("John").lastname("Doe").email("john.doe@gmail.com").get();
        Transaction trx = Transaction.aNew()
                                             .agent(agent)
                                             .product(Product.aNew()
                                                             .name("Product 1")
                                                             .price(Money.of(15d))
                                                             .get())
                                             .withCode("TR023")
                                             .get();

        TransactionSearchInput input = new TransactionSearchInput(agent);
        var searchResult = Map.of(agent, List.of(trx));
        TransactionSearchOutput output = new TransactionSearchOutput(searchResult);
        when(searchCommand.exec(input)).thenReturn(output);

        TransactionSearchRequest request = new TransactionSearchRequest(new AgentToSearch("John", "Doe", "john.doe@gmail.com"));
        TransactionSearchResponse response = new TransactionSearchResponse(List.of(new AgentTransactions(agent, List.of(trx))));
        assertEquals(response, service.search(request));
    }
}