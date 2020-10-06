package com.example.demo.seeders;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.faker.ClienteFaker;
import com.github.javafaker.Faker;

@Component
public class ClienteSeeder implements SeederInterface{
	
    private static final Logger log = LoggerFactory.getLogger(ClienteSeeder.class);
    
	@Autowired
	private ClienteFaker clienteFaker;
	
	public ClienteSeeder() {
		super();
	}
	
	@Override
	public void seed() {
	
        log.info("Seeding Client Table :: START");
        
		Faker faker = new Faker(new Locale("pt-BR"));
		this.clienteFaker.faker(faker,100L);
		
        log.info("Seeding Client Table :: FINISH");
	}
	
}
