package jugistanbul.tdd.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchasingAgentTest {

    @Test
    public void should_have_fullname() {
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@gmail.com";
        PurchasingAgent agent = PurchasingAgent.aNew().firstname(firstname).lastname(lastname).email(email).get();
        assertEquals("John Doe", agent.getFullname());
    }

    @Test
    public void should_have_email() {
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@gmail.com";
        PurchasingAgent agent = PurchasingAgent.aNew().firstname(firstname).lastname(lastname).email(email).get();
        assertEquals("john.doe@gmail.com", agent.getEmail());
    }

    @Test
    public void should_throw_ValidationException_when_no_email_specified() {
        String firstname = "John";
        String lastname = "Doe";
        Exception exception = assertThrows(PropertyRequiredException.class, ()->PurchasingAgent.aNew().firstname(firstname).lastname(lastname).get());
        assertEquals("Property{email} of model{PurchasingAgent} must not be null!", exception.getMessage());
    }

    @Test
    public void should_throw_ValidationException_when_no_firstname_specified() {
        String lastname = "Doe";
        String email = "john.doe@gmail.com";
        Exception exception = assertThrows(PropertyRequiredException.class, ()->PurchasingAgent.aNew().lastname(lastname).email(email).get());
        assertEquals("Property{firstname} of model{PurchasingAgent} must not be null!", exception.getMessage());
    }

    @Test
    public void should_throw_ValidationException_when_no_lastname_specified() {
        String firstname = "john";
        String email = "john.doe@gmail.com";
        Exception exception = assertThrows(PropertyRequiredException.class, ()->PurchasingAgent.aNew().firstname(firstname).email(email).get());
        assertEquals("Property{lastname} of model{PurchasingAgent} must not be null!", exception.getMessage());
    }

    @Test
    public void should_not_equal_to_agent_with_different_email() {
        PurchasingAgent agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        PurchasingAgent agent2 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john.doe@gmail.com").get();
        assertFalse(agent1.equals(agent2));
    }

    @Test
    public void should_not_equal_to_agent_with_different_firstname() {
        PurchasingAgent agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        PurchasingAgent agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        assertFalse(agent1.equals(agent2));
    }

    @Test
    public void should_not_equal_to_agent_with_different_lastname() {
        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var agent2 = PurchasingAgent.aNew().firstname("john").lastname("wick").email("john_doe@gmail.com").get();
        assertFalse(agent1.equals(agent2));
    }

    @Test
    public void should_equal_to_agent_with_same_info() {
        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var agent2 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        assertTrue(agent1.equals(agent2));
    }

    @Test
    public void should_buy_product_then_generate_transaction() {
        var trxCode = "TR000";
        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode(trxCode).get();
        assertEquals(Money.of(100d), trx.getPrice());
        assertEquals(trxCode, trx.getCode());
        assertEquals("USB Disc", trx.getProductName());
    }
}
