package br.com.vendas.api.controller.impl;

import br.com.vendas.api.controller.VendaAPI;
import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.handler.VendaHandler;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VendaController implements VendaAPI {

  private static final Logger logger = LoggerFactory.getLogger(VendaController.class);

  private VendaHandler vendaHandler;

  @PostMapping
  @Timed(value = "vendas.create", description = "Time taken to create a sale")
  public ResponseEntity<VendaResponseDTO> criar(
      @Parameter(description = "Dados do cliente a ser criado", required = true)
      @Valid @RequestBody VendaRequestDTO vendaRequestDTO) {
    logger.debug("Recebida requisição para criar venda: {}", vendaRequestDTO.numeroVenda());

    return ResponseEntity.status(HttpStatus.CREATED).body(vendaHandler.criar(vendaRequestDTO));

  }

  @GetMapping
  @Timed(value = "vendas.list", description = "Time taken to list all sales")
  public ResponseEntity<List<VendaResponseDTO>> listar() {
    logger.debug("Recebida requisição para listar vendas");

    return ResponseEntity.ok(vendaHandler.listar());
  }

  @GetMapping("/{id}")
  @Timed(value = "vendas.get", description = "Time taken to get a sale by ID")
  public ResponseEntity<VendaResponseDTO> buscarPorId(
      @PathVariable Long id) {
    logger.debug("Recebida requisição para buscar venda por ID: {}", id);

    return ResponseEntity.ok(vendaHandler.buscarPorId(id));

  }

  @PutMapping("/{id}")
  @Timed(value = "vendas.update", description = "Time taken to update a sale")
  public ResponseEntity<VendaResponseDTO> atualizar(
      @PathVariable Long id,
      @Valid @RequestBody VendaRequestDTO vendaRequestDTO) {
    logger.debug("Recebida requisição para atualizar venda ID: {}", id);

    return ResponseEntity.ok(vendaHandler.atualizar(id, vendaRequestDTO));

  }

  @DeleteMapping("/{id}")
  @Timed(value = "vendas.delete", description = "Time taken to delete a sale")
  public ResponseEntity<Void> deletar(
      @PathVariable Long id) {
    logger.debug("Recebida requisição para deletar venda ID: {}", id);

    vendaHandler.deletar(id);
    return ResponseEntity.noContent().build();

  }

  @PatchMapping("/{id}/cancelar")
  @Timed(value = "vendas.cancel", description = "Time taken to cancel a sale")
  public ResponseEntity<VendaResponseDTO> cancelar(
      @PathVariable Long id) {
    logger.debug("Recebida requisição para cancelar venda ID: {}", id);

    return ResponseEntity.ok(vendaHandler.cancelar(id));
  }
}

