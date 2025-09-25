package br.com.vendas.api.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompraEfetuadaEventTest {

  private final Long vendaId = 1L;
  private final String numeroVenda = "VENDA-001";
  private final BigDecimal valorTotal = new BigDecimal("1500.99");
  private final Long clienteId = 100L;
  private CompraEfetuadaEvent event;

  @BeforeEach
  void setUp() {
    event = new CompraEfetuadaEvent(vendaId, numeroVenda, valorTotal, clienteId);
  }

  @Test
  @DisplayName("Deve criar CompraEfetuadaEvent com dados corretos")
  void deveCriarCompraEfetuadaEventComDadosCorretos() {
    // Assert
    assertEquals(vendaId, event.getVendaId());
    assertEquals(numeroVenda, event.getNumeroVenda());
    assertEquals(valorTotal, event.getValorTotal());
    assertEquals(clienteId, event.getClienteId());
    assertNotNull(event.getTimestamp());
  }

  @Test
  @DisplayName("Deve retornar event type correto")
  void deveRetornarEventTypeCorreto() {
    assertEquals("CompraEfetuada", event.getEventType());
  }

  @Test
  @DisplayName("Deve lidar com valores nulos corretamente")
  void deveLidarComValoresNulos() {
    // Arrange & Act
    CompraEfetuadaEvent eventComValorNulo = new CompraEfetuadaEvent(
        null, null, null, null
    );

    // Assert
    assertNull(eventComValorNulo.getVendaId());
    assertNull(eventComValorNulo.getNumeroVenda());
    assertNull(eventComValorNulo.getValorTotal());
    assertNull(eventComValorNulo.getClienteId());
  }

  @Test
  @DisplayName("Deve lidar com valor total zero")
  void deveLidarComValorTotalZero() {
    // Arrange & Act
    BigDecimal zero = BigDecimal.ZERO;
    CompraEfetuadaEvent eventComValorZero = new CompraEfetuadaEvent(
        vendaId, numeroVenda, zero, clienteId
    );

    // Assert
    assertEquals(zero, eventComValorZero.getValorTotal());
  }
}
