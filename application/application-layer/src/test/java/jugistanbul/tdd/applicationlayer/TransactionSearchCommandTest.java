package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.Money;
import jugistanbul.tdd.transactions.PurchasingAgent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionSearchCommandTest {

    @Test
    public void should_return_all_transactions_grouped_by_agent() {
        var accounting = new Accounting(Money.of(300d));
        var saveCommand = new TransactionSaveCommand(accounting);

        var input1 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR123",100d,"USB Disc");
        var input2 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR124",150d,"Flash Disc");
        var input3 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR125",60d,"Hard Disc");
        var input4 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR126",50d,"Hard Disc 7200");

        var input5 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR127",100d,"USB Disc");
        var input6 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR128",150d,"Flash Disc");
        var input7 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR129",60d,"Hard Disc");
        var input8 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR130",50d,"Hard Disc 7200");

        saveCommand.exec(input1);
        saveCommand.exec(input2);
        saveCommand.exec(input3);
        saveCommand.exec(input4);

        saveCommand.exec(input5);
        saveCommand.exec(input6);
        saveCommand.exec(input7);
        saveCommand.exec(input8);

        var searchInput = new TransactionSearchInput();
        var searchCommand = new TransactionSearchCommand(accounting);
        var output = searchCommand.exec(searchInput);
        assertEquals(2, output.getAgentsCount());
        assertEquals(8, output.getTransactionsCount());
    }

    @Test
    public void should_return_all_transactions_of_an_agent() {
        var accounting = new Accounting(Money.of(300d));
        var saveCommand = new TransactionSaveCommand(accounting);

        var input1 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR123",100d,"USB Disc");
        var input2 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR124",150d,"Flash Disc");
        var input3 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR125",60d,"Hard Disc");
        var input4 = new TransactionSaveInput("John","Doe","john.doe@gmail.com","TR126",50d,"Hard Disc 7200");

        var input5 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR127",100d,"USB Disc");
        var input6 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR128",150d,"Flash Disc");
        var input7 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR129",60d,"Hard Disc");
        var input8 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR130",50d,"Hard Disc 7200");
        var input9 = new TransactionSaveInput("Mary","Doe","mary.doe@gmail.com","TR131",50d,"Hard Disc 7200");

        saveCommand.exec(input1);
        saveCommand.exec(input2);
        saveCommand.exec(input3);
        saveCommand.exec(input4);

        saveCommand.exec(input5);
        saveCommand.exec(input6);
        saveCommand.exec(input7);
        saveCommand.exec(input8);
        saveCommand.exec(input9);

        PurchasingAgent agent = PurchasingAgent.aNew().firstname("Mary").lastname("Doe").email("mary.doe@gmail.com").get();

        var searchInput = new TransactionSearchInput();
        searchInput.setAgentToSearch(agent);
        var searchCommand = new TransactionSearchCommand(accounting);
        var output = searchCommand.exec(searchInput);
        assertEquals(1, output.getAgentsCount());
        assertEquals(5, output.getTransactionsCount());
    }

}
