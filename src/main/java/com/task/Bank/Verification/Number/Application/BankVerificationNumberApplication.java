package com.task.Bank.Verification.Number.Application;

//import com.task.Bank.Verification.Number.Application.configs.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableConfigurationProperties(value = {AppProperties.class})
public class BankVerificationNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankVerificationNumberApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
