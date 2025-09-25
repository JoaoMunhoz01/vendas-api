// ClienteHandlerTest.java
package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteHandlerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteHandler clienteHandler;

    @Test
    void criar_DeveRetornarClienteResponseDTO_QuandoRequestValido() {
        // Arrange
        ClienteRequestDTO requestDTO = ClienteRequestDTO.builder()
                .nome("João Silva")
                .build();

        Cliente clienteSalvo = Cliente.builder()
                .id(1L)
                .nome("João Silva")
                .ativo(true)
                .dataCadastro(LocalDateTime.now())
                .build();

        when(clienteService.criar(any(Cliente.class))).thenReturn(clienteSalvo);

        // Act
        ClienteResponseDTO response = clienteHandler.criar(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(clienteSalvo.getId(), response.id());
        assertEquals(clienteSalvo.getNome(), response.nome());
        verify(clienteService, times(1)).criar(any(Cliente.class));
    }

    @Test
    void buscarAtivos_DeveRetornarListaClientesAtivos() {
        // Arrange
        Cliente cliente1 = Cliente.builder().id(1L).nome("Cliente 1").ativo(true).build();
        Cliente cliente2 = Cliente.builder().id(2L).nome("Cliente 2").ativo(true).build();
        List<Cliente> clientes = List.of(cliente1, cliente2);

        when(clienteService.buscarAtivos()).thenReturn(clientes);

        // Act
        List<ClienteResponseDTO> response = clienteHandler.buscarAtivos();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(clienteService, times(1)).buscarAtivos();
    }

    @Test
    void buscarPorId_DeveRetornarCliente_QuandoIdExistente() {
        // Arrange
        Long id = 1L;
        Cliente cliente = Cliente.builder()
                .id(id)
                .nome("Cliente Teste")
                .ativo(true)
                .build();

        when(clienteService.buscarPorId(id)).thenReturn(cliente);

        // Act
        ClienteResponseDTO response = clienteHandler.buscarPorId(id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(cliente.getNome(), response.nome());
        verify(clienteService, times(1)).buscarPorId(id);
    }

    @Test
    void atualizar_DeveRetornarClienteAtualizado_QuandoDadosValidos() {
        // Arrange
        Long id = 1L;
        ClienteRequestDTO requestDTO = ClienteRequestDTO.builder()
                .nome("Novo Nome")
                .build();

        Cliente clienteAtualizado = Cliente.builder()
                .id(id)
                .nome("Novo Nome")
                .ativo(true)
                .build();

        when(clienteService.atualizar(eq(id), any(Cliente.class))).thenReturn(clienteAtualizado);

        // Act
        ClienteResponseDTO response = clienteHandler.atualizar(requestDTO, id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(requestDTO.nome(), response.nome());
        verify(clienteService, times(1)).atualizar(eq(id), any(Cliente.class));
    }

    @Test
    void ativar_DeveChamarServico_QuandoIdValido() {
        // Arrange
        Long id = 1L;
        doNothing().when(clienteService).ativar(id);

        // Act
        clienteHandler.ativar(id);

        // Assert
        verify(clienteService, times(1)).ativar(id);
    }

    @Test
    void desativar_DeveChamarServico_QuandoIdValido() {
        // Arrange
        Long id = 1L;
        doNothing().when(clienteService).desativar(id);

        // Act
        clienteHandler.desativar(id);

        // Assert
        verify(clienteService, times(1)).desativar(id);
    }
}
