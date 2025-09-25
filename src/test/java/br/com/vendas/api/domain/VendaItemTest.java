package br.com.vendas.api.domain;

import br.com.vendas.api.exception.RegraDeNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class VendaItemTest {

    private Item item;
    private VendaItem vendaItem;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .descricao("Produto Teste")
                .precoUnitario(new BigDecimal("50.00"))
                .ativo(true)
                .build();

        vendaItem = VendaItem.builder()
                .id(1L)
                .item(item)
                .quantidade(1)
                .desconto(BigDecimal.ZERO)
                .valorTotalItem(new BigDecimal("50.00"))
                .build();
    }

    @Test
    @DisplayName("Deve criar VendaItem com builder corretamente")
    void deveCriarVendaItemComBuilder() {
        assertAll("VendaItem criado com builder",
            () -> assertEquals(1L, vendaItem.getId()),
            () -> assertEquals(item, vendaItem.getItem()),
            () -> assertEquals(1, vendaItem.getQuantidade()),
            () -> assertEquals(BigDecimal.ZERO, vendaItem.getDesconto()),
            () -> assertEquals(new BigDecimal("50.00"), vendaItem.getValorTotalItem())
        );
    }

    @Test
    @DisplayName("Deve calcular desconto para quantidade menor que 4")
    void deveCalcularDescontoQuantidadeMenorQue4() {
        vendaItem.setQuantidade(3);
        
        assertEquals(BigDecimal.ZERO, vendaItem.getDesconto());
    }

    @Test
    @DisplayName("Deve calcular desconto para quantidade entre 4 e 9")
    void deveCalcularDescontoQuantidadeEntre4e9() {
        vendaItem.setQuantidade(5);
        BigDecimal descontoEsperado = new BigDecimal("50.00").multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.10));
        
        assertEquals(descontoEsperado, vendaItem.getDesconto());
    }

    @Test
    @DisplayName("Deve calcular desconto para quantidade entre 10 e 20")
    void deveCalcularDescontoQuantidadeEntre10e20() {
        vendaItem.setQuantidade(15);
        BigDecimal descontoEsperado = new BigDecimal("50.00").multiply(BigDecimal.valueOf(15))
                .multiply(BigDecimal.valueOf(0.20));
        
        assertEquals(descontoEsperado, vendaItem.getDesconto());
    }

    @Test
    @DisplayName("Deve lançar exceção para quantidade acima de 20")
    void deveLancarExcecaoQuantidadeAcima20() {
        assertThrows(RegraDeNegocioException.class, () -> {
            vendaItem.setQuantidade(21);
        });
    }

    @Test
    @DisplayName("Deve calcular valor total do item corretamente")
    void deveCalcularValorTotalItem() {
        vendaItem.setQuantidade(5);
        BigDecimal valorBruto = new BigDecimal("50.00").multiply(BigDecimal.valueOf(5));
        BigDecimal desconto = valorBruto.multiply(BigDecimal.valueOf(0.10));
        BigDecimal valorTotalEsperado = valorBruto.subtract(desconto);
        
        assertEquals(valorTotalEsperado, vendaItem.getValorTotalItem());
    }

    @Test
    @DisplayName("Deve retornar descrição do produto")
    void deveRetornarDescricaoProduto() {
        assertEquals("Produto Teste", vendaItem.getProdutoDescritivo());
        
        VendaItem vendaItemSemItem = VendaItem.builder().build();
        assertNull(vendaItemSemItem.getProdutoDescritivo());
    }
}