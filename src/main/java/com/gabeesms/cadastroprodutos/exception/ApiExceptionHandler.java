package com.gabeesms.cadastroprodutos.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception) {
        return criarResposta(HttpStatus.NOT_FOUND, exception.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new TreeMap<>();
        exception.getBindingResult().getFieldErrors().forEach(erro ->
                erros.putIfAbsent(erro.getField(), erro.getDefaultMessage()));

        return criarResposta(HttpStatus.BAD_REQUEST, "Um ou mais campos são inválidos", erros);
    }

    private ResponseEntity<ApiError> criarResposta(
            HttpStatus status, String mensagem, Map<String, String> erros) {
        ApiError resposta = new ApiError(
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem, erros);
        return ResponseEntity.status(status).body(resposta);
    }

    public record ApiError(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            Map<String, String> fieldErrors) {
    }
}
