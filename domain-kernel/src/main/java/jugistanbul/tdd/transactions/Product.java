package jugistanbul.tdd.transactions;

public class Product {

    private final String name;

    private final double price;

    private Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public static ProductBuilder aNew() {
        return new ProductBuilder();
    }

    public double getPrice() {
        return price;
    }

    public static class ProductBuilder {

        private String name;

        private double price;

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public Product get() {
            return new Product(name, price);
        }
    }
}
