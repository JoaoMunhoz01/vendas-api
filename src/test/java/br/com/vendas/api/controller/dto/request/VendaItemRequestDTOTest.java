package br.com.vendas.api.controller.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class VendaItemRequestDTOTest {

    private final Validator validator;

    public VendaItemRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar VendaItemRequestDTO com dados válidos")
    void deveCriarVendaItemRequestDTOComDadosValidos() {
        VendaItemRequestDTO dto = new VendaItemRequestDTO(1L, 5);
        
        assertEquals(1L, dto.itemId());
        assertEquals(5, dto.quantidade());
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Deve falhar quando itemId for nulo")
    void deveFalharQuandoItemIdForNulo() {
        VendaItemRequestDTO dto = new VendaItemRequestDTO(null, 5);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("ID do Item é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando quantidade for nula")
    void deveFalharQuandoQuantidadeForNula() {
        VendaItemRequestDTO dto = new VendaItemRequestDTO(1L, null);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Quantidade é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando quantidade for zero")
    void deveFalharQuandoQuantidadeForZero() {
        VendaItemRequestDTO dto = new VendaItemRequestDTO(1L, 0);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Quantidade deve ser positiva", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando quantidade for negativa")
    void deveFalharQuandoQuantidadeForNegativa() {
        VendaItemRequestDTO dto = new VendaItemRequestDTO(1L, -5);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Quantidade deve ser positiva", violations.iterator().next().getMessage());
    }
}