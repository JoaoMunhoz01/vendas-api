package br.com.vendas.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.controller.impl.ClienteController;
import br.com.vendas.api.handler.ClienteHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

  @Mock
  private ClienteHandler clienteHandler;

  @InjectMocks
  private ClienteController clienteController;

  private ClienteRequestDTO clienteRequestDTO;
  private ClienteResponseDTO clienteResponseDTO;

  @BeforeEach
  void setUp() {
    clienteRequestDTO = ClienteRequestDTO.builder()
        .nome("João Silva")
        .build();

    clienteResponseDTO = ClienteResponseDTO.builder()
        .id(1L)
        .nome("João Silva")
        .ativo(true)
        .build();
  }

  @Test
  void criar_DeveRetornarClienteCriadoComStatus201() {
    when(clienteHandler.criar(any(ClienteRequestDTO.class))).thenReturn(clienteResponseDTO);

    ResponseEntity<ClienteResponseDTO> response = clienteController.criar(clienteRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(clienteResponseDTO, response.getBody());
    verify(clienteHandler, times(1)).criar(clienteRequestDTO);
  }

  @Test
  void buscarAtivos_DeveRetornarListaDeClientesAtivos() {
    List<ClienteResponseDTO> clientes = List.of(clienteResponseDTO);
    when(clienteHandler.buscarAtivos()).thenReturn(clientes);

    ResponseEntity<List<ClienteResponseDTO>> response = clienteController.buscarAtivos();

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    verify(clienteHandler, times(1)).buscarAtivos();
  }

  @Test
  void buscarPorId_DeveRetornarClienteQuandoExistir() {
    when(clienteHandler.buscarPorId(anyLong())).thenReturn(clienteResponseDTO);

    ResponseEntity<ClienteResponseDTO> response = clienteController.buscarPorId(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(clienteResponseDTO, response.getBody());
    verify(clienteHandler, times(1)).buscarPorId(1L);
  }

  @Test
  void atualizar_DeveRetornarClienteAtualizado() {
    when(clienteHandler.atualizar(any(ClienteRequestDTO.class), anyLong())).thenReturn(
        clienteResponseDTO);

    ResponseEntity<ClienteResponseDTO> response = clienteController.atualizar(1L,
        clienteRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(clienteResponseDTO, response.getBody());
    verify(clienteHandler, times(1)).atualizar(clienteRequestDTO, 1L);
  }

  @Test
  void ativar_DeveRetornarNoContent() {
    doNothing().when(clienteHandler).ativar(anyLong());

    ResponseEntity<Void> response = clienteController.ativar(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
    verify(clienteHandler, times(1)).ativar(1L);
  }

  @Test
  void desativar_DeveRetornarNoContent() {
    doNothing().when(clienteHandler).desativar(anyLong());

    ResponseEntity<Void> response = clienteController.desativar(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
    verify(clienteHandler, times(1)).desativar(1L);
  }
}
