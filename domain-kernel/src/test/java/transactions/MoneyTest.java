package transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {
    @Test
    public void should_hold_value() {
        Money money = Money.of(100d);
        assertEquals(Double.valueOf(100d), money.getValue());
    }

    @Test
    public void should_plus_immutable() {
        Money money = Money.of(100d);
        Money increasedMoney = money.plus(Money.of(50d));
        assertEquals(Double.valueOf(100d), money.getValue());
        assertEquals(Double.valueOf(150d), increasedMoney.getValue());
    }

    @Test
    public void should_be_equal_other_instance_with_same_valued() {
        Money money1 = Money.of(100d);
        Money money2 = Money.of(100d);
        assertEquals(money1, money2);
    }

}