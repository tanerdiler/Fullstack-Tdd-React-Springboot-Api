package transactions.port.stubrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;

@SpringBootApplication
@EnableStubRunnerServer
public class DemoStubRunnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoStubRunnerApplication.class, args);
	}

}
