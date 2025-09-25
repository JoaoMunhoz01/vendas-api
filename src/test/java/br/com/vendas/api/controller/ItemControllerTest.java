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

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.controller.impl.ItemController;
import br.com.vendas.api.handler.ItemHandler;
import java.math.BigDecimal;
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
class ItemControllerTest {

  @Mock
  private ItemHandler itemHandler;

  @InjectMocks
  private ItemController itemController;

  private ItemRequestDTO itemRequestDTO;
  private ItemResponseDTO itemResponseDTO;

  @BeforeEach
  void setUp() {
    itemRequestDTO = ItemRequestDTO.builder()
        .descricao("Notebook Dell i7 16GB")
        .precoUnitario(BigDecimal.valueOf(2500.00))
        .build();

    itemResponseDTO = ItemResponseDTO.builder()
        .id(1L)
        .descricao("Notebook Dell i7 16GB")
        .precoUnitario(BigDecimal.valueOf(2500.00))
        .ativo(true)
        .build();
  }

  @Test
  void criar_DeveRetornarItemCriadoComStatus201() {
    when(itemHandler.criar(any(ItemRequestDTO.class))).thenReturn(itemResponseDTO);

    ResponseEntity<ItemResponseDTO> response = itemController.criar(itemRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(itemResponseDTO, response.getBody());
    verify(itemHandler, times(1)).criar(itemRequestDTO);
  }

  @Test
  void buscarAtivos_DeveRetornarListaDeItensAtivos() {
    List<ItemResponseDTO> itens = List.of(itemResponseDTO);
    when(itemHandler.listar()).thenReturn(itens);

    ResponseEntity<List<ItemResponseDTO>> response = itemController.buscarAtivos();

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    verify(itemHandler, times(1)).listar();
  }

  @Test
  void buscarPorId_DeveRetornarItemQuandoExistir() {
    when(itemHandler.buscarPorId(anyLong())).thenReturn(itemResponseDTO);

    ResponseEntity<ItemResponseDTO> response = itemController.buscarPorId(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(itemResponseDTO, response.getBody());
    verify(itemHandler, times(1)).buscarPorId(1L);
  }

  @Test
  void atualizar_DeveRetornarItemAtualizado() {
    when(itemHandler.atualizar(any(ItemRequestDTO.class), anyLong())).thenReturn(itemResponseDTO);

    ResponseEntity<ItemResponseDTO> response = itemController.atualizar(1L, itemRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(itemResponseDTO, response.getBody());
    verify(itemHandler, times(1)).atualizar(itemRequestDTO, 1L);
  }

  @Test
  void ativar_DeveRetornarNoContent() {
    doNothing().when(itemHandler).ativar(anyLong());

    ResponseEntity<Void> response = itemController.ativar(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
    verify(itemHandler, times(1)).ativar(1L);
  }

  @Test
  void desativar_DeveRetornarNoContent() {
    doNothing().when(itemHandler).desativar(anyLong());

    ResponseEntity<Void> response = itemController.desativar(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
    verify(itemHandler, times(1)).desativar(1L);
  }
}
