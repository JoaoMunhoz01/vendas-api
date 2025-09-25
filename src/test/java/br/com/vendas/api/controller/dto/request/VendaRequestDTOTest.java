package br.com.vendas.api.controller.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendaRequestDTOTest {

    private final Validator validator;

    public VendaRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar VendaRequestDTO com dados válidos")
    void deveCriarVendaRequestDTOComDadosValidos() {
        VendaItemRequestDTO item = new VendaItemRequestDTO(1L, 2);
        List<VendaItemRequestDTO> itens = Arrays.asList(item);
        
        VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, 1L, itens);
        
        assertEquals("V001", dto.numeroVenda());
        assertEquals(1L, dto.clienteId());
        assertEquals(1L, dto.filialId());
        assertEquals(1, dto.itens().size());
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Deve falhar quando número da venda for nulo")
    void deveFalharQuandoNumeroVendaForNulo() {
        VendaItemRequestDTO item = new VendaItemRequestDTO(1L, 2);
        List<VendaItemRequestDTO> itens = Arrays.asList(item);
        
        VendaRequestDTO dto = new VendaRequestDTO(null, 1L, 1L, itens);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Número da venda é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando clienteId for nulo")
    void deveFalharQuandoClienteIdForNulo() {
        VendaItemRequestDTO item = new VendaItemRequestDTO(1L, 2);
        List<VendaItemRequestDTO> itens = Arrays.asList(item);
        
        VendaRequestDTO dto = new VendaRequestDTO("V001", null, 1L, itens);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("ID do cliente é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando filialId for nulo")
    void deveFalharQuandoFilialIdForNulo() {
        VendaItemRequestDTO item = new VendaItemRequestDTO(1L, 2);
        List<VendaItemRequestDTO> itens = Arrays.asList(item);
        
        VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, null, itens);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("ID da filial é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando lista de itens for vazia")
    void deveFalharQuandoListaDeItensForVazia() {
        VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, 1L, Collections.emptyList());
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Lista de itens não pode estar vazia", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando lista de itens for nula")
    void deveFalharQuandoListaDeItensForNula() {
        VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, 1L, null);
        
        var violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Lista de itens não pode estar vazia", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Deve falhar quando item na lista for inválido")
    void deveFalharQuandoItemNaListaForInvalido() {
        VendaItemRequestDTO itemInvalido = new VendaItemRequestDTO(null, -1);
        List<VendaItemRequestDTO> itens = Arrays.asList(itemInvalido);
        
        VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, 1L, itens);
        
        var violations = validator.validate(dto);
        assertEquals(2, violations.size()); // itemId nulo e quantidade negativa
    }
}