package br.com.vendas.api.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CompraCanceladaEventTest {

    private CompraCanceladaEvent event;
    private final Long vendaId = 1L;
    private final String numeroVenda = "VENDA-001";
    private final BigDecimal valorCancelado = new BigDecimal("500.50");
    private final String motivo = "Cliente desistiu da compra";

    @BeforeEach
    void setUp() {
        event = new CompraCanceladaEvent(vendaId, numeroVenda, valorCancelado, motivo);
    }

    @Test
    @DisplayName("Deve criar CompraCanceladaEvent com dados corretos")
    void deveCriarCompraCanceladaEventComDadosCorretos() {
        // Assert
        assertEquals(vendaId, event.getVendaId());
        assertEquals(numeroVenda, event.getNumeroVenda());
        assertEquals(valorCancelado, event.getValorCancelado());
        assertEquals(motivo, event.getMotivo());
        assertNotNull(event.getTimestamp());
    }

    @Test
    @DisplayName("Deve retornar event type correto")
    void deveRetornarEventTypeCorreto() {
        assertEquals("CompraCancelada", event.getEventType());
    }

    @Test
    @DisplayName("Deve lidar com motivo vazio")
    void deveLidarComMotivoVazio() {
        // Arrange & Act
        CompraCanceladaEvent eventComMotivoVazio = new CompraCanceladaEvent(
            vendaId, numeroVenda, valorCancelado, ""
        );
        
        // Assert
        assertEquals("", eventComMotivoVazio.getMotivo());
    }

    @Test
    @DisplayName("Deve lidar com motivo nulo")
    void deveLidarComMotivoNulo() {
        // Arrange & Act
        CompraCanceladaEvent eventComMotivoNulo = new CompraCanceladaEvent(
            vendaId, numeroVenda, valorCancelado, null
        );
        
        // Assert
        assertNull(eventComMotivoNulo.getMotivo());
    }

    @Test
    @DisplayName("Deve lidar com valor cancelado zero")
    void deveLidarComValorCanceladoZero() {
        // Arrange & Act
        BigDecimal zero = BigDecimal.ZERO;
        CompraCanceladaEvent eventComValorZero = new CompraCanceladaEvent(
            vendaId, numeroVenda, zero, motivo
        );
        
        // Assert
        assertEquals(zero, eventComValorZero.getValorCancelado());
    }
}