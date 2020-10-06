package com.example.demo.domain.service;

import java.util.Optional;

import com.example.demo.domain.model.Comentario;

public interface IOrdemServico<T> extends IService<T>{
	
	public Optional<Comentario> adicionarComentario(final Long ordemServicoId,String descricao);
	
	public Optional<T> finalizarOrdemServico(final Long ordemServicoId);
	
	public Optional<T> cancelarOrdemServico(final Long ordemServicoId);

}
