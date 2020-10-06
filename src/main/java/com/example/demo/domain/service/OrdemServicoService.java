package com.example.demo.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.exception.BadRequestException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Comentario;
import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.model.StatusOrdemServico;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ComentarioRepository;
import com.example.demo.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService implements IOrdemServico<OrdemServico> {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Override
	public List<OrdemServico> findAll() {
		return this.ordemServicoRepository.findAll();
	}

	@Override
	public Optional<OrdemServico> findById(Long id) {
		return Optional.ofNullable(this.ordemServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrdemServico not found for ID: " + id)));
	}

	@Override
	public Optional<OrdemServico> save(OrdemServico ordemServico) {
		Optional<Cliente> cliente = Optional.ofNullable(this.clienteRepository
				.findById(ordemServico.getCliente().getId()).orElseThrow(() -> new BadRequestException(
						"Cliente not found for ID: " + ordemServico.getCliente().getId())));

		ordemServico.setCliente(cliente.get());
		ordemServico.setOrdemStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return Optional.ofNullable(this.ordemServicoRepository.save(ordemServico));
	}

	@Override
	public Optional<OrdemServico> update(Long id, OrdemServico object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void existsById(Long id) {
		this.ordemServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrdemServico not found for ID: " + id));
	}

	@Override
	public Optional<Comentario> adicionarComentario(Long id, String descricao) {
		Optional<OrdemServico> ordemServico = Optional.ofNullable(this.ordemServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço não Encontrada")));

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico.get());

		return Optional.ofNullable(this.comentarioRepository.save(comentario));
	}

	@Override
	public Optional<OrdemServico> cancelarOrdemServico(final Long ordemServicoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OrdemServico> finalizarOrdemServico(final Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = Optional.ofNullable(this.findById(ordemServicoId).orElseThrow(()->new ResourceNotFoundException("Ordem de Serviço não Encontrada")));
		ordemServico.get().finalizarOrdemServico();
		return ordemServico;
	}
}
