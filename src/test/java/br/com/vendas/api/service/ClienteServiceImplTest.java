package br.com.vendas.api.service;

import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.repository.ClienteRepository;
import br.com.vendas.api.exception.ClienteNotFoundException;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private Cliente clienteAtualizado;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setAtivo(true);

        clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("João Silva Atualizado");
    }

    @Test
    void criar_DeveSalvarCliente_QuandoDadosValidos() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.criar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void criar_DeveLancarExcecao_QuandoNomeNulo() {
        cliente.setNome(null);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> clienteService.criar(cliente));

        assertEquals("Nome do cliente é obrigatório", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void criar_DeveLancarExcecao_QuandoNomeVazio() {
        cliente.setNome("");

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> clienteService.criar(cliente));

        assertEquals("Nome do cliente é obrigatório", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void buscarPorId_DeveRetornarCliente_QuandoExistir() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoExistir() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class,
            () -> clienteService.buscarPorId(1L));

        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void buscarAtivos_DeveRetornarListaClientesAtivos() {
        List<Cliente> clientesAtivos = Arrays.asList(cliente);
        when(clienteRepository.findByAtivoTrue()).thenReturn(clientesAtivos);

        List<Cliente> resultado = clienteService.buscarAtivos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByAtivoTrue();
    }

    @Test
    void atualizar_DeveAtualizarCliente_QuandoDadosValidos() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.atualizar(1L, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", cliente.getNome());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void atualizar_DeveLancarExcecao_QuandoClienteNaoExistir() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class,
            () -> clienteService.atualizar(1L, clienteAtualizado));

        verify(clienteRepository, never()).save(any());
    }

    @Test
    void ativar_DeveAtivarCliente() {
        cliente.setAtivo(false);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.ativar(1L);

        assertTrue(cliente.isAtivo());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void desativar_DeveDesativarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.desativar(1L);

        assertFalse(cliente.isAtivo());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }
}
