package com.inkly.inkly_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class InklyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InklyBackendApplication.class, args);
	}

}
