package com.example.demo.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteService implements IClienteService<Cliente> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}
	
	@Override
	public Optional<Cliente> findById(Long id) {
		this.existsById(id);
		return this.clienteRepository.findById(id);
	}
	
	@Override
	public Optional<Cliente> save(Cliente cliente) {
		return Optional.ofNullable(this.clienteRepository.save(cliente));
	}

	@Override
	public Optional<Cliente> update(Long id, Cliente cliente) {
		this.existsById(id);
		cliente.setId(id);
		return Optional.ofNullable(this.clienteRepository.save(cliente));
	}
	
	@Override
	public void delete(Long id) {
		this.existsById(id);
		this.clienteRepository.deleteById(id);
	}
	
	@Override
	public void existsById(Long id) {
		this.clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente not found for ID: " + id));
	}
}
