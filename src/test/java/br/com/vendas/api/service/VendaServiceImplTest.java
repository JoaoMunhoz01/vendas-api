package br.com.vendas.api.service;

import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.Venda;
import br.com.vendas.api.domain.repository.VendaRepository;
import br.com.vendas.api.event.CompraAlteradaEvent;
import br.com.vendas.api.event.CompraCanceladaEvent;
import br.com.vendas.api.event.CompraEfetuadaEvent;
import br.com.vendas.api.exception.VendaDuplicadaException;
import br.com.vendas.api.exception.VendaNotFoundException;
import br.com.vendas.api.service.impl.VendaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceImplTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private VendaServiceImpl vendaService;

    private Venda venda;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        venda = new Venda();
        venda.setId(1L);
        venda.setNumeroVenda("VENDA001");
        venda.setValorTotalVenda(new BigDecimal("1000.00"));
        venda.setCliente(cliente);
    }

    @Test
    void criar_DeveSalvarVenda_QuandoNumeroVendaUnico() {
        when(vendaRepository.existsByNumeroVenda("VENDA001")).thenReturn(false);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda resultado = vendaService.criar(venda);

        assertNotNull(resultado);
        assertEquals(venda.getId(), resultado.getId());

        verify(vendaRepository, times(1)).existsByNumeroVenda("VENDA001");
        verify(vendaRepository, times(1)).save(venda);
        verify(eventPublisher, times(1)).publishEvent(any(CompraEfetuadaEvent.class));
    }

    @Test
    void criar_DeveLancarExcecao_QuandoNumeroVendaDuplicado() {
        when(vendaRepository.existsByNumeroVenda("VENDA001")).thenReturn(true);

        VendaDuplicadaException exception = assertThrows(VendaDuplicadaException.class,
            () -> vendaService.criar(venda));

        // Verifica apenas parte da mensagem para evitar problemas de formatação
        assertTrue(exception.getMessage().contains("VENDA001"));
        verify(vendaRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void listar_DeveRetornarTodasVendas() {
        List<Venda> vendas = Arrays.asList(venda);
        when(vendaRepository.findAll()).thenReturn(vendas);

        List<Venda> resultado = vendaService.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(vendaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_DeveRetornarVenda_QuandoExistir() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Venda resultado = vendaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(venda.getId(), resultado.getId());
        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoExistir() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        VendaNotFoundException exception = assertThrows(VendaNotFoundException.class,
            () -> vendaService.buscarPorId(1L));

        assertTrue(exception.getMessage().contains("1"));
        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    void atualizar_DeveAtualizarVenda_QuandoDadosValidos() {
        // Arrange
        Long idVenda = 1L;
        Venda vendaExistente = new Venda();
        vendaExistente.setId(idVenda);
        vendaExistente.setNumeroVenda("VENDA001");
        vendaExistente.setValorTotalVenda(new BigDecimal("1000.00"));
        vendaExistente.setCliente(cliente);

        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId(idVenda); // Usar o mesmo ID
        vendaAtualizada.setNumeroVenda("VENDA001-ATUALIZADO");
        vendaAtualizada.setValorTotalVenda(new BigDecimal("1200.00"));
        vendaAtualizada.setCliente(cliente);

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaAtualizada);

        // Act
        Venda resultado = vendaService.atualizar(vendaAtualizada);

        // Assert
        assertNotNull(resultado);
        assertEquals(idVenda, resultado.getId());
        assertEquals("VENDA001-ATUALIZADO", resultado.getNumeroVenda());
    }

    @Test
    void deletar_DeveDeletarVenda_QuandoExistir() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        doNothing().when(vendaRepository).delete(venda);

        vendaService.deletar(1L);

        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).delete(venda);
        verify(eventPublisher, times(1)).publishEvent(any(CompraCanceladaEvent.class));
    }

    @Test
    void cancelar_DeveCancelarVenda_QuandoExistir() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda resultado = vendaService.cancelar(1L);

        assertNotNull(resultado);
        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).save(venda);
        verify(eventPublisher, times(1)).publishEvent(any(CompraCanceladaEvent.class));
    }

    @Test
    void publicarEventoCompraEfetuada_DevePublicarEventoCorretamente() {
        ArgumentCaptor<CompraEfetuadaEvent> eventCaptor = ArgumentCaptor.forClass(CompraEfetuadaEvent.class);

        when(vendaRepository.existsByNumeroVenda("VENDA001")).thenReturn(false);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        vendaService.criar(venda);

        verify(eventPublisher).publishEvent(eventCaptor.capture());
        CompraEfetuadaEvent event = eventCaptor.getValue();

        assertEquals(venda.getId(), event.getVendaId());
        assertEquals(venda.getNumeroVenda(), event.getNumeroVenda());
        assertEquals(venda.getValorTotalVenda(), event.getValorTotal());
        assertEquals(venda.getCliente().getId(), event.getClienteId());
    }
}
