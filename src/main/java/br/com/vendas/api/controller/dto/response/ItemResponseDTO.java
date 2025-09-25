package br.com.vendas.api.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder(toBuilder = true)
public record ItemResponseDTO(
    Long id,
    String descricao,
    BigDecimal precoUnitario,
    Boolean ativo,
    LocalDateTime dataCadastro,
    LocalDateTime dataUltimaAtualizacao
) {

}

