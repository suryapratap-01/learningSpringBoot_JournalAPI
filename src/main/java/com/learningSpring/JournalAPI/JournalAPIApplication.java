package com.learningSpring.JournalAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAPIApplication.class, args);
	}
}
