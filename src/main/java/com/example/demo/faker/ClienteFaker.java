package com.example.demo.faker;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.github.javafaker.Faker;

@Component
public class ClienteFaker implements FakerInterface{
	
    private static final Logger log = LoggerFactory.getLogger(ClienteFaker.class);

	@Autowired
	private ClienteRepository clienteRepository;
	
	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	public ClienteFaker() {
		super();
	}
	
	@Override
	public void faker(Faker faker,Long amount) {
        
		log.info("Faker => ClientFaker :: START");

		for(int i = 0 ; i < amount;i++) {
			var cliente = new Cliente();
			
			cliente.setNome(faker.name().fullName());
			cliente.setEmail(faker.internet().safeEmailAddress());
			cliente.setTelefone(faker.phoneNumber().phoneNumber());
			
			this.clientes.add(cliente);
			
		}
		
		this.clienteRepository.saveAll(this.clientes);

		log.info("Faker => ClientFaker :: FINISH");

	}

}
