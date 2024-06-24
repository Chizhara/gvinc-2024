package com.gnivc.portalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class PortalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalServiceApplication.class, args);
	}

}
