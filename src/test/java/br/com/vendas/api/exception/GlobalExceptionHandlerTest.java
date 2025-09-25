package br.com.vendas.api.exception;

import br.com.vendas.api.enums.ErrorEnum;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve tratar ClienteNotFoundException")
    void testHandleClienteNotFound() {
        ClienteNotFoundException exception = new ClienteNotFoundException(1L);
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleClienteNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.CLIENTE_NAO_ENCONTRADO, response.getBody().codigo());
        assertEquals(ErrorEnum.CLIENTE_NAO_ENCONTRADO.getMessage(), response.getBody().mensagem());
        assertNotNull(response.getBody().timestamp());
        assertNull(response.getBody().detalhes());
    }

    @Test
    @DisplayName("Deve tratar FilialNotFoundException")
    void testHandleFilialNotFound() {
        FilialNotFoundException exception = new FilialNotFoundException(1L);
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleFilialNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.FILIAL_NAO_ENCONTRADA, response.getBody().codigo());
        assertEquals(ErrorEnum.FILIAL_NAO_ENCONTRADA.getMessage(), response.getBody().mensagem());
    }

    @Test
    @DisplayName("Deve tratar VendaNotFoundException")
    void testHandleVendaNotFound() {
        VendaNotFoundException exception = new VendaNotFoundException(1L);
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleVendaNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.VENDA_NAO_ENCONTRADA, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar VendaDuplicadaException")
    void testHandleVendaDuplicada() {
        VendaDuplicadaException exception = new VendaDuplicadaException("V001");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleVendaDuplicada(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.VENDA_DUPLICADA, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar RegraDeNegocioException")
    void testHandleRegraDeNegocio() {
        RegraDeNegocioException exception = new RegraDeNegocioException("Regra violada");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleRegraDeNegocio(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.REGRA_DE_NEGOCIO_VIOLADA, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException")
    void testHandleValidationExceptions() throws Exception {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("object", "campo", "deve ser válido");
        
        when(exception.getBindingResult()).thenReturn(mock(org.springframework.validation.BindingResult.class));
        when(exception.getBindingResult().getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleValidationExceptions(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.DADOS_INVALIDOS, response.getBody().codigo());
        assertNotNull(response.getBody().detalhes());
    }

    @Test
    @DisplayName("Deve tratar HttpMessageNotReadableException")
    void testHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("JSON inválido");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleHttpMessageNotReadable(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.FORMATO_JSON_INVALIDO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentTypeMismatchException")
    void testHandleTypeMismatch() {
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getMessage()).thenReturn("Tipo inválido");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleTypeMismatch(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.TIPO_PARAMETRO_INVALIDO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar MissingServletRequestParameterException")
    void testHandleMissingParameter() {
        MissingServletRequestParameterException exception = 
            new MissingServletRequestParameterException("parametro", "String");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleMissingParameter(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.PARAMETRO_OBRIGATORIO_AUSENTE, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar HttpRequestMethodNotSupportedException")
    void testHandleMethodNotSupported() {
        HttpRequestMethodNotSupportedException exception = 
            new HttpRequestMethodNotSupportedException("PUT", List.of(new String[]{"GET", "POST"}));
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleMethodNotSupported(exception);
        
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.METODO_NAO_SUPORTADO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar NoHandlerFoundException")
    void testHandleNoHandlerFound() {
        NoHandlerFoundException exception = mock(NoHandlerFoundException.class);
        when(exception.getMessage()).thenReturn("Endpoint não encontrado");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleNoHandlerFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.ENDPOINT_NAO_ENCONTRADO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar DataIntegrityViolationException - violação única")
    void testHandleDataIntegrityViolationUnique() {
        DataIntegrityViolationException exception = 
            new DataIntegrityViolationException("unique constraint violation");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleDataIntegrityViolation(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.DADOS_DUPLICADOS, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar DataIntegrityViolationException - chave estrangeira")
    void testHandleDataIntegrityViolationForeignKey() {
        DataIntegrityViolationException exception = 
            new DataIntegrityViolationException("foreign key constraint violation");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleDataIntegrityViolation(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.REFERENCIA_INVALIDA, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar DataIntegrityViolationException - genérica")
    void testHandleDataIntegrityViolationGeneric() {
        DataIntegrityViolationException exception = 
            new DataIntegrityViolationException("generic violation");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleDataIntegrityViolation(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.VIOLACAO_INTEGRIDADE_DADOS, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar IllegalArgumentException")
    void testHandleIllegalArgument() {
        IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleIllegalArgument(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.ARGUMENTO_INVALIDO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve tratar Exception genérica")
    void testHandleGenericException() {
        Exception exception = new Exception("Erro interno");
        
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = 
            exceptionHandler.handleGenericException(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorEnum.ERRO_INTERNO, response.getBody().codigo());
    }

    @Test
    @DisplayName("Deve criar ErrorResponse com detalhes")
    void testErrorResponseWithDetails() {
        Map<String, String> detalhes = Collections.singletonMap("campo", "erro");
        GlobalExceptionHandler.ErrorResponse errorResponse = 
            new GlobalExceptionHandler.ErrorResponse(ErrorEnum.DADOS_INVALIDOS, "Mensagem", LocalDateTime.now(), detalhes);
        
        assertEquals(ErrorEnum.DADOS_INVALIDOS, errorResponse.codigo());
        assertEquals("Mensagem", errorResponse.mensagem());
        assertEquals(detalhes, errorResponse.detalhes());
    }

    @Test
    @DisplayName("Deve criar ErrorResponse sem detalhes")
    void testErrorResponseWithoutDetails() {
        GlobalExceptionHandler.ErrorResponse errorResponse = 
            new GlobalExceptionHandler.ErrorResponse(ErrorEnum.ERRO_INTERNO, "Mensagem", LocalDateTime.now());
        
        assertEquals(ErrorEnum.ERRO_INTERNO, errorResponse.codigo());
        assertEquals("Mensagem", errorResponse.mensagem());
        assertNull(errorResponse.detalhes());
    }
}
