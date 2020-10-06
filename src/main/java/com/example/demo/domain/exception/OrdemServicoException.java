package com.example.demo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrdemServicoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5542953241698964596L;

	public OrdemServicoException(String message) {
	        super(message);
	  
	}
}