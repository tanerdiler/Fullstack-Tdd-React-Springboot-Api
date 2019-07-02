package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.Money;
import jugistanbul.tdd.transactions.Product;
import jugistanbul.tdd.transactions.PurchasingAgent;
import org.junit.jupiter.api.Test;

import static jugistanbul.tdd.transactions.TransactionState.APPROVED;
import static jugistanbul.tdd.transactions.TransactionState.UNAPPROVED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionSaveCommandTest {

    @Test
    public void should_save_transaction() {
        var input = new TransactionSaveInput();
        input.setFirstname("John");
        input.setLastname("Doe");
        input.setEmail("john.doe@gmail.com");
        input.setTransactionCode("TR123");
        input.setProductPrice(123d);
        input.setProductName("IPhone X");

        var accounting = new Accounting(Money.of(100d));
        var saveCommand = new TransactionSaveCommand(accounting);
        var output = saveCommand.exec(input);

        assertEquals(Integer.valueOf(1), output.getTransactionId());
    }

    @Test
    public void should_save_approved_transaction() {
        var accounting = new Accounting(Money.of(300d));
        var saveCommand = new TransactionSaveCommand(accounting);

        var input1 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR123",100d,"USB Disc");
        var input2 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR124",150d,"Flash Disc");
        var input3 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR125",60d,"Hard Disc");
        var input4 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR126",50d,"Hard Disc 7200");

        var output1 = saveCommand.exec(input1);
        var output2 = saveCommand.exec(input2);
        var output3 = saveCommand.exec(input3);
        var output4 = saveCommand.exec(input4);

        assertEquals(APPROVED, output1.getState());
        assertEquals(APPROVED, output2.getState());
        assertEquals(UNAPPROVED, output3.getState());
        assertEquals(APPROVED, output4.getState());
    }
}
