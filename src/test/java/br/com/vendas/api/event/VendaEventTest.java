package br.com.vendas.api.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VendaEventTest {

  @Test
  @DisplayName("Deve criar VendaEvent com dados corretos e timestamp atual")
  void deveCriarVendaEventComTimestampAtual() {
    // Arrange
    Long vendaId = 1L;
    String numeroVenda = "VENDA-001";

    // Act
    VendaEventMock event = new VendaEventMock(vendaId, numeroVenda);

    // Assert
    assertEquals(vendaId, event.getVendaId());
    assertEquals(numeroVenda, event.getNumeroVenda());
    assertNotNull(event.getTimestamp());
    assertTrue(event.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    assertTrue(event.getTimestamp().isAfter(LocalDateTime.now().minusSeconds(1)));
  }

  @Test
  @DisplayName("Deve retornar event type correto")
  void deveRetornarEventTypeCorreto() {
    // Arrange
    VendaEventMock event = new VendaEventMock(1L, "VENDA-001");

    // Act & Assert
    assertEquals("MockEvent", event.getEventType());
  }

  // Classe mock para testar a classe abstrata
  private static class VendaEventMock extends VendaEvent {

    public VendaEventMock(Long vendaId, String numeroVenda) {
      super(vendaId, numeroVenda);
    }

    @Override
    public String getEventType() {
      return "MockEvent";
    }
  }
}
