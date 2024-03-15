package com.gokdenizozkan.ddd.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DddGeneralServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DddGeneralServiceApplication.class, args);
	}

}
