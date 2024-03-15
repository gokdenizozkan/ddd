package com.gokdenizozkan.ddd.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DddMainService {

	public static void main(String[] args) {
		SpringApplication.run(DddMainService.class, args);
	}

}
