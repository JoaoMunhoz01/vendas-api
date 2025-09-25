package br.com.vendas.api.mapper;

import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.Venda;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VendaMapper {

  public static Venda toEntity(VendaRequestDTO dto, Cliente cliente, Filial filial) {
    if (dto == null || cliente == null || filial == null) {
      return null;
    }

    return Venda.builder()
        .numeroVenda(dto.numeroVenda())
        .cliente(cliente)
        .filial(filial)
        .build();

  }

  public static VendaResponseDTO toResponseDTO(Venda entity) {
    if (entity == null) {
      return null;
    }

    return VendaResponseDTO.builder()
        .id(entity.getId())
        .numeroVenda(entity.getNumeroVenda())
        .dataVenda(entity.getDataVenda())
        .cliente(ClienteMapper.toResponseDTO(entity.getCliente()))
        .valorTotalVenda(entity.getValorTotalVenda())
        .filial(FilialMapper.toResponseDTO(entity.getFilial()))
        .statusVenda(entity.getStatusVenda())
        .itens(VendaItemMapper.toResponseDTOList(entity.getItens()))
        .build();
  }


}

