package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.domain.Item;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

  public static Item toEntity(ItemRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    return Item.builder()
        .descricao(dto.descricao())
        .precoUnitario(dto.precoUnitario())
        .dataUltimaAtualizacao(LocalDateTime.now())
        .build();
  }

  public static ItemResponseDTO toResponseDTO(Item entity) {
    if (entity == null) {
      return null;
    }

    return ItemResponseDTO.builder()
        .id(entity.getId())
        .descricao(entity.getDescricao())
        .precoUnitario(entity.getPrecoUnitario())
        .ativo(entity.getAtivo())
        .dataCadastro(entity.getDataCadastro())
        .dataUltimaAtualizacao(entity.getDataUltimaAtualizacao())
        .build();
  }

  public static List<ItemResponseDTO> toResponseDTOList(List<Item> entities) {
    if (entities == null) {
      return null;
    }

    return entities.stream()
        .map(ItemMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  public static void updateEntityFromDTO(Item entity, ItemRequestDTO dto) {
    if (entity == null || dto == null) {
      return;
    }

    entity.setDescricao(dto.descricao());
    entity.setPrecoUnitario(dto.precoUnitario());
    entity.setDataUltimaAtualizacao(LocalDateTime.now());
  }
}

