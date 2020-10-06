package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.example.demo.domain.ValidationGroups;
import com.example.demo.domain.exception.OrdemServicoException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from= Default.class, to=ValidationGroups.ClienteId.class)
	@ManyToOne
	@NotNull
	private Cliente cliente;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal preco;
	
	@Enumerated(EnumType.STRING)
	private StatusOrdemServico ordemStatus;
	
	private OffsetDateTime dataAbertura;
	
	private OffsetDateTime dataFinalização;
	
	@OneToMany(mappedBy = "ordemServico", targetEntity = Comentario.class, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public StatusOrdemServico getOrdemStatus() {
		return ordemStatus;
	}

	public void setOrdemStatus(StatusOrdemServico ordemStatus) {
		this.ordemStatus = ordemStatus;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public OffsetDateTime getDataFinalização() {
		return dataFinalização;
	}

	public void setDataFinalização(OffsetDateTime dataFinalização) {
		this.dataFinalização = dataFinalização;
	}
	
	/**
	 * Finalizar OrdemServico
	 */
	public void finalizarOrdemServico() {
		if(!this.podeSerFinalizada()) {
			throw new OrdemServicoException("Ordem de Servico não pode ser Finalizada");
		}
		this.setOrdemStatus(StatusOrdemServico.FINALIZADA);
		this.setDataFinalização(OffsetDateTime.now());
	}
	
	/**
	 * Cancelar OrdemServico
	 */
	public void cancelarOrdemServico() {
		if(!this.podeSerCancelada()) {
			throw new OrdemServicoException("Ordem de Servico não pode ser Cancelada");
		}
		this.setOrdemStatus(StatusOrdemServico.CANCELADA);
		this.setDataFinalização(OffsetDateTime.now());
	}
	
	
	public boolean podeSerFinalizada() {
		return this.getOrdemStatus().equals(StatusOrdemServico.ABERTA);
	}
	
	public boolean podeSerCancelada() {
		return this.getOrdemStatus().equals(StatusOrdemServico.CANCELADA);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
