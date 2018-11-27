package com.example.springjerseyrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJerseyRestApplication {

	public  enum ROLES{ADMIN,NONADMIN}

	public static void main(String[] args) {
		SpringApplication.run(SpringJerseyRestApplication.class, args);
	}
}
