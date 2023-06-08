package al.brain.saleforce.restsaleforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RestSaleforceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSaleforceApplication.class, args);
	}

}
