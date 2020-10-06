package com.example.demo.endpoint;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.model.OrdemServicoInput;
import com.example.demo.domain.model.dto.OrdemServicoDTO;
import com.example.demo.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<OrdemServicoDTO>> findAll(){
		return ResponseEntity.ok(this.toCollectionModel(this.ordemServicoService.findAll()));
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<OrdemServicoDTO> findResource(@PathVariable final Long id){
		try {
			OrdemServicoDTO cliente = modelMapper.map(this.ordemServicoService.findById(id).get(), OrdemServicoDTO.class);
			return ResponseEntity.ok(cliente);

		}catch(ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
	public ResponseEntity<OrdemServicoDTO> create (@Valid @RequestBody final OrdemServicoInput ordemServicoInput){
		try {
			OrdemServico ordemServico = this.toEntity(ordemServicoInput);
			OrdemServicoDTO persistOrdemServico = this.toModel(this.ordemServicoService.save(ordemServico).get());
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(persistOrdemServico.getId()).toUri();
			return ResponseEntity.created(location).body(persistOrdemServico);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<OrdemServicoDTO> finalizarOrdemServico(@PathVariable final Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = this.ordemServicoService.finalizarOrdemServico(ordemServicoId);
		return ResponseEntity.ok(this.toModel(ordemServico.get()));
	}
	
	private OrdemServicoDTO toModel(final OrdemServico ordemServico) {
		return modelMapper.map(ordemServico,OrdemServicoDTO.class);
	}
	
	private List<OrdemServicoDTO> toCollectionModel(final List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(final OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput,OrdemServico.class);
	}
}
