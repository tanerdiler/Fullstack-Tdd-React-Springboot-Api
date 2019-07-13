package transactions.port.rest;

import io.restassured.RestAssured;
import transactions.port.rest.controller.*;
import transactions.port.rest.service.TransactionService;
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
import java.util.List;

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

        var request1 = new TransactionSaveRequest("john","doe","john.doe@gmail.com","USB Disc",20.0,"TR023");
        var response1 = new TransactionSaveResponse(1, "APPROVED");
        Mockito.when(service.save(request1)).thenReturn(response1);

        var request2 = new TransactionSaveRequest("mary","doe","mary.doe@gmail.com","USB Disc",100.0,"TR025");
        var response2 = new TransactionSaveResponse(1, "UNAPPROVED");
        Mockito.when(service.save(request2)).thenReturn(response2);

        TransactionSearchResponse searchResponse2 = new TransactionSearchResponse();
        List<TransactionResponse> searchTransactionList2 = new ArrayList<>();
        searchTransactionList2.add(new TransactionResponse(1, "john doe", "TR123", "APPROVED", "USB Disc", 100D));
        searchTransactionList2.add(new TransactionResponse(2, "john doe", "TR124", "UNAPPROVED", "Flash Disc", 200D));
        searchTransactionList2.add(new TransactionResponse(3, "mary doe", "TR125", "APPROVED", "Hard Disc", 300D));
        searchTransactionList2.add(new TransactionResponse(4, "mary doe", "TR126", "UNAPPROVED", "Mac Book Pro", 400D));
        searchResponse2.setTransactions(searchTransactionList2);
        var searchRequest2 = new TransactionSearchRequest();
        Mockito.when(service.search(searchRequest2)).thenReturn(searchResponse2);

        var searchRequest = new TransactionSearchRequest();
        searchRequest.setAgent(new AgentToSearch("john", "doe","john.doe@gmail.com"));
        TransactionSearchResponse response = new TransactionSearchResponse();
        List<TransactionResponse> transactionList = new ArrayList<>();
        transactionList.add(new TransactionResponse(1, "john doe", "TR123", "APPROVED", "USB Disc", 100D));
        transactionList.add(new TransactionResponse(2, "john doe", "TR124", "UNAPPROVED", "Flash Disc", 200D));
        response.setTransactions(transactionList);
        Mockito.when(service.search(searchRequest)).thenReturn(response);

    }

    @AfterEach
    public void after() {

    }



}