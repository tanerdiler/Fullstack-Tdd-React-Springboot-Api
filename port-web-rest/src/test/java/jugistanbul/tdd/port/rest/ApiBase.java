package jugistanbul.tdd.port.rest;

import io.restassured.RestAssured;
import jugistanbul.tdd.applicationlayer.TransactionSaveCommand;
import jugistanbul.tdd.applicationlayer.TransactionSaveInput;
import jugistanbul.tdd.port.rest.controller.*;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.port=0")
public class ApiBase {

    @LocalServerPort
    int port;

    //@MockBean
    //private TransactionService service;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port;

    }

    @AfterEach
    public void after() {

    }



}