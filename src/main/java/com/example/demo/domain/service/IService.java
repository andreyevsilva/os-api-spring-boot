package com.example.demo.domain.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.model.Cliente;

public interface IService<T> {

	public List<T> findAll();

	public Optional<T> findById(Long id);
	
	public Optional<T> save(T object);
	
	public Optional<T> update(Long id, T object);
	
	public void delete(Long id);
	
	public void existsById(Long id);
}
