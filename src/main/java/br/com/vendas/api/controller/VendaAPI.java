package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
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

@Tag(name = "Vendas", description = "API para gerenciamento de vendas")
public interface VendaAPI {

  @Operation(summary = "Criar nova venda", description = "Cria uma nova venda com os dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Venda criada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "409", description = "Número da venda já existe", content = @Content)
  })
  ResponseEntity<VendaResponseDTO> criar(
      @Parameter(description = "Dados da venda a ser criada", required = true)
      @Valid @RequestBody VendaRequestDTO vendaRequestDTO);

  @Operation(summary = "Listar todas as vendas", description = "Retorna uma lista com todas as vendas cadastradas")
  @ApiResponse(responseCode = "200", description = "Lista de vendas retornada com sucesso",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDTO.class)))
  ResponseEntity<List<VendaResponseDTO>> listar();

  @Operation(summary = "Buscar venda por ID", description = "Retorna uma venda específica pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda encontrada",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
  })
  ResponseEntity<VendaResponseDTO> buscarPorId(
      @Parameter(description = "ID da venda", required = true)
      @PathVariable Long id);

  @Operation(summary = "Atualizar venda", description = "Atualiza uma venda existente com os novos dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
  })
  ResponseEntity<VendaResponseDTO> atualizar(
      @Parameter(description = "ID da venda", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados da venda", required = true)
      @Valid @RequestBody VendaRequestDTO vendaRequestDTO);

  @Operation(summary = "Deletar venda", description = "Remove uma venda do sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Venda deletada com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
  })
  ResponseEntity<Void> deletar(
      @Parameter(description = "ID da venda", required = true)
      @PathVariable Long id);

  @Operation(summary = "Cancelar venda", description = "Cancela uma venda existente, alterando seu status para CANCELADO")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda cancelada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
  })
  ResponseEntity<VendaResponseDTO> cancelar(
      @Parameter(description = "ID da venda", required = true)
      @PathVariable Long id);
}


