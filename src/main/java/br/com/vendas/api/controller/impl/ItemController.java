package br.com.vendas.api.controller.impl;

import br.com.vendas.api.controller.ItemAPI;
import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.handler.ItemHandler;
import br.com.vendas.api.mapper.ItemMapper;
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
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ItemController implements ItemAPI {

  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

  private final ItemHandler itemHandler;

  @PostMapping
  @Timed(value = "itens.create", description = "Time taken to create an item")

  public ResponseEntity<ItemResponseDTO> criar(
      @Parameter(description = "Dados do item a ser criado", required = true)
      @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
    logger.debug("Recebida requisição para criar item: {}", itemRequestDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(itemHandler.criar(itemRequestDTO));
  }

  @GetMapping
  @Timed(value = "itens.list", description = "Time taken to list all items")

  public ResponseEntity<List<ItemResponseDTO>> buscarAtivos() {
    logger.debug("Recebida requisição para listar itens ativos");

    return ResponseEntity.ok(itemHandler.listar());
  }

  @GetMapping("/{id}")
  @Timed(value = "itens.get", description = "Time taken to get an item by ID")

  public ResponseEntity<ItemResponseDTO> buscarPorId(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para buscar item por ID: {}", id);

    return ResponseEntity.ok(itemHandler.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Timed(value = "itens.update", description = "Time taken to update an item")

  public ResponseEntity<ItemResponseDTO> atualizar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id,
      @Parameter(description = "Novos dados do item", required = true)
      @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
    logger.debug("Recebida requisição para atualizar item ID: {}", id);

    return ResponseEntity.ok(itemHandler.atualizar(itemRequestDTO, id));
  }

  @PatchMapping("/{id}/ativar")
  @Timed(value = "itens.activate", description = "Time taken to activate an item")

  public ResponseEntity<Void> ativar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para ativar item ID: {}", id);

    itemHandler.ativar(id);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/desativar")
  @Timed(value = "itens.deactivate", description = "Time taken to deactivate an item")

  public ResponseEntity<Void> desativar(
      @Parameter(description = "ID do item", required = true)
      @PathVariable Long id) {
    logger.debug("Recebida requisição para desativar item ID: {}", id);

    itemHandler.desativar(id);

    return ResponseEntity.noContent().build();
  }
}

