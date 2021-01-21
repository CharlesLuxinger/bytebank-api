package com.github.charlesluxinger.bytebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.github.charlesluxinger.bytbank")
@EntityScan(basePackages = "com.github.charlesluxinger.bytbank")
public class ByteBankChallengeApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ByteBankChallengeApplication.class, args);
	}

}
