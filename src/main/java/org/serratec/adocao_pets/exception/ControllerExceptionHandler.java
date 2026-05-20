package org.serratec.adocao_pets.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

        private final String regiao = "America/Sao_Paulo";

        @Override // captura erros de validação do @Valid
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                List<String> erros = new ArrayList<>();

                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        erros.add(error.getField() + ": " + error.getDefaultMessage());
                }

                ErroResposta erroResposta = new ErroResposta(
                                status.value(),
                                "Existem campos inválidos!", LocalDateTime.now(ZoneId.of(regiao)), erros);

                return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
        }

        @ExceptionHandler(RecursoNaoEncontradoException.class) // captura quando um recurso não é achado no banco(404)
        public ResponseEntity<Object> handleRecursoNaoEncontrado(
                        RecursoNaoEncontradoException ex,
                        WebRequest request) {

                HttpStatus status = HttpStatus.NOT_FOUND;
                List<String> erros = new ArrayList<>();

                erros.add(ex.getMessage());

                ErroResposta erroResposta = new ErroResposta(
                                status.value(), "Recurso não encontrado!", LocalDateTime.now(ZoneId.of(regiao)), erros);

                return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(EnumValidationException.class) // captura as exceções personalizadas dos ENUM (400)
        public ResponseEntity<Object> handleEnumValidation(EnumValidationException ex, WebRequest request) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                List<String> erros = new ArrayList<>();

                erros.add(ex.getMessage());

                ErroResposta erroResposta = new ErroResposta(
                                status.value(), "ENUM inválido, valor de campo incorreto!",
                                LocalDateTime.now(ZoneId.of(regiao)),
                                erros);

                return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);
        }

        @Override // método para capturar erros na inserção de valores no JSON no geral
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                List<String> erros = List.of(ex.getMostSpecificCause().getMessage());

                ErroResposta erroResposta = new ErroResposta(
                                status.value(),
                                "Erro na leitura do corpo da requisição (JSON inválido)",
                                LocalDateTime.now(ZoneId.of(regiao)),
                                erros);

                return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
        }

        @ExceptionHandler(Exception.class) // método para captura de erro no servidor - 500
        public ResponseEntity<Object> handleErroInesperado(Exception ex, WebRequest request) {
                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                List<String> erros = List.of("Ocorreu um erro interno inesperado no servidor. Tente mais tarde.");

                ErroResposta erroResposta = new ErroResposta(
                                status.value(),
                                "Erro Interno do Servidor",
                                LocalDateTime.now(ZoneId.of(regiao)),
                                erros);

                return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);
        }

}
