package br.com.vendas.api.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompraAlteradaEventTest {

  private final Long vendaId = 1L;
  private final String numeroVenda = "VENDA-001";
  private final BigDecimal valorAnterior = new BigDecimal("1000.00");
  private final BigDecimal valorAtual = new BigDecimal("1200.00");
  private CompraAlteradaEvent event;

  @BeforeEach
  void setUp() {
    event = new CompraAlteradaEvent(vendaId, numeroVenda, valorAnterior, valorAtual);
  }

  @Test
  @DisplayName("Deve criar CompraAlteradaEvent com dados corretos")
  void deveCriarCompraAlteradaEventComDadosCorretos() {
    // Assert
    assertEquals(vendaId, event.getVendaId());
    assertEquals(numeroVenda, event.getNumeroVenda());
    assertEquals(valorAnterior, event.getValorAnterior());
    assertEquals(valorAtual, event.getValorAtual());
    assertNotNull(event.getTimestamp());
  }

  @Test
  @DisplayName("Deve retornar event type correto")
  void deveRetornarEventTypeCorreto() {
    assertEquals("CompraAlterada", event.getEventType());
  }

  @Test
  @DisplayName("Deve lidar com valores nulos")
  void deveLidarComValoresNulos() {
    // Arrange & Act
    CompraAlteradaEvent eventComValoresNulos = new CompraAlteradaEvent(
        null, null, null, null
    );

    // Assert
    assertNull(eventComValoresNulos.getVendaId());
    assertNull(eventComValoresNulos.getNumeroVenda());
    assertNull(eventComValoresNulos.getValorAnterior());
    assertNull(eventComValoresNulos.getValorAtual());
  }

  @Test
  @DisplayName("Deve lidar com valores decimais grandes")
  void deveLidarComValoresDecimaisGrandes() {
    // Arrange
    BigDecimal valorGrandeAnterior = new BigDecimal("999999.99");
    BigDecimal valorGrandeAtual = new BigDecimal("888888.88");

    // Act
    CompraAlteradaEvent eventComValoresGrandes = new CompraAlteradaEvent(
        vendaId, numeroVenda, valorGrandeAnterior, valorGrandeAtual
    );

    // Assert
    assertEquals(valorGrandeAnterior, eventComValoresGrandes.getValorAnterior());
    assertEquals(valorGrandeAtual, eventComValoresGrandes.getValorAtual());
  }
}
