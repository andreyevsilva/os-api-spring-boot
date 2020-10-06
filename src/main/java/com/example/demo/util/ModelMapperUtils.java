package com.example.demo.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.model.OrdemServicoInput;
import com.example.demo.domain.model.dto.OrdemServicoDTO;

public class ModelMapperUtils {
	
	@Autowired
	private static ModelMapper modelMapper;
	
	public static OrdemServicoDTO toModel(final OrdemServico ordemServico) {
		return modelMapper.map(ordemServico,OrdemServicoDTO.class);
	}
	
	public static List<OrdemServicoDTO> toCollectionModel(final List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	public static OrdemServico toEntity(final OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput,OrdemServico.class);
	}
}
