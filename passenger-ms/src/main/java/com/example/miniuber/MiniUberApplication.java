package com.example.miniuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MiniUberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniUberApplication.class, args);
	}

}
