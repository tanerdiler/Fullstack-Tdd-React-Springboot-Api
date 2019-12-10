# KATA-3
TDD ile BACKEND'i gerçekleştirelim
## AMAÇ
KATA-1'de yazdığımız REST-SERVICE'lerin BUSINESS-LOGIC gerçekleştirimini TDD ile yapalım.
## YÖNLENDIRMELER
- "mvn clean test" komutunu kullanarak testleri çalıştırabilirsiniz
## DEFINITION OF DONE
- Domain Model'ler oluşturulmalı
- Domain Kernel ile Rest Katmanı'nın etkileşimi sağlanmalı
## GÖREVLER

1. Aşağıdaki soruları cevaplayalım

1.1 Anemic Programming nedir?
1.2 Domain Driven Design nedir?
1.3 Hexagonal Architecture nedir?
1.4 SATIN_ALIM_TAKIBI.pdf proje tanımında geçen domain modellerini çıkaralım
1.5 SATIN_ALIM_TAKIBI.pdf proje tanımında geçen eylemleri çıkaralım
1.6 Hangi eylem hangi domain model tarafından gerçekleştirilebilir?
1.7 Test Driven Design ile yazılım geliştireceğiz ama nerden başlayacağız?

NOT: Aşağıdaki çalışmayı domain-kernel modülü içerisinde gerçekleştirelim

2. Test - 1 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin 

```
@Test
public void should_approve_a_transaction_if_price_is_lower_than_limit() {
   ...
}
```

3. Test - 2 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_deny_a_transaction_if_price_is_higher_than_limit() {
   ...
}
```

4. Test - 3 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_price_equals_to_limit() {
   ...
}
```

5. Test - 4 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_is_still_lower_than_limit() {
   ...
}
```

6. Test - 5 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_deny_a_transaction_if_total_is_still_higher_than_limit() {
   ...
}
```

7. Test - 6 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_is_still_lower_than_limit() {
   ...
}
```

8. Test - 7 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_is_still_lower_than_limit() {
   ...
}
```

9. Test - 8 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_with_different_reference_is_still_lower_than_limit() {
   ...
}
```
10. Test - 9 : Aşağıdaki testin geçmesini sağlayacak şekilde gerekli refactoring çalışmasını gerçekletirin.

```
@Test
public void refactor() {
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var ssdDisc = new Product("SSD Disc", 50d);
    var transaction1 = agentJohn.buy(ssdDisc);
    boolean transaction1Approved = accounting.save(transaction1);
}
```

11. Test - 10 : Aşağıdaki testin geçmesini sağlayacak şekilde gerekli refactoring çalışmasını gerçekletirin. 

```
@Test
public void should_have_transaction_code() {
    PurchasingAgent agentJohn = PurchasingAgent.aNew() // make constructor of PurchasingAgent private
	                                       .firstname("John")
	                                       .lastname("Doe")
	                                       .email("john.doe@gmail.com")
	                                       .get();
    Transaction transaction1 = agentJohn.buy(Product // make constructor of Product private
	                                                 .aNew()
	                                                 .name("SSD Disc")
	                                                 .price(50d)
	                                                 .get())
	                                    .withCode("TR001")
	                                    .get();
     assertEquals(transaction1.getCode(), "TR001");
}
```





