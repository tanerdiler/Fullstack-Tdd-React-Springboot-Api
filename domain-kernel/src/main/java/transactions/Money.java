package transactions;

import java.util.Objects;

public class Money {
    private Double value;

    private Money (Double value) {

        this.value = value;
    }

    public static Money of(Double value) {
        return new Money(value);
    }

    public Money plus(Money money) {
        return Money.of(value+money.value);
    }

    public boolean isGreaterThan(Money money) {
        return value > money.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    public Double getValue() {
        return value;
    }
}