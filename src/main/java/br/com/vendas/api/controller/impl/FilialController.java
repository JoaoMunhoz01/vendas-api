package br.com.vendas.api.controller.impl;

import br.com.vendas.api.controller.FilialAPI;
import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.handler.FilialHandler;
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
@AllArgsConstructor
@RequestMapping("/api/filiais")
@CrossOrigin(origins = "*")
public class FilialController implements FilialAPI {

  private static final Logger logger = LoggerFactory.getLogger(FilialController.class);
  private final FilialHandler filialHandler;

  @PostMapping
  @Timed(value = "filiais.create", description = "Time taken to create a branch")

  public ResponseEntity<FilialResponseDTO> criar(
      @Parameter(description = "Dados da filial a ser criada", required = true)
      @Valid @RequestBody FilialRequestDTO filialRequestDTO) {
    logger.debug("Recebida requisição para criar filial: {}", filialRequestDTO.nome());

    return ResponseEntity.status(HttpStatus.CREATED).body(filialHandler.criar(filialRequestDTO));
  }

  @GetMapping
  @Timed(value = "filiais.list", description = "Time taken to list all branches")

  public ResponseEntity<List<FilialResponseDTO>> buscarAtivas() {
    logger.debug("Recebida requisição para listar filiais ativas");

    return ResponseEntity.ok(filialHandler.buscarAtivas());
  }

  @GetMapping("/{id}")
  @Timed(value = "filiais.get", description = "Time taken to get a branch by ID")

  public ResponseEntity<FilialResponseDTO> buscarPorId(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para buscar filial por ID: {}", id);

    return ResponseEntity.ok(filialHandler.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Timed(value = "filiais.update", description = "Time taken to update a branch")

  public ResponseEntity<FilialResponseDTO> atualizar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados da filial", required = true)
      @Valid @RequestBody FilialRequestDTO filialRequestDTO) {
    logger.debug("Recebida requisição para atualizar filial ID: {}", id);

    return ResponseEntity.ok(filialHandler.atualizar(filialRequestDTO, id));
  }

  @PatchMapping("/{id}/ativar")
  @Timed(value = "filiais.activate", description = "Time taken to activate a branch")

  public ResponseEntity<Void> ativar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para ativar filial ID: {}", id);

    filialHandler.ativar(id);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/desativar")
  @Timed(value = "filiais.deactivate", description = "Time taken to deactivate a branch")

  public ResponseEntity<Void> desativar(
      @Parameter(description = "ID da filial", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para desativar filial ID: {}", id);

    filialHandler.desativar(id);

    return ResponseEntity.noContent().build();
  }
}

