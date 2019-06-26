package jugistanbul.tdd.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApproveDenyTest {

    @Test
    public void should_approve_a_transaction_if_price_is_lower_than_limit(){
        var accounting = new Accounting(100d);
        var agentJohn = PurchasingAgent.aNew()
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();
        var flashMemory = Product.aNew().name("Flash Memory").price(60d).get();
        var transaction =  agentJohn.buy(flashMemory).withCode("TR001").get();
        boolean approved = accounting.save(transaction);
        assertTrue(approved, "should approve a transaction if price 60d is lower than limit 100d");
    }

    @Test
    public void should_deny_a_transaction_if_price_is_higher_than_limit(){
        var accounting = new Accounting(100d);
        var agentJohn =PurchasingAgent.aNew()
                                      .firstname("John")
                                      .lastname("Doe")
                                      .email("john.doe@gmail.com")
                                      .get();
        var flashMemory = Product.aNew().name("Flash Memory").price(120d).get();
        var transaction =  agentJohn.buy(flashMemory).withCode("TR001").get();
        boolean approved = accounting.save(transaction);
        assertFalse(approved, "should approve a transaction if price 120d is higher than limit 100d");
    }

    @Test
    public void should_approve_a_transaction_if_price_equals_to_limit(){
        var accounting = new Accounting(100d);
        var agentJohn = PurchasingAgent.aNew()
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();
        var flashMemory = Product.aNew().name("Flash Memory").price(100d).get();
        var transaction =  agentJohn.buy(flashMemory).withCode("TR001").get();
        boolean approved = accounting.save(transaction);
        assertTrue(approved, "should approve a transaction if price 100d equals to limit 100d");
    }

    @Test
    public void should_approve_a_transaction_if_total_is_still_lower_than_limit() {
        var accounting = new Accounting(100d);
        var agentJohn = PurchasingAgent.aNew()
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();

        var ssdDisc = Product.aNew().name("SSD Disc").price(50d).get();
        var hardDisc = Product.aNew().name("Hard Disc").price(40d).get();
        var nasDevice = Product.aNew().name("NAS Device").price(10d).get();

        var transaction1 = agentJohn.buy(ssdDisc).withCode("TR001").get();
        var transaction2 = agentJohn.buy(hardDisc).withCode("TR002").get();
        var transaction3 = agentJohn.buy(nasDevice).withCode("TR003").get();

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
        var agentJohn =PurchasingAgent.aNew()
                                      .firstname("John")
                                      .lastname("Doe")
                                      .email("john.doe@gmail.com")
                                      .get();

        var ssdDisc = Product.aNew().name("SSD Disc").price(50d).get();
        var hardDisc = Product.aNew().name("Hard Disc").price(40d).get();
        var nasDevice = Product.aNew().name("NAS Device").price(20d).get();

        var transaction1 = agentJohn.buy(ssdDisc).withCode("TR001").get();
        var transaction2 = agentJohn.buy(hardDisc).withCode("TR002").get();
        var transaction3 = agentJohn.buy(nasDevice).withCode("TR003").get();

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
        var agentJohn = PurchasingAgent.aNew()
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();

        var ssdDisc = Product.aNew().name("SSD Disc").price(50d).get();
        var hardDisc = Product.aNew().name("Hard Disc").price(40d).get();
        var nasDevice = Product.aNew().name("NAS Device").price(20d).get();
        var flashMemory = Product.aNew().name("Flash Memory").price(10d).get();

        var transaction1 = agentJohn.buy(ssdDisc).withCode("TR001").get();
        var transaction2 = agentJohn.buy(hardDisc).withCode("TR002").get();
        var transaction3 = agentJohn.buy(nasDevice).withCode("TR003").get();
        var transaction4 = agentJohn.buy(flashMemory).withCode("TR004").get();

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
        var agentJohn = PurchasingAgent.aNew()
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();
        var agentMary = PurchasingAgent.aNew()
                                       .firstname("Mary")
                                       .lastname("Doe")
                                       .email("mary.doe@gmail.com")
                                       .get();

        var ssdDisc = Product.aNew().name("SSD Disc").price(50d).get();
        var hardDisc = Product.aNew().name("Hard Disc").price(30d).get();
        var nasDevice = Product.aNew().name("NAS Device").price(20d).get();
        var flashMemory = Product.aNew().name("Flash Memory").price(10d).get();

        var transaction1 = agentJohn.buy(ssdDisc).withCode("TR001").get();
        var transaction2 = agentJohn.buy(hardDisc).withCode("TR002").get();
        var transaction3 = agentJohn.buy(flashMemory).withCode("TR003").get();
        var transaction4 = agentMary.buy(flashMemory).withCode("TR004").get();
        var transaction5 = agentJohn.buy(nasDevice).withCode("TR005").get();

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
        var agentJohn1 = PurchasingAgent.aNew().firstname("John").lastname("Doe").email("john.doe@gmail.com").get();
        var agentJohn2 = PurchasingAgent.aNew().firstname("John").lastname("Doe").email("john.doe@gmail.com").get();
        var agentMary = PurchasingAgent.aNew().firstname("Mary").lastname("Doe").email("mary.doe@gmail.com").get();

        var ssdDisc = Product.aNew().name("SSD Disc").price(50d).get();
        var hardDisc = Product.aNew().name("Hard Disc").price(30d).get();
        var nasDevice = Product.aNew().name("NAS Device").price(20d).get();
        var flashMemory = Product.aNew().name("Flash Memory").price(10d).get();

        var transaction1 = agentJohn1.buy(ssdDisc).withCode("TR001").get();
        var transaction2 = agentJohn2.buy(hardDisc).withCode("TR002").get();
        var transaction3 = agentJohn1.buy(flashMemory).withCode("TR003").get();
        var transaction4 = agentMary.buy(flashMemory).withCode("TR004").get();
        var transaction5 = agentJohn2.buy(nasDevice).withCode("TR005").get();

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
    public void should_have_transaction_code() {
        var agentJohn = PurchasingAgent.aNew() // make constructor of PurchasingAgent private
                                       .firstname("John")
                                       .lastname("Doe")
                                       .email("john.doe@gmail.com")
                                       .get();
        var transaction1 = agentJohn.buy(Product // make constructor of Product private
                                                 .aNew()
                                                 .name("SSD Disc")
                                                 .price(50d)
                                                 .get())
                                    .withCode("TR001")
                                    .get();
        assertEquals(transaction1.getCode(), "TR001");
    }



}
