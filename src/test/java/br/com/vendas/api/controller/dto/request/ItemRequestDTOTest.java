package br.com.vendas.api.controller.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestDTOTest {

    private final Validator validator;

    public ItemRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar ItemRequestDTO com dados válidos")
    void deveCriarItemRequestDTOComDadosValidos() {
        ItemRequestDTO dto = new ItemRequestDTO("Notebook Dell", BigDecimal.valueOf(2500.00));
        
        assertEquals("Notebook Dell", dto.descricao());
        assertEquals(BigDecimal.valueOf(2500.00), dto.precoUnitario());
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Deve falhar quando descrição for nula")
    void deveFalharQuandoDescricaoForNula() {
        ItemRequestDTO dto = new ItemRequestDTO(null, BigDecimal.valueOf(100.00));
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Descrição é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando preço unitário for nulo")
    void deveFalharQuandoPrecoUnitarioForNulo() {
        ItemRequestDTO dto = new ItemRequestDTO("Produto", null);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Preço unitário é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando preço unitário for negativo")
    void deveFalharQuandoPrecoUnitarioForNegativo() {
        ItemRequestDTO dto = new ItemRequestDTO("Produto", BigDecimal.valueOf(-10.00));
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Preço unitário deve ser maior que zero", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando preço unitário for zero")
    void deveFalharQuandoPrecoUnitarioForZero() {
        ItemRequestDTO dto = new ItemRequestDTO("Produto", BigDecimal.ZERO);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Preço unitário deve ser maior que zero", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando descrição exceder 200 caracteres")
    void deveFalharQuandoDescricaoExceder200Caracteres() {
        String descricaoLonga = "D".repeat(201);
        ItemRequestDTO dto = new ItemRequestDTO(descricaoLonga, BigDecimal.valueOf(100.00));
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Descrição deve ter no máximo 200 caracteres", violations.iterator().next().getMessage());
    }
}