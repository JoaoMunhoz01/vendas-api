// VendaHandlerTest.java
package br.com.vendas.api.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vendas.api.controller.dto.request.VendaItemRequestDTO;
import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.Venda;
import br.com.vendas.api.enums.StatusVenda;
import br.com.vendas.api.service.ClienteService;
import br.com.vendas.api.service.FilialService;
import br.com.vendas.api.service.ItemService;
import br.com.vendas.api.service.VendaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VendaHandlerTest {

  @Mock
  private VendaService vendaService;

  @Mock
  private ClienteService clienteService;

  @Mock
  private FilialService filialService;

  @Mock
  private ItemService itemService;

  @InjectMocks
  private VendaHandler vendaHandler;

  @Test
  void criar_DeveRetornarVendaResponseDTO_QuandoDadosValidos() {
    // Arrange
    Long clienteId = 1L;
    Long filialId = 1L;
    Long itemId = 1L;

    VendaItemRequestDTO itemDTO = VendaItemRequestDTO.builder()
        .itemId(itemId)
        .quantidade(2)
        .build();

    VendaRequestDTO requestDTO = VendaRequestDTO.builder()
        .numeroVenda("V001")
        .clienteId(clienteId)
        .filialId(filialId)
        .itens(List.of(itemDTO))
        .build();

    Cliente cliente = Cliente.builder().id(clienteId).nome("Cliente Teste").build();
    Filial filial = Filial.builder().id(filialId).nome("Filial Teste").build();
    Item item = Item.builder().id(itemId).descricao("Item Teste").precoUnitario(
        BigDecimal.valueOf(50.0)).build();
    Venda vendaSalva = Venda.builder()
        .id(1L)
        .numeroVenda("V001")
        .cliente(cliente)
        .filial(filial)
        .statusVenda(StatusVenda.NAO_CANCELADO)
        .build();

    when(clienteService.buscarPorId(clienteId)).thenReturn(cliente);
    when(filialService.buscarPorId(filialId)).thenReturn(filial);
    when(itemService.buscarPorId(itemId)).thenReturn(item);
    when(vendaService.criar(any(Venda.class))).thenReturn(vendaSalva);

    // Act
    VendaResponseDTO response = vendaHandler.criar(requestDTO);

    // Assert
    assertNotNull(response);
    assertEquals(vendaSalva.getId(), response.id());
    assertEquals(vendaSalva.getNumeroVenda(), response.numeroVenda());
    verify(clienteService, times(1)).buscarPorId(clienteId);
    verify(filialService, times(1)).buscarPorId(filialId);
    verify(itemService, times(1)).buscarPorId(itemId);
    verify(vendaService, times(1)).criar(any(Venda.class));
  }

  @Test
  void listar_DeveRetornarListaVendas() {
    // Arrange
    Venda venda1 = Venda.builder().id(1L).numeroVenda("V001").build();
    Venda venda2 = Venda.builder().id(2L).numeroVenda("V002").build();
    List<Venda> vendas = List.of(venda1, venda2);

    when(vendaService.listar()).thenReturn(vendas);

    // Act
    List<VendaResponseDTO> response = vendaHandler.listar();

    // Assert
    assertNotNull(response);
    assertEquals(2, response.size());
    verify(vendaService, times(1)).listar();
  }

  @Test
  void buscarPorId_DeveRetornarVenda_QuandoIdExistente() {
    // Arrange
    Long id = 1L;
    Venda venda = Venda.builder()
        .id(id)
        .numeroVenda("V001")
        .statusVenda(StatusVenda.NAO_CANCELADO)
        .build();

    when(vendaService.buscarPorId(id)).thenReturn(venda);

    // Act
    VendaResponseDTO response = vendaHandler.buscarPorId(id);

    // Assert
    assertNotNull(response);
    assertEquals(id, response.id());
    assertEquals(venda.getNumeroVenda(), response.numeroVenda());
    verify(vendaService, times(1)).buscarPorId(id);
  }

  @Test
  void atualizar_DeveRetornarVendaAtualizada_QuandoDadosValidos() {
    // Arrange
    Long id = 1L;
    Long clienteId = 1L;
    Long filialId = 1L;
    Long itemId = 1L;

    VendaItemRequestDTO itemDTO = VendaItemRequestDTO.builder()
        .itemId(itemId)
        .quantidade(3)
        .build();

    VendaRequestDTO requestDTO = VendaRequestDTO.builder()
        .numeroVenda("V001-ATUALIZADO")
        .clienteId(clienteId)
        .filialId(filialId)
        .itens(List.of(itemDTO))
        .build();

    // Criar uma lista vazia para os itens da venda existente
    Venda vendaExistente = Venda.builder()
        .id(id)
        .numeroVenda("V001")
        .cliente(Cliente.builder().id(clienteId).build())
        .filial(Filial.builder().id(filialId).build())
        .itens(new ArrayList<>()) // Inicializar a lista para evitar NullPointerException
        .build();

    Cliente cliente = Cliente.builder().id(clienteId).nome("Cliente Teste").build();
    Filial filial = Filial.builder().id(filialId).nome("Filial Teste").build();
    Item item = Item.builder().id(itemId).descricao("Item Teste")
        .precoUnitario(BigDecimal.valueOf(50.0)).build();
    Venda vendaAtualizada = Venda.builder()
        .id(id)
        .numeroVenda("V001-ATUALIZADO")
        .cliente(cliente)
        .filial(filial)
        .itens(new ArrayList<>()) // Inicializar a lista para evitar NullPointerException
        .build();

    when(vendaService.buscarPorId(id)).thenReturn(vendaExistente);
    when(clienteService.buscarPorId(clienteId)).thenReturn(cliente);
    when(filialService.buscarPorId(filialId)).thenReturn(filial);
    when(itemService.buscarPorId(itemId)).thenReturn(item);
    when(vendaService.atualizar(any(Venda.class))).thenReturn(vendaAtualizada);

    // Act
    VendaResponseDTO response = vendaHandler.atualizar(id, requestDTO);

    // Assert
    assertNotNull(response);
    assertEquals(id, response.id());
    assertEquals(requestDTO.numeroVenda(), response.numeroVenda());
    verify(vendaService, times(1)).buscarPorId(id);
    verify(vendaService, times(1)).atualizar(any(Venda.class));
  }

  @Test
  void deletar_DeveChamarServico_QuandoIdValido() {
    // Arrange
    Long id = 1L;
    doNothing().when(vendaService).deletar(id);

    // Act
    vendaHandler.deletar(id);

    // Assert
    verify(vendaService, times(1)).deletar(id);
  }

  @Test
  void cancelar_DeveRetornarVendaCancelada_QuandoIdValido() {
    // Arrange
    Long id = 1L;
    Venda vendaCancelada = Venda.builder()
        .id(id)
        .numeroVenda("V001")
        .statusVenda(StatusVenda.CANCELADO)
        .build();

    when(vendaService.cancelar(id)).thenReturn(vendaCancelada);

    // Act
    VendaResponseDTO response = vendaHandler.cancelar(id);

    // Assert
    assertNotNull(response);
    assertEquals(id, response.id());
    assertEquals(StatusVenda.CANCELADO, vendaCancelada.getStatusVenda());
    verify(vendaService, times(1)).cancelar(id);
  }
}
