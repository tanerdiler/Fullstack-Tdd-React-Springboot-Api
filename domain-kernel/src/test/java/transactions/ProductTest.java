package transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    @Test
    public void should_have_name_and_price () {
        Product product = Product.aNew().name("USB Disc").price(Money.of(200d)).get();
        assertEquals("USB Disc", product.getName());
        assertEquals(Money.of(200d), product.getPrice());
    }

    @Test
    public void should_throw_PropertyRequiredException_if_no_price_specified () {
        PropertyRequiredException ex = assertThrows(PropertyRequiredException.class, ()-> Product.aNew().name("USB Disc").get());
        assertEquals("Property{price} of model{Product} must not be null!", ex.getMessage());
    }

    @Test
    public void should_throw_PropertyRequiredException_if_no_name_specified () {
        PropertyRequiredException ex = assertThrows(PropertyRequiredException.class, ()-> Product.aNew().price(Money.of(200d)).get());
        assertEquals("Property{name} of model{Product} must not be null!", ex.getMessage());
    }

}