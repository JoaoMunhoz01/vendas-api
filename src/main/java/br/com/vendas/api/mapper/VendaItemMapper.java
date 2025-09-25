package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.VendaItemRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaItemResponseDTO;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.VendaItem;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VendaItemMapper {

  public static VendaItem toEntity(VendaItemRequestDTO dto, Item item) {
    if (dto == null || item == null) {
      return null;
    }

    VendaItem vendaItem = VendaItem.builder()
        .item(item)
        .quantidade(dto.quantidade())
        .build();
    vendaItem.setQuantidade(dto.quantidade());

    return vendaItem;
  }

  public static VendaItemResponseDTO toResponseDTO(VendaItem entity) {
    if (entity == null) {
      return null;
    }

    return VendaItemResponseDTO.builder()
        .id(entity.getId())
        .quantidade(entity.getQuantidade())
        .valorUnitario(entity.getItem().getPrecoUnitario())
        .desconto(entity.getDesconto())
        .itemNome(entity.getItem().getDescricao())
        .itemId(entity.getItem().getId())
        .valorTotalItem(entity.getValorTotalItem())
        .build();
  }

  public static List<VendaItemResponseDTO> toResponseDTOList(List<VendaItem> entities) {
    if (entities == null) {
      return null;
    }

    return entities.stream()
        .map(VendaItemMapper::toResponseDTO)
        .toList();
  }
}

