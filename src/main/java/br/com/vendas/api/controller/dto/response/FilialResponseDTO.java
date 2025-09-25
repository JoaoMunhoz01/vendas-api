package br.com.vendas.api.controller.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder(toBuilder = true)
public record FilialResponseDTO(
    Long id,
    String nome,
    Boolean ativa,
    LocalDateTime dataCadastro
) {

}

