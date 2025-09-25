package br.com.vendas.api.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vendas.api.enums.StatusVenda;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VendaTest {

  private Venda venda;
  private Cliente cliente;
  private Filial filial;
  private Item item;

  @BeforeEach
  void setUp() {
    cliente = Cliente.builder()
        .id(1L)
        .nome("Cliente Teste")
        .ativo(true)
        .build();

    filial = Filial.builder()
        .id(1L)
        .nome("Filial Teste")
        .ativa(true)
        .build();

    item = Item.builder()
        .id(1L)
        .descricao("Produto Teste")
        .precoUnitario(new BigDecimal("100.00"))
        .ativo(true)
        .build();

    venda = Venda.builder()
        .id(1L)
        .numeroVenda("V001")
        .dataVenda(LocalDateTime.now())
        .valorTotalVenda(BigDecimal.ZERO)
        .statusVenda(StatusVenda.NAO_CANCELADO)
        .cliente(cliente)
        .filial(filial)
        .itens(new ArrayList<>())
        .build();
  }

  @Test
  @DisplayName("Deve criar venda com builder corretamente")
  void deveCriarVendaComBuilder() {
    assertAll("Venda criada com builder",
        () -> assertEquals(1L, venda.getId()),
        () -> assertEquals("V001", venda.getNumeroVenda()),
        () -> assertNotNull(venda.getDataVenda()),
        () -> assertEquals(BigDecimal.ZERO, venda.getValorTotalVenda()),
        () -> assertEquals(StatusVenda.NAO_CANCELADO, venda.getStatusVenda()),
        () -> assertEquals(cliente, venda.getCliente()),
        () -> assertEquals(filial, venda.getFilial()),
        () -> assertNotNull(venda.getItens()),
        () -> assertTrue(venda.getItens().isEmpty())
    );
  }

  @Test
  @DisplayName("Deve criar venda com construtor personalizado")
  void deveCriarVendaComConstrutorPersonalizado() {
    Venda vendaPersonalizada = new Venda("V002", cliente, filial);

    assertAll("Venda criada com construtor personalizado",
        () -> assertNull(vendaPersonalizada.getId()),
        () -> assertEquals("V002", vendaPersonalizada.getNumeroVenda()),
        () -> assertEquals(cliente, vendaPersonalizada.getCliente()),
        () -> assertEquals(filial, vendaPersonalizada.getFilial()),
        () -> assertNotNull(vendaPersonalizada.getDataVenda()),
        () -> assertEquals(StatusVenda.NAO_CANCELADO, vendaPersonalizada.getStatusVenda())
    );
  }

  @Test
  @DisplayName("Deve adicionar item à venda")
  void deveAdicionarItemAVenda() {
    VendaItem vendaItem = VendaItem.builder()
        .item(item)
        .quantidade(2)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("200.00"))
        .build();

    venda.adicionarItem(vendaItem);

    assertAll("Item adicionado à venda",
        () -> assertFalse(venda.getItens().isEmpty()),
        () -> assertEquals(1, venda.getItens().size()),
        () -> assertEquals(venda, vendaItem.getVenda()),
        () -> assertEquals(new BigDecimal("200.00"), venda.getValorTotalVenda())
    );
  }

  @Test
  @DisplayName("Deve remover item da venda")
  void deveRemoverItemDaVenda() {
    VendaItem vendaItem = VendaItem.builder()
        .item(item)
        .quantidade(2)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("200.00"))
        .build();

    venda.adicionarItem(vendaItem);
    venda.removerItem(vendaItem);

    assertAll("Item removido da venda",
        () -> assertTrue(venda.getItens().isEmpty()),
        () -> assertNull(vendaItem.getVenda()),
        () -> assertEquals(BigDecimal.ZERO, venda.getValorTotalVenda())
    );
  }

  @Test
  @DisplayName("Deve calcular valor total da venda")
  void deveCalcularValorTotalVenda() {
    VendaItem item1 = VendaItem.builder()
        .item(item)
        .quantidade(1)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("100.00"))
        .build();

    VendaItem item2 = VendaItem.builder()
        .item(item)
        .quantidade(2)
        .desconto(new BigDecimal("10.00"))
        .valorTotalItem(new BigDecimal("190.00"))
        .build();

    venda.adicionarItem(item1);
    venda.adicionarItem(item2);

    BigDecimal valorTotalEsperado = new BigDecimal("100.00").add(new BigDecimal("190.00"));
    assertEquals(valorTotalEsperado, venda.getValorTotalVenda());
  }

  @Test
  @DisplayName("Deve cancelar e reativar venda")
  void deveCancelarEReativarVenda() {
    venda.cancelar();
    assertEquals(StatusVenda.CANCELADO, venda.getStatusVenda());
    assertTrue(venda.isCancelada());

    venda.reativar();
    assertEquals(StatusVenda.NAO_CANCELADO, venda.getStatusVenda());
    assertFalse(venda.isCancelada());
  }

  @Test
  @DisplayName("Deve calcular valor total de desconto")
  void deveCalcularValorTotalDesconto() {
    VendaItem item1 = VendaItem.builder()
        .item(item)
        .quantidade(1)
        .desconto(new BigDecimal("5.00"))
        .valorTotalItem(new BigDecimal("95.00"))
        .build();

    VendaItem item2 = VendaItem.builder()
        .item(item)
        .quantidade(2)
        .desconto(new BigDecimal("10.00"))
        .valorTotalItem(new BigDecimal("190.00"))
        .build();

    venda.adicionarItem(item1);
    venda.adicionarItem(item2);

    BigDecimal descontoTotalEsperado = new BigDecimal("5.00").add(new BigDecimal("10.00"));
    assertEquals(descontoTotalEsperado, venda.getValorTotalDesconto());
  }

  @Test
  @DisplayName("Deve calcular quantidade total de itens")
  void deveCalcularQuantidadeTotalItens() {
    VendaItem item1 = VendaItem.builder()
        .item(item)
        .quantidade(3)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("300.00"))
        .build();

    VendaItem item2 = VendaItem.builder()
        .item(item)
        .quantidade(2)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("200.00"))
        .build();

    venda.adicionarItem(item1);
    venda.adicionarItem(item2);

    assertEquals(5, venda.getQuantidadeTotalItens());
  }

  @Test
  @DisplayName("Deve verificar se venda tem itens")
  void deveVerificarSeVendaTemItens() {
    assertFalse(venda.temItens());

    VendaItem vendaItem = VendaItem.builder()
        .item(item)
        .quantidade(1)
        .desconto(BigDecimal.ZERO)
        .valorTotalItem(new BigDecimal("100.00"))
        .build();

    venda.adicionarItem(vendaItem);
    assertTrue(venda.temItens());
  }
}
