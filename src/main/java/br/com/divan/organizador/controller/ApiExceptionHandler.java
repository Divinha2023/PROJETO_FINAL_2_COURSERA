package br.com.divan.organizador.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.divan.organizador.service.RecursoNaoEncontradoException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroApi> tratarNaoEncontrado(RecursoNaoEncontradoException exception) {
		return resposta(HttpStatus.NOT_FOUND, exception.getMessage(), Map.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroApi> tratarValidacao(MethodArgumentNotValidException exception) {
		Map<String, String> campos = new LinkedHashMap<>();
		for (FieldError erro : exception.getBindingResult().getFieldErrors()) {
			campos.put(erro.getField(), erro.getDefaultMessage());
		}
		return resposta(HttpStatus.BAD_REQUEST, "Existem campos inválidos.", campos);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
	public ResponseEntity<ErroApi> tratarRequisicaoInvalida(Exception exception) {
		return resposta(HttpStatus.BAD_REQUEST, "Confira os valores enviados na requisição.", Map.of());
	}

	private ResponseEntity<ErroApi> resposta(HttpStatus status, String mensagem, Map<String, String> campos) {
		ErroApi erro = new ErroApi(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem, campos);
		return ResponseEntity.status(status).body(erro);
	}

	public record ErroApi(
			LocalDateTime momento,
			int status,
			String erro,
			String mensagem,
			Map<String, String> campos) {
	}
}
