package jugistanbul.tdd.applicationlayer;

public class TransactionSaveInput {

    private String firstname;

    private String lastname;

    private String email;

    private String transactionCode;

    private double productPrice;

    private String productName;

    public TransactionSaveInput() {

    }

    public TransactionSaveInput(String firstname, String lastname, String email, String transactionCode, double productPrice, String productName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.transactionCode = transactionCode;
        this.productPrice = productPrice;
        this.productName = productName;
    }

    public void setFirstname(String firstname) {

        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getFirstname() {
        return firstname;
    }
}
