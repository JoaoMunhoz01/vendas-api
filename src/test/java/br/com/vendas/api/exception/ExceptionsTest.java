package br.com.vendas.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionsTest {

  @Test
  @DisplayName("Deve criar ClienteNotFoundException com mensagem")
  void testClienteNotFoundExceptionWithMessage() {
    String message = "Cliente não encontrado";
    ClienteNotFoundException exception = new ClienteNotFoundException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar ClienteNotFoundException com ID")
  void testClienteNotFoundExceptionWithId() {
    Long id = 1L;
    ClienteNotFoundException exception = new ClienteNotFoundException(id);

    assertEquals("Cliente não encontrado com ID: " + id, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar FilialNotFoundException com mensagem")
  void testFilialNotFoundExceptionWithMessage() {
    String message = "Filial não encontrada";
    FilialNotFoundException exception = new FilialNotFoundException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar FilialNotFoundException com ID")
  void testFilialNotFoundExceptionWithId() {
    Long id = 1L;
    FilialNotFoundException exception = new FilialNotFoundException(id);

    assertEquals("Filial não encontrada com ID: " + id, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar RegraDeNegocioException")
  void testRegraDeNegocioException() {
    String message = "Regra de negócio violada";
    RegraDeNegocioException exception = new RegraDeNegocioException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar VendaDuplicadaException")
  void testVendaDuplicadaException() {
    String numeroVenda = "V001";
    VendaDuplicadaException exception = new VendaDuplicadaException(numeroVenda);

    assertEquals("Já existe uma venda com o número: " + numeroVenda, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar VendaNotFoundException com mensagem")
  void testVendaNotFoundExceptionWithMessage() {
    String message = "Venda não encontrada";
    VendaNotFoundException exception = new VendaNotFoundException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar VendaNotFoundException com mensagem e causa")
  void testVendaNotFoundExceptionWithMessageAndCause() {
    String message = "Venda não encontrada";
    Throwable cause = new RuntimeException("Causa original");
    VendaNotFoundException exception = new VendaNotFoundException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Deve criar VendaNotFoundException com ID")
  void testVendaNotFoundExceptionWithId() {
    Long id = 1L;
    VendaNotFoundException exception = new VendaNotFoundException(id);

    assertEquals("Venda não encontrada com ID: " + id, exception.getMessage());
  }

  @Test
  @DisplayName("Deve criar VendaNotFoundException com número")
  void testVendaNotFoundExceptionWithNumero() {
    String numeroVenda = "V001";
    VendaNotFoundException exception = new VendaNotFoundException(numeroVenda, true);

    assertEquals("Venda não encontrada com número: " + numeroVenda, exception.getMessage());
  }
}
