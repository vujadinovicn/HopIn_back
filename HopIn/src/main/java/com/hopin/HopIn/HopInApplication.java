package com.hopin.HopIn;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HopInApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopInApplication.class, args);
	}

}
