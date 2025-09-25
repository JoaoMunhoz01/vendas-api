package br.com.vendas.api.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.service.ItemService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemHandlerTest {

  @Mock
  private ItemService itemService;

  @InjectMocks
  private ItemHandler itemHandler;

  @Test
  void criar_DeveRetornarItemResponseDTO_QuandoRequestValido() {
    // Arrange
    ItemRequestDTO requestDTO = ItemRequestDTO.builder()
        .descricao("Descrição do produto")
        .precoUnitario(BigDecimal.valueOf(99.99))
        .build();

    Item itemSalvo = Item.builder()
        .id(1L)
        .descricao("Descrição do produto")
        .precoUnitario(BigDecimal.valueOf(99.99))
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .dataUltimaAtualizacao(LocalDateTime.now())
        .build();

    when(itemService.criar(any(Item.class))).thenReturn(itemSalvo);

    // Act
    ItemResponseDTO response = itemHandler.criar(requestDTO);

    // Assert
    assertNotNull(response);
    assertEquals(itemSalvo.getId(), response.id());
    assertEquals(itemSalvo.getDescricao(), response.descricao());
    assertEquals(itemSalvo.getPrecoUnitario(), response.precoUnitario());
    assertTrue(itemSalvo.getAtivo());
    assertNotNull(itemSalvo.getDataCadastro());
    assertNotNull(itemSalvo.getDataUltimaAtualizacao());
    verify(itemService, times(1)).criar(any(Item.class));
  }

  @Test
  void listar_DeveRetornarListaItensAtivos() {
    // Arrange
    Item item1 = Item.builder().id(1L).descricao("Item 1").ativo(true).build();
    Item item2 = Item.builder().id(2L).descricao("Item 2").ativo(true).build();
    List<Item> itens = List.of(item1, item2);

    when(itemService.buscarAtivos()).thenReturn(itens);

    // Act
    List<ItemResponseDTO> response = itemHandler.listar();

    // Assert
    assertNotNull(response);
    assertEquals(2, response.size());
    verify(itemService, times(1)).buscarAtivos();
  }

  @Test
  void buscarPorId_DeveRetornarItem_QuandoIdExistente() {
    // Arrange
    Long id = 1L;
    Item item = Item.builder()
        .id(id)
        .descricao("Item Teste")
        .precoUnitario(BigDecimal.valueOf(50.0))
        .ativo(true)
        .build();

    when(itemService.buscarPorId(id)).thenReturn(item);

    // Act
    ItemResponseDTO response = itemHandler.buscarPorId(id);

    // Assert
    assertNotNull(response);
    assertEquals(id, response.id());
    assertEquals(item.getDescricao(), response.descricao());
    verify(itemService, times(1)).buscarPorId(id);
  }

  @Test
  void atualizar_DeveRetornarItemAtualizado_QuandoDadosValidos() {
    // Arrange
    Long id = 1L;
    ItemRequestDTO requestDTO = ItemRequestDTO.builder()
        .descricao("Nova descrição")
        .precoUnitario(BigDecimal.valueOf(150.0))
        .build();

    Item itemAtualizado = Item.builder()
        .id(id)
        .descricao("Nova descrição")
        .precoUnitario(BigDecimal.valueOf(150.0))
        .ativo(true)
        .build();

    when(itemService.atualizar(eq(id), any(Item.class))).thenReturn(itemAtualizado);

    // Act
    ItemResponseDTO response = itemHandler.atualizar(requestDTO, id);

    // Assert
    assertNotNull(response);
    assertEquals(id, response.id());
    assertEquals(requestDTO.descricao(), response.descricao());
    assertEquals(requestDTO.precoUnitario(), response.precoUnitario());
    verify(itemService, times(1)).atualizar(eq(id), any(Item.class));
  }

  @Test
  void ativar_DeveChamarServico_QuandoIdValido() {
    // Arrange
    Long id = 1L;
    doNothing().when(itemService).ativar(id);

    // Act
    itemHandler.ativar(id);

    // Assert
    verify(itemService, times(1)).ativar(id);
  }

  @Test
  void desativar_DeveChamarServico_QuandoIdValido() {
    // Arrange
    Long id = 1L;
    doNothing().when(itemService).desativar(id);

    // Act
    itemHandler.desativar(id);

    // Assert
    verify(itemService, times(1)).desativar(id);
  }
}
