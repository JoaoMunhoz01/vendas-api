package br.com.vendas.api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Schema(description = "Dados de um item da venda")
@Builder
public record VendaItemRequestDTO(
    @Schema(description = "ID do Item", example = "1")
    @NotNull(message = "ID do Item é obrigatório")
    Long itemId,

    @Schema(description = "Quantidade do produto (máximo 20 itens)", example = "5", minimum = "1", maximum = "20")
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    Integer quantidade
) {

}

