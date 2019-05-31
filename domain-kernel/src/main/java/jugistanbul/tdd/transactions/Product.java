package jugistanbul.tdd.transactions;

public class Product {

    private final String name;

    private final Money price;

    public Product(String name, Money price) {

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public static Builder aNew() {
        return new Builder();
    }

    public static class Builder {

        private String name;

        private Money price;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Money price) {
            this.price = price;
            return this;
        }

        public Product get() {
            if (name == null) {
                throw new PropertyRequiredException("Product", "name");
            } else
            if (price == null) {
                throw new PropertyRequiredException("Product", "price");
            }
            return new Product(name, price);
        }
    }
}
