package br.com.vendas.api.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemCanceladoEventTest {

  private final Long vendaId = 1L;
  private final String numeroVenda = "VENDA-001";
  private final Long itemId = 10L;
  private final Long produtoId = 20L;
  private final Integer quantidade = 5;
  private final BigDecimal valorItem = new BigDecimal("99.99");
  private ItemCanceladoEvent event;

  @BeforeEach
  void setUp() {
    event = new ItemCanceladoEvent(vendaId, numeroVenda, itemId, produtoId, quantidade, valorItem);
  }

  @Test
  @DisplayName("Deve criar ItemCanceladoEvent com dados corretos")
  void deveCriarItemCanceladoEventComDadosCorretos() {
    // Assert
    assertEquals(vendaId, event.getVendaId());
    assertEquals(numeroVenda, event.getNumeroVenda());
    assertEquals(itemId, event.getItemId());
    assertEquals(produtoId, event.getProdutoId());
    assertEquals(quantidade, event.getQuantidade());
    assertEquals(valorItem, event.getValorItem());
    assertNotNull(event.getTimestamp());
  }

  @Test
  @DisplayName("Deve retornar event type correto")
  void deveRetornarEventTypeCorreto() {
    assertEquals("ItemCancelado", event.getEventType());
  }

  @Test
  @DisplayName("Deve lidar com valores nulos")
  void deveLidarComValoresNulos() {
    // Arrange & Act
    ItemCanceladoEvent eventComValoresNulos = new ItemCanceladoEvent(
        null, null, null, null, null, null
    );

    // Assert
    assertNull(eventComValoresNulos.getVendaId());
    assertNull(eventComValoresNulos.getNumeroVenda());
    assertNull(eventComValoresNulos.getItemId());
    assertNull(eventComValoresNulos.getProdutoId());
    assertNull(eventComValoresNulos.getQuantidade());
    assertNull(eventComValoresNulos.getValorItem());
  }

  @Test
  @DisplayName("Deve lidar com quantidade zero")
  void deveLidarComQuantidadeZero() {
    // Arrange & Act
    ItemCanceladoEvent eventComQuantidadeZero = new ItemCanceladoEvent(
        vendaId, numeroVenda, itemId, produtoId, 0, valorItem
    );

    // Assert
    assertEquals(0, eventComQuantidadeZero.getQuantidade());
  }

  @Test
  @DisplayName("Deve lidar com quantidade negativa")
  void deveLidarComQuantidadeNegativa() {
    // Arrange & Act
    ItemCanceladoEvent eventComQuantidadeNegativa = new ItemCanceladoEvent(
        vendaId, numeroVenda, itemId, produtoId, -5, valorItem
    );

    // Assert
    assertEquals(-5, eventComQuantidadeNegativa.getQuantidade());
  }

  @Test
  @DisplayName("Deve lidar com valores decimais precisos")
  void deveLidarComValoresDecimaisPrecisos() {
    // Arrange
    BigDecimal valorPreciso = new BigDecimal("123.4567");

    // Act
    ItemCanceladoEvent eventComValorPreciso = new ItemCanceladoEvent(
        vendaId, numeroVenda, itemId, produtoId, quantidade, valorPreciso
    );

    // Assert
    assertEquals(valorPreciso, eventComValorPreciso.getValorItem());
  }
}
