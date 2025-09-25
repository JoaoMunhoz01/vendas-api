package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.domain.Filial;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FilialMapper {

  public static Filial toEntity(FilialRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    return Filial.builder()
        .nome(dto.nome())
        .build();
  }

  public static FilialResponseDTO toResponseDTO(Filial entity) {
    if (entity == null) {
      return null;
    }

    return FilialResponseDTO.builder()
        .id(entity.getId())
        .nome(entity.getNome())
        .ativa(entity.getAtiva())
        .dataCadastro(entity.getDataCadastro())
        .build();
  }

  public static List<FilialResponseDTO> toResponseDTOList(List<Filial> entities) {
    if (entities == null) {
      return null;
    }

    return entities.stream()
        .map(FilialMapper::toResponseDTO)
        .toList();
  }

  public void updateEntityFromDTO(Filial entity, FilialRequestDTO dto) {
    if (entity == null || dto == null) {
      return;
    }

    entity.setNome(dto.nome());

  }
}

