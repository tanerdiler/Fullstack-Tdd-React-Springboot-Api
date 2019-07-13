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
  }`
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
#### 2. Backend developer olarak, frontend ekibine dummy verilerle çalıştırılabilir bir api uygulaması vermeliyim
- port-web-rest projesi altında 'mvn clean install' komutunu çalıştırarak oluşan artifact'i local repo'ya atalım
- port-web-rest-stub-runner projesi altında 'mvn clean package' komutunu çalıştıralım ve artifact'in oluşmasını sağlayalım
- oluşan artifact'i aşağıdaki komut ile çalıştıralım
```
java -jar target/transactions-port-rest-stubrunner-0.0.1-SNAPSHOT.jar --server.port=8070 --stubrunner.ids=transactions:transactions-port-rest:0.0.1-SNAPSHOT:stubs:8000
```

