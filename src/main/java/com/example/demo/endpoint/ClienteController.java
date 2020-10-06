package com.example.demo.endpoint;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll(){
		return ResponseEntity.ok(this.clienteService.findAll());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Cliente> findResource(@PathVariable final Long id){
		try {
			Optional<Cliente> cliente = this.clienteService.findById(id);
			return ResponseEntity.ok(cliente.get());

		}catch(ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping 
    @Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Cliente> create(@Valid @RequestBody final Cliente cliente) {
		try {
			Optional<Cliente> persistCliente = this.clienteService.save(cliente);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(persistCliente.get().getId()).toUri();
			return ResponseEntity.created(location).body(persistCliente.get());
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") final Long id,@Valid @RequestBody final Cliente cliente) {
		try {
			Optional<Cliente> updateCliente = this.clienteService.update(id,cliente);
			return ResponseEntity.ok(updateCliente.get());
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Long id){
		try {
			this.clienteService.delete(id);
			return ResponseEntity.noContent().build();
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
