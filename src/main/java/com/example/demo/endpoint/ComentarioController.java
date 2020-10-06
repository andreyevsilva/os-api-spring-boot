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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.model.Comentario;
import com.example.demo.domain.model.ComentarioInput;
import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.model.dto.ComentarioDTO;
import com.example.demo.domain.service.OrdemServicoService;

@RestController
@RequestMapping("ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("{/ordemServicoId}")
	public ResponseEntity<List<ComentarioDTO>> findAll(@PathVariable final Long ordemServiceId){
		Optional<OrdemServico> ordemServico = this.ordemServicoService.findById(ordemServiceId);
		return ResponseEntity.ok(this.toCollectionModel(ordemServico.get().getComentarios()));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
	public ResponseEntity<ComentarioDTO> create(@PathVariable final Long ordemServicoId,@Valid @RequestBody final ComentarioInput comentarioInput) {
		
		try {
			Optional<Comentario> comentario = this.ordemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
			ComentarioDTO comentarioDTO = this.toModel(comentario.get());
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comentarioDTO.getId()).toUri();
			return ResponseEntity.created(location).body(comentarioDTO);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	public ComentarioDTO toModel(final Comentario comentario) {
		return modelMapper.map(comentario,ComentarioDTO.class);
	}
	
	public List<ComentarioDTO> toCollectionModel(final List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}
	
	public Comentario toEntity(final ComentarioInput comentarioInput) {
		return modelMapper.map(comentarioInput,Comentario.class);
	}
}
