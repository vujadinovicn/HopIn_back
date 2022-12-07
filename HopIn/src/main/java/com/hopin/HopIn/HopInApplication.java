package com.hopin.HopIn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HopInApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopInApplication.class, args);
	}

}
