package br.com.vendas.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.domain.Filial;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilialMapperTest {

  @Test
  void toEntity_WhenDtoIsNull_ShouldReturnNull() {
    // Act
    Filial result = FilialMapper.toEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toEntity_WhenDtoIsValid_ShouldReturnEntity() {
    // Arrange
    FilialRequestDTO dto = new FilialRequestDTO("Filial Centro");

    // Act
    Filial result = FilialMapper.toEntity(dto);

    // Assert
    assertNotNull(result);
    assertEquals("Filial Centro", result.getNome());
  }

  @Test
  void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
    // Act
    FilialResponseDTO result = FilialMapper.toResponseDTO(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTO_WhenEntityIsValid_ShouldReturnResponseDTO() {
    // Arrange
    Filial entity = Filial.builder()
        .id(1L)
        .nome("Filial Norte")
        .ativa(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    // Act
    FilialResponseDTO result = FilialMapper.toResponseDTO(entity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("Filial Norte", result.nome());
    assertTrue(result.ativa());
    assertNotNull(result.dataCadastro());
  }

  @Test
  void toResponseDTOList_WhenEntitiesIsNull_ShouldReturnNull() {
    // Act
    List<FilialResponseDTO> result = FilialMapper.toResponseDTOList(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponseDTOList_WhenEntitiesHasItems_ShouldReturnList() {
    // Arrange
    Filial filial1 = Filial.builder()
        .id(1L)
        .nome("Filial 1")
        .ativa(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    Filial filial2 = Filial.builder()
        .id(2L)
        .nome("Filial 2")
        .ativa(false)
        .dataCadastro(LocalDateTime.now())
        .build();

    List<Filial> entities = List.of(filial1, filial2);

    // Act
    List<FilialResponseDTO> result = FilialMapper.toResponseDTOList(entities);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Filial 1", result.get(0).nome());
    assertEquals("Filial 2", result.get(1).nome());
  }

  @Test
  void updateEntityFromDTO_WhenValid_ShouldUpdateEntity() {
    // Arrange
    Filial entity = Filial.builder()
        .id(1L)
        .nome("Nome Antigo")
        .ativa(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    FilialRequestDTO dto = new FilialRequestDTO("Nome Novo");
    FilialMapper mapper = new FilialMapper();

    // Act
    mapper.updateEntityFromDTO(entity, dto);

    // Assert
    assertEquals("Nome Novo", entity.getNome());
    // Other fields should remain unchanged
    assertEquals(1L, entity.getId());
    assertTrue(entity.getAtiva());
    assertNotNull(entity.getDataCadastro());
  }
}
