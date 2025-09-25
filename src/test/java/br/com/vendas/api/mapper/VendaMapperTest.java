package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.Venda;
import br.com.vendas.api.domain.VendaItem;
import br.com.vendas.api.enums.StatusVenda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VendaMapperTest {

  @Test
  void toEntity_WhenAnyParameterIsNull_ShouldReturnNull() {
    // Arrange
    VendaRequestDTO dto = new VendaRequestDTO("V001", 1L, 1L, List.of());
    Cliente cliente = new Cliente();
    Filial filial = new Filial();

    // Act & Assert
    assertNull(VendaMapper.toEntity(null, cliente, filial));
    assertNull(VendaMapper.toEntity(dto, null, filial));
    assertNull(VendaMapper.toEntity(dto, cliente, null));
    assertNull(VendaMapper.toEntity(null, null, null));
  }

  @Test
  void toEntity_WhenValid_ShouldReturnVenda() {
    // Arrange
    VendaRequestDTO dto = new VendaRequestDTO("V002", 1L, 1L, List.of());
    Cliente cliente = Cliente.builder()
        .id(1L)
        .nome("Cliente Teste")
        .build();

    Filial filial = Filial.builder()
        .id(1L)
        .nome("Filial Teste")
        .build();

    // Act
    Venda result = VendaMapper.toEntity(dto, cliente, filial);

    // Assert
    assertNotNull(result);
    assertEquals("V002", result.getNumeroVenda());
    assertEquals(cliente, result.getCliente());
    assertEquals(filial, result.getFilial());
  }

  @Test
  void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
    // Act
    VendaResponseDTO result = VendaMapper.toResponseDTO(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTO_WhenEntityIsValid_ShouldReturnResponseDTO() {
    // Arrange
    Cliente cliente = Cliente.builder()
        .id(1L)
        .nome("Cliente")
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    Filial filial = Filial.builder()
        .id(1L)
        .nome("Filial")
        .ativa(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    Item item = Item.builder()
        .id(1L)
        .descricao("Item")
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    VendaItem vendaItemitem = VendaItem.builder()
        .id(1L)
        .quantidade(2)
        .item(item)
        .build();

    Venda entity = Venda.builder()
        .id(1L)
        .numeroVenda("V003")
        .dataVenda(LocalDateTime.now())
        .cliente(cliente)
        .filial(filial)
        .valorTotalVenda(new BigDecimal("200.00"))
        .statusVenda(StatusVenda.NAO_CANCELADO)
        .itens(List.of(vendaItemitem))
        .build();

    // Act
    VendaResponseDTO result = VendaMapper.toResponseDTO(entity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("V003", result.numeroVenda());
    assertNotNull(result.dataVenda());
    assertNotNull(result.cliente());
    assertNotNull(result.filial());
    assertEquals(new BigDecimal("200.00"), result.valorTotalVenda());
    assertEquals(StatusVenda.NAO_CANCELADO, result.statusVenda());
    assertNotNull(result.itens());
    assertEquals(1, result.itens().size());
  }

  @Test
  void toResponseDTO_WhenEntityHasNullFields_ShouldHandleGracefully() {
    // Arrange
    Venda entity = Venda.builder()
        .id(1L)
        .numeroVenda("V004")
        .dataVenda(LocalDateTime.now())
        .cliente(null)
        .filial(null)
        .valorTotalVenda(null)
        .statusVenda(null)
        .itens(null)
        .build();

    // Act
    VendaResponseDTO result = VendaMapper.toResponseDTO(entity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("V004", result.numeroVenda());
    assertNull(result.cliente());
    assertNull(result.filial());
    assertNull(result.valorTotalVenda());
    assertNull(result.statusVenda());
    assertNull(result.itens());
  }
}
