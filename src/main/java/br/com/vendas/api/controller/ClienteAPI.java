package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
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

@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public interface ClienteAPI {

  @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "409", description = "Cliente já existe", content = @Content)
  })
  ResponseEntity<ClienteResponseDTO> criar(
      @Parameter(description = "Dados do cliente a ser criado", required = true)
      @Valid @RequestBody ClienteRequestDTO clienteRequestDTO);

  @Operation(summary = "Listar clientes ativos", description = "Retorna uma lista com todos os clientes ativos")
  @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class)))
  ResponseEntity<List<ClienteResponseDTO>> buscarAtivos();

  @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
  })
  ResponseEntity<ClienteResponseDTO> buscarPorId(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id);

  @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente com os novos dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
  })
  ResponseEntity<ClienteResponseDTO> atualizar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados do cliente", required = true)
      @Valid @RequestBody ClienteRequestDTO clienteRequestDTO);

  @Operation(summary = "Ativar cliente", description = "Ativa um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Cliente ativado com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
  })
  ResponseEntity<Void> ativar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id);

  @Operation(summary = "Desativar cliente", description = "Desativa um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso", content = @Content),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
  })
  ResponseEntity<Void> desativar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id);
}


