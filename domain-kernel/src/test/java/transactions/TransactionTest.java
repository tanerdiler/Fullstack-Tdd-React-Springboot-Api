package transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static transactions.TransactionState.APPROVED;
import static transactions.TransactionState.UNAPPROVED;
import static transactions.TransactionState.WAITING_APPROVE;

public class TransactionTest {

    @Test
    public void should_have_product_info_with_trx_code() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        assertEquals("USB Disc", trx.getProductName());
        assertEquals(Money.of(200d), trx.getPrice());
        assertEquals("TR123", trx.getCode());
    }

    @Test
    public void should_throw_PropertyRequiredException_if_no_trxcode_specified() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        PropertyRequiredException ex = assertThrows(PropertyRequiredException.class, ()-> Transaction.aNew().product(product).get());
        assertEquals("Property{code} of model{Transaction} must not be null!", ex.getMessage());
    }

    @Test
    public void should_throw_PropertyRequiredException_if_no_product_specified() {
        PropertyRequiredException ex = assertThrows(PropertyRequiredException.class, ()-> Transaction.aNew().withCode("TR123").get());
        assertEquals("Property{product} of model{Transaction} must not be null!", ex.getMessage());
    }

    @Test
    public void should_have_state_as_WAITING_APPROVE_by_default() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        assertEquals(WAITING_APPROVE, trx.getState());
    }

    @Test
    public void should_be_approved() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        trx.approve();
        assertEquals(APPROVED, trx.getState());
    }

    @Test
    public void should_be_unapproved() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        trx.unapprove();
        assertEquals(UNAPPROVED, trx.getState());
    }

    @Test
    public void should_not_be_unapproved_if_already_approved() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        trx.approve();
        IllegalOperationException exception = assertThrows(IllegalOperationException.class, ()->trx.unapprove());
        assertEquals("Already approved transaction can't be unapproved!", exception.getMessage());
    }

    @Test
    public void should_not_be_approved_if_already_unapproved() {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        Transaction trx = Transaction.aNew().product(product).withCode("TR123").get();
        trx.unapprove();
        IllegalOperationException exception = assertThrows(IllegalOperationException.class, ()->trx.approve());
        assertEquals("Already unapproved transaction can't be approved!", exception.getMessage());
    }

}