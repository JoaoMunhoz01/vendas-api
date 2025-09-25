package br.com.vendas.api.controller.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder(toBuilder = true)
public record ClienteResponseDTO(
    Long id,
    String nome,
    Boolean ativo,
    LocalDateTime dataCadastro
) {

}

