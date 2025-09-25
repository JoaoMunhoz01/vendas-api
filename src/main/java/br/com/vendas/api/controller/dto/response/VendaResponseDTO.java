package br.com.vendas.api.controller.dto.response;

import br.com.vendas.api.enums.StatusVenda;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record VendaResponseDTO(
    Long id,
    String numeroVenda,
    LocalDateTime dataVenda,
    ClienteResponseDTO cliente,
    BigDecimal valorTotalVenda,
    FilialResponseDTO filial,
    StatusVenda statusVenda,
    List<VendaItemResponseDTO> itens
) {

}

