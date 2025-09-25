package br.com.vendas.api.controller.impl;

import br.com.vendas.api.controller.ClienteAPI;
import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.handler.ClienteHandler;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ClienteController implements ClienteAPI {

  private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

  private final ClienteHandler clienteHandler;


  @PostMapping
  @Timed(value = "clientes.create", description = "Time taken to create a client")

  public ResponseEntity<ClienteResponseDTO> criar(
      @Parameter(description = "Dados do cliente a ser criado", required = true)
      @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
    logger.debug("Recebida requisição para criar cliente: {}", clienteRequestDTO.nome());
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteHandler.criar(clienteRequestDTO));
  }

  @GetMapping
  @Timed(value = "clientes.list", description = "Time taken to list all clients")

  public ResponseEntity<List<ClienteResponseDTO>> buscarAtivos() {
    logger.debug("Recebida requisição para listar clientes ativos");
    return ResponseEntity.ok(clienteHandler.buscarAtivos());
  }

  @GetMapping("/{id}")
  @Timed(value = "clientes.get", description = "Time taken to get a client by ID")

  public ResponseEntity<ClienteResponseDTO> buscarPorId(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para buscar cliente por ID: {}", id);
    return ResponseEntity.ok(clienteHandler.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Timed(value = "clientes.update", description = "Time taken to update a client")

  public ResponseEntity<ClienteResponseDTO> atualizar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados do cliente", required = true)
      @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
    logger.debug("Recebida requisição para atualizar cliente ID: {}", id);
    return ResponseEntity.ok(clienteHandler.atualizar(clienteRequestDTO, id));
  }

  @PatchMapping("/{id}/ativar")
  @Timed(value = "clientes.activate", description = "Time taken to activate a client")

  public ResponseEntity<Void> ativar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para ativar cliente ID: {}", id);
    clienteHandler.ativar(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/desativar")
  @Timed(value = "clientes.deactivate", description = "Time taken to deactivate a client")

  public ResponseEntity<Void> desativar(
      @Parameter(description = "ID do cliente", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para desativar cliente ID: {}", id);

    clienteHandler.desativar(id);

    return ResponseEntity.noContent().build();
  }
}

