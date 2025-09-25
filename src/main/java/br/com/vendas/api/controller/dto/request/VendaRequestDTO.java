package br.com.vendas.api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Schema(description = "Dados para criação ou atualização de uma venda")
@Builder(toBuilder = true)
public record VendaRequestDTO(
    @Schema(description = "Número único da venda", example = "V001")
    @NotBlank(message = "Número da venda é obrigatório")
    String numeroVenda,

    @Schema(description = "ID do cliente", example = "1")
    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,

    @Schema(description = "ID da filial", example = "1")
    @NotNull(message = "ID da filial é obrigatório")
    Long filialId,
    @Schema(description = "Lista de itens da venda")
    @NotEmpty(message = "Lista de itens não pode estar vazia")
    @Valid
    List<VendaItemRequestDTO> itens
) {

}

