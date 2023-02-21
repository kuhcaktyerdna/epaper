package com.example.epaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class EpaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpaperApplication.class, args);
	}

}
