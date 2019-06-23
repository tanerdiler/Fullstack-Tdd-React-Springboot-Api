package jugistanbul.tdd.port.rest;

import io.restassured.RestAssured;
import jugistanbul.tdd.applicationlayer.TransactionSaveCommand;
import jugistanbul.tdd.applicationlayer.TransactionSaveInput;
import jugistanbul.tdd.port.rest.controller.*;
import jugistanbul.tdd.port.rest.service.TransactionService;
import jugistanbul.tdd.transactions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.port=0")
public class ApiBase {

    @LocalServerPort
    int port;

    @MockBean
    private TransactionService service;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port;

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("FLash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        trx1.setId(1);
        trx1.approve();
        trx2.setId(2);
        trx2.unapprove();


        var request1 = new TransactionSaveRequest("john","doe","john.doe@gmail.com","USB Disc",20.0,"TR023");
        var response1 = new TransactionSaveResponse(1, TransactionState.APPROVED);
        Mockito.when(service.save(request1)).thenReturn(response1);

        var request2 = new TransactionSaveRequest("mary","doe","mary.doe@gmail.com","USB Disc",100.0,"TR025");
        var response2 = new TransactionSaveResponse(1, TransactionState.UNAPPROVED);
        Mockito.when(service.save(request2)).thenReturn(response2);

        var searchRequest = new TransactionSearchRequest();
        searchRequest.setAgent(new AgentToSearch("john","doe","john.doe@gmail.com"));
        var searchResponse = new TransactionSearchResponse();
        Collection<AgentTransactions> transactions = new ArrayList<>();
        transactions.add(new AgentTransactions(agent1, Arrays.asList(new Transaction[]{trx1,trx2})));
        searchResponse.setAgentTransactions(transactions);
        Mockito.when(service.search(searchRequest)).thenReturn(searchResponse);

        var searchRequest2 = new TransactionSearchRequest();
        Mockito.when(service.search(searchRequest2)).thenReturn(searchResponse);

    }

    @AfterEach
    public void after() {

    }



}