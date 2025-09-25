package br.com.vendas.api.exception;

import br.com.vendas.api.enums.ErrorEnum;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(VendaNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleVendaNotFound(VendaNotFoundException ex) {
    logger.warn("Venda não encontrada: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.VENDA_NAO_ENCONTRADA,
        ErrorEnum.VENDA_NAO_ENCONTRADA.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(VendaDuplicadaException.class)
  public ResponseEntity<ErrorResponse> handleVendaDuplicada(VendaDuplicadaException ex) {
    logger.warn("Tentativa de criar venda duplicada: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.VENDA_DUPLICADA,
        ErrorEnum.VENDA_DUPLICADA.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ClienteNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleClienteNotFound(ClienteNotFoundException ex) {
    logger.warn("Cliente não encontrado: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.CLIENTE_NAO_ENCONTRADO,
        ErrorEnum.CLIENTE_NAO_ENCONTRADO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(FilialNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleFilialNotFound(FilialNotFoundException ex) {
    logger.warn("Filial não encontrada: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.FILIAL_NAO_ENCONTRADA,
        ErrorEnum.FILIAL_NAO_ENCONTRADA.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(RegraDeNegocioException.class)
  public ResponseEntity<ErrorResponse> handleRegraDeNegocio(RegraDeNegocioException ex) {
    logger.warn("Regra de negócio violada: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.REGRA_DE_NEGOCIO_VIOLADA,
        ErrorEnum.REGRA_DE_NEGOCIO_VIOLADA.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    logger.warn("Erro de validação: {}", ex.getMessage());

    Map<String, String> errors = ex.getBindingResult()

        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            error -> error.getDefaultMessage() != null ? error.getDefaultMessage()
                : "Valor inválido",
            (existing, replacement) -> existing
        ));

    ErrorResponse error = new ErrorResponse(
        ErrorEnum.DADOS_INVALIDOS,
        ErrorEnum.DADOS_INVALIDOS.getMessage(),
        LocalDateTime.now(),
        errors
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    logger.warn("Erro ao ler mensagem HTTP: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.FORMATO_JSON_INVALIDO,
        ErrorEnum.FORMATO_JSON_INVALIDO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    logger.warn("Erro de tipo de argumento: {}", ex.getMessage());

    ErrorResponse error = new ErrorResponse(
        ErrorEnum.TIPO_PARAMETRO_INVALIDO,
        ErrorEnum.TIPO_PARAMETRO_INVALIDO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameter(
      MissingServletRequestParameterException ex) {
    logger.warn("Parâmetro obrigatório ausente: {}", ex.getMessage());

    ErrorResponse error = new ErrorResponse(
        ErrorEnum.PARAMETRO_OBRIGATORIO_AUSENTE,
        ErrorEnum.PARAMETRO_OBRIGATORIO_AUSENTE.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    logger.warn("Método HTTP não suportado: {}", ex.getMessage());

    ErrorResponse error = new ErrorResponse(
        ErrorEnum.METODO_NAO_SUPORTADO,
        ErrorEnum.METODO_NAO_SUPORTADO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
    logger.warn("Endpoint não encontrado: {}", ex.getMessage());

    ErrorResponse error = new ErrorResponse(
        ErrorEnum.ENDPOINT_NAO_ENCONTRADO,
        ErrorEnum.ENDPOINT_NAO_ENCONTRADO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
      DataIntegrityViolationException ex) {
    logger.error("Violação de integridade de dados: {}", ex.getMessage());

    ErrorEnum codigoEnum = ErrorEnum.VIOLACAO_INTEGRIDADE_DADOS;

    if (ex.getMessage() != null) {
      if (ex.getMessage().contains("unique") || ex.getMessage().contains("duplicate")) {
        codigoEnum = ErrorEnum.DADOS_DUPLICADOS;
      } else if (ex.getMessage().contains("foreign key") || ex.getMessage()
          .contains("constraint")) {
        codigoEnum = ErrorEnum.REFERENCIA_INVALIDA;
      }
    }

    ErrorResponse error = new ErrorResponse(
        codigoEnum,
        codigoEnum.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
    logger.warn("Argumento ilegal: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.ARGUMENTO_INVALIDO,
        ErrorEnum.ARGUMENTO_INVALIDO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    logger.error("Erro interno do servidor: {}", ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(
        ErrorEnum.ERRO_INTERNO,
        ErrorEnum.ERRO_INTERNO.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  public record ErrorResponse(
      ErrorEnum codigo,
      String mensagem,
      LocalDateTime timestamp,
      Map<String, String> detalhes
  ) {

    public ErrorResponse(ErrorEnum codigo, String mensagem, LocalDateTime timestamp) {
      this(codigo, mensagem, timestamp, null);
    }
  }
}

