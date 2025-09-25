package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.VendaItemRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaItemResponseDTO;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.VendaItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VendaItemMapperTest {

  @Test
  void toEntity_WhenDtoOrItemIsNull_ShouldReturnNull() {
    // Act & Assert
    assertNull(VendaItemMapper.toEntity(null, new Item()));
    assertNull(VendaItemMapper.toEntity(new VendaItemRequestDTO(2L, 5), null));
    assertNull(VendaItemMapper.toEntity(null, null));
  }

  @Test
  void toEntity_WhenValid_ShouldReturnVendaItem() {
    // Arrange
    VendaItemRequestDTO dto = new VendaItemRequestDTO(5L, 10);
    Item item = Item.builder()
        .id(5L)
        .descricao("Teclado")
        .precoUnitario(new BigDecimal("100.00"))
        .build();

    // Act
    VendaItem result = VendaItemMapper.toEntity(dto, item);

    // Assert
    assertNotNull(result);
    assertEquals(item, result.getItem());
    assertEquals(10, result.getQuantidade());
  }

  @Test
  void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
    // Act
    VendaItemResponseDTO result = VendaItemMapper.toResponseDTO(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTO_WhenEntityIsValid_ShouldReturnResponseDTO() {
    // Arrange
    Item item = Item.builder()
        .id(1L)
        .descricao("Monitor")
        .precoUnitario(new BigDecimal("500.00"))
        .build();

    VendaItem entity = VendaItem.builder()
        .id(10L)
        .item(item)
        .quantidade(2)
        .valorTotalItem(new BigDecimal("100.00"))
        .desconto(new BigDecimal("50.00"))
        .build();

    // Act
    VendaItemResponseDTO result = VendaItemMapper.toResponseDTO(entity);

    // Assert
    assertNotNull(result);
    assertEquals(10L, result.id());
    assertEquals(2, result.quantidade());
    assertEquals(new BigDecimal("500.00"), result.valorUnitario());
    assertEquals(new BigDecimal("50.00"), result.desconto());
    assertEquals("Monitor", result.itemNome());
    assertEquals(1L, result.itemId());
    assertNotNull(result.valorTotalItem());
  }

  @Test
  void toResponseDTOList_WhenEntitiesIsNull_ShouldReturnNull() {
    // Act
    List<VendaItemResponseDTO> result = VendaItemMapper.toResponseDTOList(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTOList_WhenEntitiesHasItems_ShouldReturnList() {
    // Arrange
    Item item1 = Item.builder()
        .id(1L)
        .descricao("Item 1")
        .precoUnitario(new BigDecimal("10.00"))
        .build();

    Item item2 = Item.builder()
        .id(2L)
        .descricao("Item 2")
        .precoUnitario(new BigDecimal("20.00"))
        .build();

    VendaItem vendaItem1 = VendaItem.builder()
        .id(1L)
        .item(item1)
        .quantidade(1)
        .build();

    VendaItem vendaItem2 = VendaItem.builder()
        .id(2L)
        .item(item2)
        .quantidade(2)
        .build();

    List<VendaItem> entities = List.of(vendaItem1, vendaItem2);

    // Act
    List<VendaItemResponseDTO> result = VendaItemMapper.toResponseDTOList(entities);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Item 1", result.get(0).itemNome());
    assertEquals("Item 2", result.get(1).itemNome());
  }
}
