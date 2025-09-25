package br.com.vendas.api.service;

import br.com.vendas.api.event.CompraAlteradaEvent;
import br.com.vendas.api.event.CompraCanceladaEvent;
import br.com.vendas.api.event.CompraEfetuadaEvent;
import br.com.vendas.api.event.ItemCanceladoEvent;
import br.com.vendas.api.service.impl.EventListenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class EventListenerServiceTest {

    @InjectMocks
    private EventListenerService eventListenerService;

    @Test
    void handleCompraEfetuada_DeveProcessarEventoSemErros() {
        CompraEfetuadaEvent event = new CompraEfetuadaEvent(
            1L, "VENDA001", new BigDecimal("1000.00"), 1L
        );

        assertDoesNotThrow(() -> eventListenerService.handleCompraEfetuada(event));
    }

    @Test
    void handleCompraAlterada_DeveProcessarEventoSemErros() {
        CompraAlteradaEvent event = new CompraAlteradaEvent(
            1L, "VENDA001", new BigDecimal("900.00"), new BigDecimal("1000.00")
        );

        assertDoesNotThrow(() -> eventListenerService.handleCompraAlterada(event));
    }

    @Test
    void handleCompraCancelada_DeveProcessarEventoSemErros() {
        CompraCanceladaEvent event = new CompraCanceladaEvent(
            1L, "VENDA001", new BigDecimal("1000.00"), "Cancelamento teste"
        );

        assertDoesNotThrow(() -> eventListenerService.handleCompraCancelada(event));
    }

    @Test
    void handleItemCancelado_DeveProcessarEventoSemErros() {
        ItemCanceladoEvent event = new ItemCanceladoEvent(
            1L, "VENDA001", 1L, 1L, 2, new BigDecimal("500.00")
        );

        assertDoesNotThrow(() -> eventListenerService.handleItemCancelado(event));
    }
}
