package br.com.vendas.api.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;

@Builder(toBuilder = true)
public record ItemRequestDTO(
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    String descricao,

    @NotNull(message = "Preço unitário é obrigatório")
    @Positive(message = "Preço unitário deve ser maior que zero")
    BigDecimal precoUnitario
) {

}

