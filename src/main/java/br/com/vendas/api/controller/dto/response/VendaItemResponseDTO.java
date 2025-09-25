package br.com.vendas.api.controller.dto.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder(toBuilder = true)
public record VendaItemResponseDTO(
    Long id,
    Long itemId,
    String itemNome,
    Integer quantidade,
    BigDecimal valorUnitario,
    BigDecimal desconto,
    BigDecimal valorTotalItem
) {

}

