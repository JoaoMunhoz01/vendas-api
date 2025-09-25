package br.com.vendas.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.domain.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteMapperTest {

  @Test
  void toEntity_WhenDtoIsNull_ShouldReturnNull() {
    Cliente result = ClienteMapper.toEntity(null);
    assertNull(result);
  }

  @Test
  void toEntity_WhenDtoIsValid_ShouldReturnEntity() {
    ClienteRequestDTO dto = new ClienteRequestDTO("João Silva");
    Cliente result = ClienteMapper.toEntity(dto);
    assertNotNull(result);
    assertEquals("João Silva", result.getNome());
  }

  @Test
  void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
    ClienteResponseDTO result = ClienteMapper.toResponseDTO(null);
    assertNull(result);
  }

  @Test
  void toResponseDTO_WhenEntityIsValid_ShouldReturnResponseDTO() {
    Cliente entity = Cliente.builder()
        .id(1L)
        .nome("Maria Santos")
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();
    ClienteResponseDTO result = ClienteMapper.toResponseDTO(entity);
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("Maria Santos", result.nome());
    assertTrue(result.ativo());
    assertNotNull(result.dataCadastro());
  }

  @Test
  void toResponseDTOList_WhenEntitiesIsNull_ShouldReturnNull() {
    List<ClienteResponseDTO> result = ClienteMapper.toResponseDTOList(null);
    assertNull(result);
  }

  @Test
  void toResponseDTOList_WhenEntitiesIsEmpty_ShouldReturnEmptyList() {
    List<Cliente> entities = List.of();
    List<ClienteResponseDTO> result = ClienteMapper.toResponseDTOList(entities);
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void toResponseDTOList_WhenEntitiesHasItems_ShouldReturnList() {
    Cliente cliente1 = Cliente.builder()
        .id(1L)
        .nome("Cliente 1")
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    Cliente cliente2 = Cliente.builder()
        .id(2L)
        .nome("Cliente 2")
        .ativo(false)
        .dataCadastro(LocalDateTime.now())
        .build();

    List<Cliente> entities = List.of(cliente1, cliente2);

    List<ClienteResponseDTO> result = ClienteMapper.toResponseDTOList(entities);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Cliente 1", result.get(0).nome());
    assertEquals("Cliente 2", result.get(1).nome());
  }

  @Test
  void updateEntityFromDTO_WhenEntityOrDtoIsNull_ShouldDoNothing() {

    Cliente entity = new Cliente();
    ClienteRequestDTO dto = new ClienteRequestDTO("Novo Nome");

    ClienteMapper mapper = new ClienteMapper();

    mapper.updateEntityFromDTO(null, dto);
    mapper.updateEntityFromDTO(entity, null);
    mapper.updateEntityFromDTO(null, null);

    assertNotEquals("Novo Nome", entity.getNome());
  }

  @Test
  void updateEntityFromDTO_WhenValid_ShouldUpdateEntity() {
    Cliente entity = Cliente.builder()
        .id(1L)
        .nome("Nome Antigo")
        .ativo(true)
        .dataCadastro(LocalDateTime.now())
        .build();

    ClienteRequestDTO dto = new ClienteRequestDTO("Nome Novo");
    ClienteMapper mapper = new ClienteMapper();

    mapper.updateEntityFromDTO(entity, dto);

    assertEquals("Nome Novo", entity.getNome());
    assertEquals(1L, entity.getId());
    assertTrue(entity.getAtivo());
    assertNotNull(entity.getDataCadastro());
  }
}
