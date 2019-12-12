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
   	Accounting accounting = new Accounting(100d);
	Transaction transaction = new Transaction(60d);
	boolean approved = accounting.save(transaction);
	assertTrue(approved, "should approve a transaction if price 60d is lower than limit 100d");.
}
```

3. Test - 2 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_deny_a_transaction_if_price_is_higher_than_limit() {
	Accounting accounting = new Accounting(100d);
	Accounting transaction = new Transaction(120d);
	boolean approved = accounting.save(transaction);
	assertFalse(approved, "should approve a transaction if price 120d is higher than limit 100d");
}
```

4. Test - 3 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_price_equals_to_limit() {
	Accounting accounting = new Accounting(100d);
	Transaction transaction = new Transaction(100d);
	boolean approved = accounting.save(transaction);
        assertTrue(approved, "should approve a transaction if price 100d equals to limit 100d");
}
```

5. Test - 4 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_is_still_lower_than_limit() {
	Accounting accounting = new Accounting(100d);
	Transaction transaction1 = new Transaction(50d);
	Transaction transaction2 = new Transaction(40d);
	Transaction transaction3 = new Transaction(10d);

	boolean transaction1Approved = accounting.save(transaction1);
	boolean transaction2Approved = accounting.save(transaction2);
	boolean transaction3Approved = accounting.save(transaction3);

	assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
	assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
	assertTrue(transaction3Approved, "should approve a transaction with price 10d if total 100d is higher than limit 100d");
}
```

6. Test - 5 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_deny_a_transaction_if_total_is_still_higher_than_limit() {
    Accounting accounting = new Accounting(100d);
    Transaction transaction1 = new Transaction(50d);
    Transaction transaction2 = new Transaction(40d);
    Transaction transaction3 = new Transaction(20d);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);

    assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
    assertFalse(transaction3Approved, "should deny a transaction with price 20d if total 110d is higher than limit 100d");
}
```

7. Test - 6 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_is_still_lower_than_limit() {
    Accounting accounting = new Accounting(100d);
    Transaction transaction1 = new Transaction(50d);
    Transaction transaction2 = new Transaction(40d);
    Transaction transaction3 = new Transaction(20d);
    Transaction transaction4 = new Transaction(10d);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);

    assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
    assertFalse(transaction3Approved, "should deny a transaction with price 20d if total 110d is higher than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction with price 10d if total 100d is higher than limit 100d");
}
```
--- çözüm

```
public boolean save(Transaction transaction) {
    double total = transactions.stream().filter(t->t.isApproved()).mapToDouble(t->t.getPrice()).sum();
    if (total+transaction.getPrice()<=limit) {
        transaction.beApproved();
    } else {
        transaction.beDenied();
    }
    transactions.add(transaction);
    return transaction.isApproved();
}
```

8. Test - 7 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_is_still_lower_than_limit() {
    Accounting accounting = new Accounting(100d);
    PurchasingAgent agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    PurchasingAgent agentMary = new PurchasingAgent("Mary", "Doe", "mary.doe@gmail.com");
    Transaction transaction1 = new Transaction(agentJohn, 50d);
    Transaction transaction2 = new Transaction(agentJohn, 30d);
    Transaction transaction3 = new Transaction(agentJohn, 10d);
    Transaction transaction4 = new Transaction(agentMary, 10d);
    Transaction transaction5 = new Transaction(agentJohn, 20d);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);
    boolean transaction5Approved = accounting.save(transaction5);

    assertTrue(transaction1Approved, "should approve a transaction of john with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction of john with price 30d if total 80d is lower than limit 100d");
    assertTrue(transaction3Approved, "should approve a transaction of john with price 10d if total 90d is lower than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction of mary with price 10d if total 10d is lower than limit 100d");
    assertFalse(transaction5Approved, "should deny a transaction of john with price 20d if total 110d is higher than limit 100d");
}
```

9. Test - 8 : Aşağıdaki testi Failed/Success/Refactor döngüsünde gerçekleştirin

```
@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_with_different_reference_is_still_lower_than_limit() {
    Accounting accounting = new Accounting(100d);
    PurchasingAgent agentJohn1 = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    PurchasingAgent agentJohn2 = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    PurchasingAgent agentMary = new PurchasingAgent("Mary", "Doe", "mary.doe@gmail.com");
    Transaction transaction1 = new Transaction(agentJohn1, 50d);
    Transaction transaction2 = new Transaction(agentJohn2, 30d);
    Transaction transaction3 = new Transaction(agentJohn1, 10d);
    Transaction transaction4 = new Transaction(agentMary, 10d);
    Transaction transaction5 = new Transaction(agentJohn2, 20d);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);
    boolean transaction5Approved = accounting.save(transaction5);

    assertTrue(transaction1Approved, "should approve a transaction of john with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction of john with price 30d if total 80d is lower than limit 100d");
    assertTrue(transaction3Approved, "should approve a transaction of john with price 10d if total 90d is lower than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction of mary with price 10d if total 10d is lower than limit 100d");
    assertFalse(transaction5Approved, "should deny a transaction of john with price 20d if total 110d is higher than limit 100d");
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

--- Replace Other Tests
```
@Test
public void should_approve_a_transaction_if_price_is_lower_than_limit(){
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var flashMemory = new Product("Flash Memory", 60d);
    var transaction = new Transaction(agentJohn, flashMemory);
    boolean approved = accounting.save(transaction);
    assertTrue(approved, "should approve a transaction if price 60d is lower than limit 100d");
}

@Test
public void should_deny_a_transaction_if_price_is_higher_than_limit(){
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var flashMemory = new Product("Flash Memory", 120d);
    var transaction = new Transaction(agentJohn, flashMemory);
    boolean approved = accounting.save(transaction);
    assertFalse(approved, "should approve a transaction if price 120d is higher than limit 100d");
}

@Test
public void should_approve_a_transaction_if_price_equals_to_limit(){
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var flashMemory = new Product("Flash Memory", 100d);
    var transaction = new Transaction(agentJohn, flashMemory);
    boolean approved = accounting.save(transaction);
    assertTrue(approved, "should approve a transaction if price 100d equals to limit 100d");
}

@Test
public void should_approve_a_transaction_if_total_is_still_lower_than_limit() {
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");

    var ssdDisc = new Product("SSD Disc", 50d);
    var hardDisc = new Product("Hard Disc", 40d);
    var nasDevice = new Product("NAS Device", 10d);

    var transaction1 = new Transaction(agentJohn, ssdDisc);
    var transaction2 = new Transaction(agentJohn, hardDisc);
    var transaction3 = new Transaction(agentJohn, nasDevice);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);

    assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
    assertTrue(transaction3Approved, "should approve a transaction with price 10d if total 100d is higher than limit 100d");
}

@Test
public void should_deny_a_transaction_if_total_is_still_higher_than_limit() {
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");

    var ssdDisc = new Product("SSD Disc", 50d);
    var hardDisc = new Product("Hard Disc", 40d);
    var nasDevice = new Product("NAS Device", 20d);

    var transaction1 = new Transaction(agentJohn, ssdDisc);
    var transaction2 = new Transaction(agentJohn, hardDisc);
    var transaction3 = new Transaction(agentJohn, nasDevice);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);

    assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
    assertFalse(transaction3Approved, "should deny a transaction with price 20d if total 110d is higher than limit 100d");
}

@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_is_still_lower_than_limit() {
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");

    var ssdDisc = new Product("SSD Disc", 50d);
    var hardDisc = new Product("Hard Disc", 40d);
    var nasDevice = new Product("NAS Device", 20d);
    var flashMemory = new Product("Flash Memory", 10d);

    var transaction1 = new Transaction(agentJohn, ssdDisc);
    var transaction2 = new Transaction(agentJohn, hardDisc);
    var transaction3 = new Transaction(agentJohn, nasDevice);
    var transaction4 = new Transaction(agentJohn, flashMemory);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);

    assertTrue(transaction1Approved, "should approve a transaction with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction with price 40d if total 90d is lower than limit 100d");
    assertFalse(transaction3Approved, "should deny a transaction with price 20d if total 110d is higher than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction with price 10d if total 100d is higher than limit 100d");
}

@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_is_still_lower_than_limit() {
    var accounting = new Accounting(100d);
    var agentJohn = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var agentMary = new PurchasingAgent("Mary", "Doe", "mary.doe@gmail.com");

    var ssdDisc = new Product("SSD Disc", 50d);
    var hardDisc = new Product("Hard Disc", 30d);
    var nasDevice = new Product("NAS Device", 20d);
    var flashMemory = new Product("Flash Memory", 10d);

    var transaction1 = new Transaction(agentJohn, ssdDisc);
    var transaction2 = new Transaction(agentJohn, hardDisc);
    var transaction3 = new Transaction(agentJohn, flashMemory);
    var transaction4 = new Transaction(agentMary, flashMemory);
    var transaction5 = new Transaction(agentJohn, nasDevice);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);
    boolean transaction5Approved = accounting.save(transaction5);

    assertTrue(transaction1Approved, "should approve a transaction of john with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction of john with price 30d if total 80d is lower than limit 100d");
    assertTrue(transaction3Approved, "should approve a transaction of john with price 10d if total 90d is lower than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction of mary with price 10d if total 10d is lower than limit 100d");
    assertFalse(transaction5Approved, "should deny a transaction of john with price 20d if total 110d is higher than limit 100d");
}

@Test
public void should_approve_a_transaction_if_total_of_approved_transactions_of_same_agent_with_different_reference_is_still_lower_than_limit() {
    var accounting = new Accounting(100d);
    var agentJohn1 = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var agentJohn2 = new PurchasingAgent("John", "Doe", "john.doe@gmail.com");
    var agentMary = new PurchasingAgent("Mary", "Doe", "mary.doe@gmail.com");

    var ssdDisc = new Product("SSD Disc", 50d);
    var hardDisc = new Product("Hard Disc", 30d);
    var nasDevice = new Product("NAS Device", 20d);
    var flashMemory = new Product("Flash Memory", 10d);

    var transaction1 = new Transaction(agentJohn1, ssdDisc);
    var transaction2 = new Transaction(agentJohn2, hardDisc);
    var transaction3 = new Transaction(agentJohn1, flashMemory);
    var transaction4 = new Transaction(agentMary, flashMemory);
    var transaction5 = new Transaction(agentJohn2, nasDevice);

    boolean transaction1Approved = accounting.save(transaction1);
    boolean transaction2Approved = accounting.save(transaction2);
    boolean transaction3Approved = accounting.save(transaction3);
    boolean transaction4Approved = accounting.save(transaction4);
    boolean transaction5Approved = accounting.save(transaction5);

    assertTrue(transaction1Approved, "should approve a transaction of john with price 50d if total 50d is lower than limit 100d");
    assertTrue(transaction2Approved, "should approve a transaction of john with price 30d if total 80d is lower than limit 100d");
    assertTrue(transaction3Approved, "should approve a transaction of john with price 10d if total 90d is lower than limit 100d");
    assertTrue(transaction4Approved, "should approve a transaction of mary with price 10d if total 10d is lower than limit 100d");
    assertFalse(transaction5Approved, "should deny a transaction of john with price 20d if total 110d is higher than limit 100d");
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

