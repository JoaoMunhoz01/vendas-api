package br.com.vendas.api.controller.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteRequestDTOTest {

  private final Validator validator;

  public ClienteRequestDTOTest() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Deve criar ClienteRequestDTO com dados válidos")
  void deveCriarClienteRequestDTOComDadosValidos() {
    ClienteRequestDTO dto = new ClienteRequestDTO("João Silva");

    assertEquals("João Silva", dto.nome());
    assertTrue(validator.validate(dto).isEmpty());
  }

  @Test
  @DisplayName("Deve falhar quando nome for nulo")
  void deveFalharQuandoNomeForNulo() {
    ClienteRequestDTO dto = new ClienteRequestDTO(null);

    var violations = validator.validate(dto);
    assertEquals(1, violations.size());
    assertEquals("Nome é obrigatório", violations.iterator().next().getMessage());
  }

  @Test
  @DisplayName("Deve falhar quando nome for vazio")
  void deveFalharQuandoNomeForVazio() {
    ClienteRequestDTO dto = new ClienteRequestDTO("");

    var violations = validator.validate(dto);
    assertEquals(1, violations.size());
    assertEquals("Nome é obrigatório", violations.iterator().next().getMessage());
  }

  @Test
  @DisplayName("Deve falhar quando nome for em branco")
  void deveFalharQuandoNomeForEmBranco() {
    ClienteRequestDTO dto = new ClienteRequestDTO("   ");

    var violations = validator.validate(dto);
    assertEquals(1, violations.size());
    assertEquals("Nome é obrigatório", violations.iterator().next().getMessage());
  }

  @Test
  @DisplayName("Deve falhar quando nome exceder 100 caracteres")
  void deveFalharQuandoNomeExceder100Caracteres() {
    String nomeLongo = "A".repeat(101);
    ClienteRequestDTO dto = new ClienteRequestDTO(nomeLongo);

    var violations = validator.validate(dto);
    assertEquals(1, violations.size());
    assertEquals("Nome deve ter no máximo 100 caracteres",
        violations.iterator().next().getMessage());
  }

  @Test
  @DisplayName("Deve aceitar nome com exatamente 100 caracteres")
  void deveAceitarNomeComExatamente100Caracteres() {
    String nomeExato = "A".repeat(100);
    ClienteRequestDTO dto = new ClienteRequestDTO(nomeExato);

    assertTrue(validator.validate(dto).isEmpty());
  }
}
