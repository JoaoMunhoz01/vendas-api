package br.com.vendas.api.controller.dto.response;

import br.com.vendas.api.enums.StatusVenda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseDTOsTest {

    @Test
    @DisplayName("Deve criar ClienteResponseDTO usando Builder")
    void deveCriarClienteResponseDTOComBuilder() {
        LocalDateTime dataCadastro = LocalDateTime.now();
        
        ClienteResponseDTO dto = ClienteResponseDTO.builder()
                .id(1L)
                .nome("João Silva")
                .ativo(true)
                .dataCadastro(dataCadastro)
                .build();
        
        assertEquals(1L, dto.id());
        assertEquals("João Silva", dto.nome());
        assertTrue(dto.ativo());
        assertEquals(dataCadastro, dto.dataCadastro());
    }

    @Test
    @DisplayName("Deve criar FilialResponseDTO usando Builder")
    void deveCriarFilialResponseDTOComBuilder() {
        LocalDateTime dataCadastro = LocalDateTime.now();
        
        FilialResponseDTO dto = FilialResponseDTO.builder()
                .id(1L)
                .nome("Filial Centro")
                .ativa(true)
                .dataCadastro(dataCadastro)
                .build();
        
        assertEquals(1L, dto.id());
        assertEquals("Filial Centro", dto.nome());
        assertTrue(dto.ativa());
        assertEquals(dataCadastro, dto.dataCadastro());
    }

    @Test
    @DisplayName("Deve criar ItemResponseDTO usando Builder")
    void deveCriarItemResponseDTOComBuilder() {
        LocalDateTime now = LocalDateTime.now();
        
        ItemResponseDTO dto = ItemResponseDTO.builder()
                .id(1L)
                .descricao("Notebook Dell")
                .precoUnitario(BigDecimal.valueOf(2500.00))
                .ativo(true)
                .dataCadastro(now)
                .dataUltimaAtualizacao(now)
                .build();
        
        assertEquals(1L, dto.id());
        assertEquals("Notebook Dell", dto.descricao());
        assertEquals(BigDecimal.valueOf(2500.00), dto.precoUnitario());
        assertTrue(dto.ativo());
        assertEquals(now, dto.dataCadastro());
        assertEquals(now, dto.dataUltimaAtualizacao());
    }

    @Test
    @DisplayName("Deve criar VendaItemResponseDTO usando Builder")
    void deveCriarVendaItemResponseDTOComBuilder() {
        VendaItemResponseDTO dto = VendaItemResponseDTO.builder()
                .id(1L)
                .itemId(10L)
                .itemNome("Notebook")
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(1500.00))
                .desconto(BigDecimal.valueOf(100.00))
                .valorTotalItem(BigDecimal.valueOf(2900.00))
                .build();
        
        assertEquals(1L, dto.id());
        assertEquals(10L, dto.itemId());
        assertEquals("Notebook", dto.itemNome());
        assertEquals(2, dto.quantidade());
        assertEquals(BigDecimal.valueOf(1500.00), dto.valorUnitario());
        assertEquals(BigDecimal.valueOf(100.00), dto.desconto());
        assertEquals(BigDecimal.valueOf(2900.00), dto.valorTotalItem());
    }

    @Test
    @DisplayName("Deve criar VendaResponseDTO usando Builder")
    void deveCriarVendaResponseDTOComBuilder() {
        LocalDateTime dataVenda = LocalDateTime.now();
        
        ClienteResponseDTO cliente = ClienteResponseDTO.builder()
                .id(1L)
                .nome("Cliente Teste")
                .ativo(true)
                .dataCadastro(dataVenda.minusDays(1))
                .build();
        
        FilialResponseDTO filial = FilialResponseDTO.builder()
                .id(1L)
                .nome("Filial Teste")
                .ativa(true)
                .dataCadastro(dataVenda.minusMonths(1))
                .build();
        
        VendaItemResponseDTO item = VendaItemResponseDTO.builder()
                .id(1L)
                .itemId(10L)
                .itemNome("Produto Teste")
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(100.00))
                .desconto(BigDecimal.ZERO)
                .valorTotalItem(BigDecimal.valueOf(200.00))
                .build();
        
        List<VendaItemResponseDTO> itens = Arrays.asList(item);
        
        VendaResponseDTO dto = VendaResponseDTO.builder()
                .id(1L)
                .numeroVenda("V001")
                .dataVenda(dataVenda)
                .cliente(cliente)
                .valorTotalVenda(BigDecimal.valueOf(200.00))
                .filial(filial)
                .statusVenda(StatusVenda.NAO_CANCELADO)
                .itens(itens)
                .build();
        
        assertEquals(1L, dto.id());
        assertEquals("V001", dto.numeroVenda());
        assertEquals(dataVenda, dto.dataVenda());
        assertEquals(cliente, dto.cliente());
        assertEquals(BigDecimal.valueOf(200.00), dto.valorTotalVenda());
        assertEquals(filial, dto.filial());
        assertEquals(StatusVenda.NAO_CANCELADO, dto.statusVenda());
        assertEquals(1, dto.itens().size());
        assertEquals(item, dto.itens().get(0));
    }

    @Test
    @DisplayName("Deve testar método toBuilder do ClienteResponseDTO")
    void deveTestarToBuilderClienteResponseDTO() {
        ClienteResponseDTO original = ClienteResponseDTO.builder()
                .id(1L)
                .nome("Original")
                .ativo(true)
                .dataCadastro(LocalDateTime.now())
                .build();
        
        ClienteResponseDTO modificado = original.toBuilder()
                .nome("Modificado")
                .ativo(false)
                .build();
        
        assertEquals(1L, modificado.id());
        assertEquals("Modificado", modificado.nome());
        assertFalse(modificado.ativo());
        assertEquals(original.dataCadastro(), modificado.dataCadastro());
    }
}
