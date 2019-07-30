package com.flyht.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.flyht.test.service.ITwitterService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TestApplication implements CommandLineRunner {

	
	@Autowired
	private ITwitterService ts;
	
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Entering console");
		
		ts.initConsole();
		
		log.info("Exiting console");
		
	}

}
