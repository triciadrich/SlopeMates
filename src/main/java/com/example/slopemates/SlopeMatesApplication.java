package com.example.slopemates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlopeMatesApplication {

	public static void main(String[] args) {
		System.out.println("before run");
		SpringApplication.run(SlopeMatesApplication.class, args);
		System.out.println("after run");
	}

}
