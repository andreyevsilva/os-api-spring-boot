package com.example.demo.domain.handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.domain.exception.BadRequestException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.error.BadRequestErrorDetails;
import com.example.demo.error.ResourceNotFoundErrorDetails;
import com.example.demo.error.ValidationErrorDetails;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
    private static final Logger Log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException rfnException,HttpHeaders headers, HttpStatus status,WebRequest request) {
		ResourceNotFoundErrorDetails resourceNotFoundErrorDetails = ResourceNotFoundErrorDetails.Builder
        		.newBuilder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail("Recurso não Encontrado")
                .developerMessage(rfnException.getClass().getName())
                .build();
        
        return super.handleExceptionInternal(rfnException, resourceNotFoundErrorDetails, headers, status, request); 
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestParameterNotValid(BadRequestException bRException,HttpHeaders headers, HttpStatus status,WebRequest request) {	
		
		Log.info(bRException.getLocalizedMessage());
		
		BadRequestErrorDetails badRequestErrorDetails = BadRequestErrorDetails.Builder
        		.newBuilder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Parametro Inválido")
                .detail("Recurso não encontrado de acordo com o parâmentro enviado.")
                .developerMessage(bRException.getClass().getName())
                .build();
        
        return super.handleExceptionInternal(bRException, badRequestErrorDetails, headers, status, request); 
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		    List<String> fields = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
		    List<String> fieldMessages = ex.getBindingResult().getFieldErrors().stream().map(error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())).collect(Collectors.toList());
	        		    
	        ValidationErrorDetails validationErrorDetails = ValidationErrorDetails.Builder
	                .newBuilder()
	                .timestamp(OffsetDateTime.now())
	                .status(HttpStatus.BAD_REQUEST.value())
	                .title("Erro de Validação")
	                .detail("Um ou mais Campos estão inválidos")
	                .developerMessage(ex.getClass().getName())
	                .fields(fields)
	                .fieldsMessages(fieldMessages)
	                .build();
	        
	        return super.handleExceptionInternal(ex, validationErrorDetails, headers, status, request);	
	}
}
