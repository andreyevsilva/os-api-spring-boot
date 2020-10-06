package com.example.demo.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private ClienteSeeder clienteSeeder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.clienteSeeder.seed();
	}
	
}
