package com.xproj.SwitchPay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SwitchPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwitchPayApplication.class, args);
	}

}
