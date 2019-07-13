# KATA-1
CONTRACT DRIVEN DESIGN
## AMAÇ
- Rest servislerimizin her zaman aynı isteği alıp aynı cevabı döneceğini garantisini vermek
- Frontend ekibine veya third-party bir entegrasyon için veritabanı gerektirmeyen çalışabilir bir api uygulaması vermek 
## YÖNLENDIRMELER
- "mvn clean test" komutunu kullanarak testleri çalıştırabilirsiniz
- "target/generated-test-sources" içerisinde üretilmiş java test kodları bulunur
- "src/test/java/jugis`tanbul/tdd/port/rest/service/ApiBase.java" içerisindeki @BeforeEach ile işaretlenmiş method içerisinde gerekl mocklama işlemlerini gerçekleştirmeyi unutmayın.
## DEFINITION OF DONE
- .groovy uzantılı contract dosyaları tanımlanmış olmalı
- Tanımlanan 4 adet contract testi başarılı bir şekilde çalışmalı
- Gerekli service ve resource/controller sınıfları tanımlanmış olmalı
- Mocklanan service methodları gerçekte null dönüyor olmalı
## GÖREVLER
#### 1. Satın Alım Uzmanı olarak, satın alım işlemlerini sisteme kayıt edebilmeliyim
##### 1.1 Şirketin CFO'su olarak, sisteme girilen satın alım işleminin limite bakılarak sistem tarafından onaylanmasını istiyorum
/src/test/resources/contracts/api/save-approved-transactions.groovy
```		
  package contracts.api
  
  import org.springframework.cloud.contract.spec.Contract
  
  Contract.make {
      description("""
                  Save transaction and sign as approved
                
                  given:
                    transaction
                  when:
                      saved 
                  then:
                    we'll return transaction id and whether approved or not
                
                  """)

      request {
          method POST()
          url "/transactions"
          headers {
              contentType applicationJson()
          }
          body("""
        {
          "firstname": "john",
          "lastname": "doe",
          "email": "john.doe@gmail.com",
          "productName": "USB Disc",
          "price": 20.0,
          "transactionCode": "TR023"
        }
      """
          )
      }

      response {
          status OK()
          headers {
              contentType applicationJson()
          }
          body("""
        {
          "id": 1,
          "state": "APPROVED",
        }
      """
          )
      }
  }
```
-- TransactionSaveController.java
				
```
package jugistanbul.tdd.port.rest.controller;

import jugistanbul.tdd.port.rest.service.TransactionService;
import jugistanbul.tdd.transactions.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionSaveController {

    private final TransactionService service;

    @PostMapping
    public TransactionSaveResponse save(@RequestBody @Valid TransactionSaveRequest request) {
        return service.save(request);
    }
}
```

-- TransactionSaveRequest.java

```
package jugistanbul.tdd.port.rest.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionSaveRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String productName;
    private Double price;

    public TransactionSaveRequest(String firstname, String lastname, String email, String productName, Double price, String transactionCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.productName = productName;
        this.price = price;
        this.transactionCode = transactionCode;
    }

    private String transactionCode;
}
```

-- TransactionSaveResponse.java
```
package jugistanbul.tdd.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSaveResponse {
    private Integer id;
    private String state;
}
```
-- ApiBase.java

```
var request1 = new TransactionSaveRequest("john","doe","john.doe@gmail.com","USB Disc",20.0,"TR023");
var response1 = new TransactionSaveResponse(1, "APPROVED");
Mockito.when(service.save(request1)).thenReturn(response1);
```

-- TransactionService.java
```
package jugistanbul.tdd.port.rest.service;

import jugistanbul.tdd.applicationlayer.*;
import jugistanbul.tdd.port.rest.controller.*;
import jugistanbul.tdd.transactions.PurchasingAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TransactionService {
  public TransactionSaveResponse save(TransactionSaveRequest request) {
        return null;
    }
}
```

##### 1.2 Şirketin CFO'su olarak, sisteme girilen satın alım işleminin limite bakılarak sistem tarafından onaylanmamasını istiyorum
/src/test/resources/contracts/api/save-unapproved-transactions.groovy`
```
  package contracts.api
  
  import org.springframework.cloud.contract.spec.Contract
  
  Contract.make {
      description("""
                  Save transaction and sign as unapproved because of it makes total exceed the limit
                
                  given:
                    transaction
                  when:
                      saved 
                  then:
                    we'll return information with unapproved state and id
                
                  """)
      request {
          method POST()
          url "/transactions"
          headers {
              contentType applicationJson()
          }
          body("""
        {
          "firstname": "mary",
          "lastname": "doe",
          "email": "mary.doe@gmail.com",
          "productName": "USB Disc",
          "price": 100.0,
          "transactionCode": "TR025"
        }
      """
          )
      }

      response {
          status OK()
          headers {
              contentType applcationJson()
          }
          body("""
        {
          "id": 1,
          "state": "UNAPPROVED",
        }
      """
          )
      }
  }
```

-- ApiBase.java

```
var request2 = new TransactionSaveRequest("mary","doe","mary.doe@gmail.com","USB Disc",100.0,"TR025");
var response2 = new TransactionSaveResponse(1, "UNAPPROVED");
Mockito.when(service.save(request2)).thenReturn(response2);
```

##### 1.3 Şirketin CFO'su olarak, sisteme girilen tüm satın alım işlemlerini listeleyebilmek istiyorum
/src/test/resources/contracts/api/return-all-transactions.groovy
```	
  package contracts.api
  
  import org.springframework.cloud.contract.spec.Contract
  
  Contract.make {
      description("""
  Return all transactions

  given:
    search criteria
  when:
      search 
  then:
    we'll return all transactions ever saved

  """)

      request {
          method GET()
          url "/transactions"
          headers {
              contentType applicationJson()
          }
          body("""
        {
          "agent": null
        }
      """
          )
      }

      response {
          status OK()
          headers {
              contentType applicationJson()
          }
          body("""
                  {
                      "transactions":[
                          {"id":1,"agent":"john doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
                          {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
                          {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Hard Disc","price":300.0},
                          {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Book Pro","price":400.0}
                      ]
                  }
      """
          )
      }
  }
```

-- TransactionSearchController.java
```
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionSearchController {

    private final TransactionService service;

    @GetMapping
    public TransactionSearchResponse search(@RequestBody @Valid TransactionSearchRequest request) {
        return service.search(request);
    }
}
```

-- TransactionSearchRequest.java

```
package jugistanbul.tdd.port.rest.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionSearchRequest {

  private AgentToSearch agent;

  @Override
  public boolean equals(Object o) {
      if (this == o)
          return true;
      if (o == null || getClass() != o.getClass())
          return false;
      TransactionSearchRequest request = (TransactionSearchRequest) o;
      return Objects.equals(agent, request.agent);
  }

  @Override
  public int hashCode() {
      return Objects.hash(agent);
  }

}
```

-- AgentToSearch.java

```
package jugistanbul.tdd.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class AgentToSearch {

  private String firstname;
  private String lastname;
  private String email;

  public AgentToSearch(String firstname, String lastname, String email) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o)
          return true;
      if (o == null || getClass() != o.getClass())
          return false;
      AgentToSearch that = (AgentToSearch) o;
      return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
      return Objects.hash(firstname, lastname, email);
  }
}
```


-- TransactionSearchResponse.java
```
package jugistanbul.tdd.port.rest.controller;

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
```

-- TransactionResponse.java
```
package jugistanbul.tdd.port.rest.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionResponse {

    private Integer id;

    private String agent;

    private String productName;

    private String code;

    private String state;

    private Double price;

    public TransactionResponse(Integer id, String agent, String code, String state, String productName, Double price) {
        this.id = id;
        this.agent = agent;
        this.productName = productName;
        this.code = code;
        this.state = state;
        this.price = price;
    }
}
```
-- ApiBase.java
```
  TransactionSearchResponse searchResponse2 = new TransactionSearchResponse();
    List<TransactionResponse> searchTransactionList2 = new ArrayList<>();
    searchTransactionList2.add(new TransactionResponse(1, "john doe", "TR123", "APPROVED", "USB Disc", 100D));
    searchTransactionList2.add(new TransactionResponse(2, "john doe", "TR124", "UNAPPROVED", "Flash Disc", 200D));
    searchTransactionList2.add(new TransactionResponse(3, "mary doe", "TR125", "APPROVED", "Hard Disc", 300D));
    searchTransactionList2.add(new TransactionResponse(4, "mary doe", "TR126", "UNAPPROVED", "Mac Book Pro", 400D));
    searchResponse2.setTransactions(searchTransactionList2);
    var searchRequest2 = new TransactionSearchRequest();
    Mockito.when(service.search(searchRequest2)).thenReturn(searchResponse2);
```

-- TransactionService.java`

```
  public TransactionSearchResponse search(TransactionSearchRequest request) {
      return null;
  }
```


##### 1.4 Şirketin CFO'su olarak, satın alım uzmanına ait tüm işlemleri listeleyebilmek istiyorum
/src/test/resources/contracts/api/return-purchasingagent-transactions.groovy
```
  package contracts.api

  import org.springframework.cloud.contract.spec.Contract

  Contract.make {
      description("""
  Return transactions by created specified purchasing agent

  given:
    purchasing agent info
  when:
      search 
  then:
    we will return transactions by created specified purchasing agent

  """)

      request {
          method GET()
          url "/transactions"
          headers {
              contentType applicationJson()
          }
          body("""
        {
          "agent": {"firstname":"john", "lastname":"doe", "email":"john.doe@gmail.com"}
        }
      """
          )
      }

      response {
          status OK()
          body("""
                  {
                      "transactions":[
                          {"id":1,"agent":"john doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
                          {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0}
                      ]
                  }
      """
          )
      }
  }
```
-- ApiBase.java
```
var searchRequest = new TransactionSearchRequest();
searchRequest.setAgent(new AgentToSearch("john", "doe","john.doe@gmail.com"));
TransactionSearchResponse response = new TransactionSearchResponse();
List<TransactionResponse> transactionList = new ArrayList<>();
transactionList.add(new TransactionResponse(1, "john doe", "TR123", "APPROVED", "USB Disc", 100D));
transactionList.add(new TransactionResponse(2, "john doe", "TR124", "UNAPPROVED", "Flash Disc", 200D));
response.setTransactions(transactionList);
Mockito.when(service.search(searchRequest)).thenReturn(response);`
```

#### 2. Backend developer olarak, frontend ekibine dummy verilerle çalıştırılabilir bir api uygulaması vermeliyim
- port-web-rest projesi altında 'mvn clean install' komutunu çalıştırarak oluşan artifact'i local repo'ya atalım
- port-web-rest-stub-runner projesi altında 'mvn clean package' komutunu çalıştıralım ve artifact'in oluşmasını sağlayalım
- oluşan artifact'i aşağıdaki komut ile çalıştıralım
```java -jar target/transactions-port-rest-stubrunner-0.0.1-SNAPSHOT.jar --server.port=8070 --stubrunner.ids=jugistanbul.tdd:transactions-port-rest:0.0.1-SNAPHOT:stubs:8000```