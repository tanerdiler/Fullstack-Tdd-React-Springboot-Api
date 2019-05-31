package jugistanbul.tdd.transactions;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountingTest {

    @Test
    public void should_calc_total_price_of_transactions_of_an_agent(){
        var accounting = new Accounting(Money.of(5000d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(400d)).get()).withCode("TR125").get();


        var agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        var trx4 = agent2.buy(Product.aNew().name("USB Disc").price(Money.of(300d)).get()).withCode("TR123").get();
        var trx5 = agent2.buy(Product.aNew().name("Flash Disc").price(Money.of(500d)).get()).withCode("TR124").get();
        var trx6 = agent2.buy(Product.aNew().name("Hard Disc").price(Money.of(800d)).get()).withCode("TR125").get();

        accounting.save(trx1);
        accounting.save(trx2);
        accounting.save(trx3);
        accounting.save(trx4);
        accounting.save(trx5);
        accounting.save(trx6);

        assertEquals(Money.of(700d), accounting.calcTotalPriceOfAgentTransactions(agent1));
        assertEquals(Money.of(1600d), accounting.calcTotalPriceOfAgentTransactions(agent2));
    }

    @Test
    public void should_unapprove_transaction_if_it_makes_total_price_higher_than_limit(){
        var accounting = new Accounting(Money.of(300d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(150d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(60d)).get()).withCode("TR125").get();
        var trx4 = agent1.buy(Product.aNew().name("Hard Disc 7200").price(Money.of(50d)).get()).withCode("TR126").get();

        var isTrx1Approved = accounting.save(trx1);
        var isTrx2Approved = accounting.save(trx2);
        var isTrx3Approved = accounting.save(trx3);
        var isTrx4Approved = accounting.save(trx4);

        assertTrue(isTrx1Approved);
        assertTrue(isTrx2Approved);
        assertFalse(isTrx3Approved);
        assertTrue(isTrx4Approved);
    }

    @Test
    public void should_calc_total_price_of_approved_transactions_of_an_agent(){
        var accounting = new Accounting(Money.of(400d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(400d)).get()).withCode("TR125").get();


        var agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        var trx4 = agent2.buy(Product.aNew().name("USB Disc").price(Money.of(300d)).get()).withCode("TR123").get();
        var trx5 = agent2.buy(Product.aNew().name("Flash Disc").price(Money.of(500d)).get()).withCode("TR124").get();
        var trx6 = agent2.buy(Product.aNew().name("Hard Disc").price(Money.of(800d)).get()).withCode("TR125").get();

        accounting.save(trx1);
        accounting.save(trx2);
        accounting.save(trx3);
        accounting.save(trx4);
        accounting.save(trx5);
        accounting.save(trx6);

        assertEquals(Money.of(300d), accounting.calcTotalPriceOfAgentTransactions(agent1));
        assertEquals(Money.of(300d), accounting.calcTotalPriceOfAgentTransactions(agent2));
    }


    @Test
    public void should_return_all_transactions() {
        var accounting = new Accounting(Money.of(400d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(400d)).get()).withCode("TR125").get();


        var agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        var trx4 = agent2.buy(Product.aNew().name("USB Disc").price(Money.of(300d)).get()).withCode("TR123").get();
        var trx5 = agent2.buy(Product.aNew().name("Flash Disc").price(Money.of(500d)).get()).withCode("TR124").get();
        var trx6 = agent2.buy(Product.aNew().name("Hard Disc").price(Money.of(800d)).get()).withCode("TR125").get();

        accounting.save(trx1);
        accounting.save(trx2);
        accounting.save(trx3);
        accounting.save(trx4);
        accounting.save(trx5);
        accounting.save(trx6);

        Map<PurchasingAgent, List<Transaction>> transactions = accounting.getTransactions();
        assertEquals(2, transactions.size());
        assertEquals(6, transactions.values().stream().mapToInt(l->l.size()).sum());
    }

    @Test
    public void should_return_transactions_of_agent() {
        var accounting = new Accounting(Money.of(400d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(400d)).get()).withCode("TR125").get();


        var agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        var trx4 = agent2.buy(Product.aNew().name("USB Disc").price(Money.of(300d)).get()).withCode("TR123").get();
        var trx5 = agent2.buy(Product.aNew().name("Flash Disc").price(Money.of(500d)).get()).withCode("TR124").get();
        var trx6 = agent2.buy(Product.aNew().name("Hard Disc").price(Money.of(800d)).get()).withCode("TR125").get();

        accounting.save(trx1);
        accounting.save(trx2);
        accounting.save(trx3);
        accounting.save(trx4);
        accounting.save(trx5);
        accounting.save(trx6);

        Map<PurchasingAgent, List<Transaction>> transactions = accounting.getTransactionsOf(agent1);
        assertEquals(3, transactions.get(agent1).size());
    }

    @Test
    public void should_return_empty_list_when_agent_not_found() {
        var accounting = new Accounting(Money.of(400d));

        var agent1 = PurchasingAgent.aNew().firstname("john").lastname("doe").email("john_doe@gmail.com").get();
        var trx1 = agent1.buy(Product.aNew().name("USB Disc").price(Money.of(100d)).get()).withCode("TR123").get();
        var trx2 = agent1.buy(Product.aNew().name("Flash Disc").price(Money.of(200d)).get()).withCode("TR124").get();
        var trx3 = agent1.buy(Product.aNew().name("Hard Disc").price(Money.of(400d)).get()).withCode("TR125").get();


        var agent2 = PurchasingAgent.aNew().firstname("mary").lastname("doe").email("john_doe@gmail.com").get();
        var trx4 = agent2.buy(Product.aNew().name("USB Disc").price(Money.of(300d)).get()).withCode("TR123").get();
        var trx5 = agent2.buy(Product.aNew().name("Flash Disc").price(Money.of(500d)).get()).withCode("TR124").get();
        var trx6 = agent2.buy(Product.aNew().name("Hard Disc").price(Money.of(800d)).get()).withCode("TR125").get();

        var agent3 = PurchasingAgent.aNew().firstname("jack").lastname("doe").email("jack_doe@gmail.com").get();

        accounting.save(trx1);
        accounting.save(trx2);
        accounting.save(trx3);
        accounting.save(trx4);
        accounting.save(trx5);
        accounting.save(trx6);

        Map<PurchasingAgent, List<Transaction>> transactions = accounting.getTransactionsOf(agent3);
        assertEquals(0, transactions.get(agent3).size());
    }
}
