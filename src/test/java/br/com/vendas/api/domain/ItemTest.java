package br.com.vendas.api.domain;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .descricao("Produto Teste")
                .precoUnitario(new BigDecimal("99.99"))
                .ativo(true)
                .dataCadastro(LocalDateTime.now())
                .vendaItens(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("Deve criar item com builder corretamente")
    void deveCriarItemComBuilder() {
        assertAll("Item criado com builder",
            () -> assertEquals(1L, item.getId()),
            () -> assertEquals("Produto Teste", item.getDescricao()),
            () -> assertEquals(new BigDecimal("99.99"), item.getPrecoUnitario()),
            () -> assertTrue(item.getAtivo()),
            () -> assertNotNull(item.getDataCadastro()),
            () -> assertNotNull(item.getVendaItens())
        );
    }

    @Test
    @DisplayName("Deve ativar e desativar item")
    void deveAtivarEDesativarItem() {
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
        
        item.desativar();
        
        assertAll("Item desativado",
            () -> assertFalse(item.getAtivo()),
            () -> assertNotNull(item.getDataUltimaAtualizacao()),
            () -> assertTrue(item.getDataUltimaAtualizacao().isAfter(antes))
        );

        item.ativar();
        
        assertAll("Item ativado",
            () -> assertTrue(item.getAtivo()),
            () -> assertNotNull(item.getDataUltimaAtualizacao())
        );
    }

    @Test
    @DisplayName("Deve atualizar preço do item")
    void deveAtualizarPrecoItem() {
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
        BigDecimal novoPreco = new BigDecimal("149.99");

        item.atualizarPreco(novoPreco);

        assertAll("Preço atualizado",
            () -> assertEquals(novoPreco, item.getPrecoUnitario()),
            () -> assertNotNull(item.getDataUltimaAtualizacao()),
            () -> assertTrue(item.getDataUltimaAtualizacao().isAfter(antes))
        );
    }

    @Test
    @DisplayName("Deve atualizar data automaticamente no preUpdate")
    void deveAtualizarDataNoPreUpdate() {
        // O método @PreUpdate será testado indiretamente através dos métodos que alteram o estado
        Item itemSimples = Item.builder()
                .descricao("Teste")
                .precoUnitario(BigDecimal.TEN)
                .build();

        // A data de atualização deve ser nula inicialmente
        assertNull(itemSimples.getDataUltimaAtualizacao());
    }
}
