package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.domain.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemMapperTest {

  @Test
  void toEntity_WhenDtoIsNull_ShouldReturnNull() {
    // Act
    Item result = ItemMapper.toEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toEntity_WhenDtoIsValid_ShouldReturnEntityWithCurrentTimestamp() {
    // Arrange
    LocalDateTime beforeTest = LocalDateTime.now().minusSeconds(1);
    ItemRequestDTO dto = new ItemRequestDTO("Notebook", new BigDecimal("2500.00"));

    // Act
    Item result = ItemMapper.toEntity(dto);

    // Assert
    assertNotNull(result);
    assertEquals("Notebook", result.getDescricao());
    assertEquals(new BigDecimal("2500.00"), result.getPrecoUnitario());
    assertNotNull(result.getDataUltimaAtualizacao());
    assertTrue(result.getDataUltimaAtualizacao().isAfter(beforeTest));
  }

  @Test
  void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
    // Act
    ItemResponseDTO result = ItemMapper.toResponseDTO(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTO_WhenEntityIsValid_ShouldReturnResponseDTO() {
    // Arrange
    LocalDateTime now = LocalDateTime.now();
    Item entity = Item.builder()
        .id(1L)
        .descricao("Mouse")
        .precoUnitario(new BigDecimal("50.00"))
        .ativo(true)
        .dataCadastro(now)
        .dataUltimaAtualizacao(now)
        .build();

    // Act
    ItemResponseDTO result = ItemMapper.toResponseDTO(entity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("Mouse", result.descricao());
    assertEquals(new BigDecimal("50.00"), result.precoUnitario());
    assertTrue(result.ativo());
    assertEquals(now, result.dataCadastro());
    assertEquals(now, result.dataUltimaAtualizacao());
  }

  @Test
  void toResponseDTOList_WhenEntitiesIsNull_ShouldReturnNull() {
    // Act
    List<ItemResponseDTO> result = ItemMapper.toResponseDTOList(null);

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
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    Item item2 = Item.builder()
        .id(2L)
        .descricao("Item 2")
        .precoUnitario(new BigDecimal("20.00"))
        .ativo(false)
        .dataCadastro(LocalDateTime.now())
        .build();

    List<Item> entities = List.of(item1, item2);

    // Act
    List<ItemResponseDTO> result = ItemMapper.toResponseDTOList(entities);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Item 1", result.get(0).descricao());
    assertEquals("Item 2", result.get(1).descricao());
  }

  @Test
  void updateEntityFromDTO_WhenValid_ShouldUpdateEntityWithNewTimestamp() {
    // Arrange
    LocalDateTime oldTimestamp = LocalDateTime.now().minusDays(1);
    Item entity = Item.builder()
        .id(1L)
        .descricao("Descrição Antiga")
        .precoUnitario(new BigDecimal("100.00"))
        .dataUltimaAtualizacao(oldTimestamp)
        .build();

    ItemRequestDTO dto = new ItemRequestDTO("Descrição Nova", new BigDecimal("150.00"));

    // Act
    ItemMapper.updateEntityFromDTO(entity, dto);

    // Assert
    assertEquals("Descrição Nova", entity.getDescricao());
    assertEquals(new BigDecimal("150.00"), entity.getPrecoUnitario());
    assertNotNull(entity.getDataUltimaAtualizacao());
    assertTrue(entity.getDataUltimaAtualizacao().isAfter(oldTimestamp));
    // ID should remain unchanged
    assertEquals(1L, entity.getId());
  }
}
