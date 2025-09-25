package br.com.vendas.api.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TodosEventosTest {

    @Test
    @DisplayName("Deve verificar hierarquia e polimorfismo dos eventos")
    void deveVerificarHierarquiaEPolimorfismo() {
        // Arrange
        VendaEvent[] eventos = {
            new CompraEfetuadaEvent(1L, "V001", new BigDecimal("100.00"), 1L),
            new CompraAlteradaEvent(2L, "V002", new BigDecimal("50.00"), new BigDecimal("75.00")),
            new CompraCanceladaEvent(3L, "V003", new BigDecimal("200.00"), "Teste"),
            new ItemCanceladoEvent(4L, "V004", 10L, 20L, 1, new BigDecimal("25.00"))
        };

        // Act & Assert
        for (VendaEvent evento : eventos) {
            assertNotNull(evento.getEventType());
            assertNotNull(evento.getTimestamp());
            assertTrue(evento.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
            
            // Verifica se cada evento tem seu tipo específico
            assertTrue(evento.getEventType().contains("Compra") || 
                      evento.getEventType().contains("Item"));
        }
    }
}