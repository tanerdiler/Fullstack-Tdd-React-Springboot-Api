package jugistanbul.tdd.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApproveDenyTest {

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
}
