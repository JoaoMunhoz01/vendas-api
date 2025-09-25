package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Filiais", description = "API para gerenciamento de filiais")
public interface FilialAPI {

  @Operation(summary = "Criar nova filial", description = "Cria uma nova filial com os dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Filial criada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilialResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "409", description = "Filial já existe", content = @Content)
  })
  ResponseEntity<FilialResponseDTO> criar(
      @Parameter(description = "Dados da filial a ser criada", required = true)
      @Valid @RequestBody FilialRequestDTO filialRequestDTO);

  @Operation(summary = "Listar filiais ativas", description = "Retorna uma lista com todas as filiais ativas")
  @ApiResponse(responseCode = "200", description = "Lista de filiais retornada com sucesso",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilialResponseDTO.class)))
  ResponseEntity<List<FilialResponseDTO>> buscarAtivas();

  @Operation(summary = "Buscar filial por ID", description = "Retorna uma filial específica pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Filial encontrada",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilialResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Filial não encontrada", content = @Content)
  })
  ResponseEntity<FilialResponseDTO> buscarPorId(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id);

  @Operation(summary = "Atualizar filial", description = "Atualiza uma filial existente com os novos dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Filial atualizada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilialResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "404", description = "Filial não encontrada", content = @Content)
  })
  ResponseEntity<FilialResponseDTO> atualizar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados da filial", required = true)
      @Valid @RequestBody FilialRequestDTO filialRequestDTO);

  @Operation(summary = "Ativar filial", description = "Ativa uma filial existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Filial ativada com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Filial não encontrada", content = @Content)
  })
  ResponseEntity<Void> ativar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id);

  @Operation(summary = "Desativar filial", description = "Desativa uma filial existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Filial desativada com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Filial não encontrada", content = @Content)
  })
  ResponseEntity<Void> desativar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id);
}


