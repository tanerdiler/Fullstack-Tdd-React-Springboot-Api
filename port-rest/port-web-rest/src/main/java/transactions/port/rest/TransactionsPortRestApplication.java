package transactions.port.rest;

import jugistanbul.tdd.applicationlayer.TransactionSaveCommand;
import jugistanbul.tdd.applicationlayer.TransactionSearchCommand;
import jugistanbul.tdd.transactions.Accounting;
import jugistanbul.tdd.transactions.Money;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransactionsPortRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsPortRestApplication.class, args);
	}

	@Bean
	public Accounting getAccounting() {
		return new Accounting(Money.of(300d));
	}

	@Bean
	public TransactionSaveCommand getSaveCommand(Accounting accounting) {
		return new TransactionSaveCommand(accounting);
	}

	@Bean
	public TransactionSearchCommand getSearchCommand(Accounting accounting) {
		return new TransactionSearchCommand(accounting);
	}
}
