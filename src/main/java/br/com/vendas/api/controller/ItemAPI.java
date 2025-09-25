package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
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

@Tag(name = "Itens", description = "API para gerenciamento de itens/produtos")
public interface ItemAPI {

  @Operation(summary = "Criar novo item", description = "Cria um novo item com os dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "409", description = "Item já existe", content = @Content)
  })
  ResponseEntity<ItemResponseDTO> criar(
      @Parameter(description = "Dados do item a ser criado", required = true)
      @Valid @RequestBody ItemRequestDTO itemRequestDTO);

  @Operation(summary = "Listar itens ativos", description = "Retorna uma lista com todos os itens ativos")
  @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class)))
  ResponseEntity<List<ItemResponseDTO>> buscarAtivos();

  @Operation(summary = "Buscar item por ID", description = "Retorna um item específico pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
  })
  ResponseEntity<ItemResponseDTO> buscarPorId(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id);

  @Operation(summary = "Atualizar item", description = "Atualiza um item existente com os novos dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
  })
  ResponseEntity<ItemResponseDTO> atualizar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados do item", required = true)
      @Valid @RequestBody ItemRequestDTO itemRequestDTO);

  @Operation(summary = "Ativar item", description = "Ativa um item existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item ativado com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
  })
  ResponseEntity<Void> ativar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id);

  @Operation(summary = "Desativar item", description = "Desativa um item existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item desativado com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
  })
  ResponseEntity<Void> desativar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id);
}


