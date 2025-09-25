package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.domain.Cliente;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

  public static Cliente toEntity(ClienteRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    return Cliente.builder()
        .nome(dto.nome())
        .build();
  }

  public static ClienteResponseDTO toResponseDTO(Cliente entity) {
    if (entity == null) {
      return null;
    }

    return ClienteResponseDTO.builder()
        .id(entity.getId())
        .nome(entity.getNome())
        .ativo(entity.getAtivo())
        .dataCadastro(entity.getDataCadastro())
        .build();
  }

  public static List<ClienteResponseDTO> toResponseDTOList(List<Cliente> entities) {
    if (entities == null) {
      return null;
    }

    return entities.stream()
        .map(ClienteMapper::toResponseDTO)
        .toList();
  }

  public void updateEntityFromDTO(Cliente entity, ClienteRequestDTO dto) {
    if (entity == null || dto == null) {
      return;
    }

    entity.setNome(dto.nome());
  }
}

