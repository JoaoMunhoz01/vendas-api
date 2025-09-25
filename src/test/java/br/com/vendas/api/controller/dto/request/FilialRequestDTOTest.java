package br.com.vendas.api.controller.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class FilialRequestDTOTest {

    private final Validator validator;

    public FilialRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar FilialRequestDTO com dados válidos")
    void deveCriarFilialRequestDTOComDadosValidos() {
        FilialRequestDTO dto = new FilialRequestDTO("Filial Centro");
        
        assertEquals("Filial Centro", dto.nome());
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Deve falhar quando nome for nulo")
    void deveFalharQuandoNomeForNulo() {
        FilialRequestDTO dto = new FilialRequestDTO(null);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Nome é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando nome exceder 100 caracteres")
    void deveFalharQuandoNomeExceder100Caracteres() {
        String nomeLongo = "B".repeat(101);
        FilialRequestDTO dto = new FilialRequestDTO(nomeLongo);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Nome deve ter no máximo 100 caracteres", violations.iterator().next().getMessage());
    }
}