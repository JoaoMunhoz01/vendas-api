package br.com.vendas.api.service.impl;

import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.repository.ItemRepository;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.ItemService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

  private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

  private final ItemRepository itemRepository;

  @Transactional
  public Item criar(Item item) {
    logger.debug("Criando novo item: {}", item.getId());

    validarDados(item);

    Item itemSalvo = itemRepository.save(item);
    logger.info("Item criado com sucesso: ID {}", itemSalvo.getId());

    return itemSalvo;
  }

  public Item buscarPorId(Long id) {
    logger.debug("Buscando item por ID: {}", id);

    return itemRepository.findById(id)
        .orElseThrow(() -> new RegraDeNegocioException("Item não encontrado com ID: " + id));
  }

  public List<Item> buscarAtivos() {
    logger.debug("Listando itens ativos");

    return itemRepository.findByAtivoTrue();
  }

  @Transactional
  public Item atualizar(Long id, Item itemAtualizado) {
    logger.debug("Atualizando item ID: {}", id);

    Item itemExistente = buscarPorId(id);

    validarDados(itemAtualizado);

    atualizarDadosItem(itemExistente, itemAtualizado);

    Item itemSalvo = itemRepository.save(itemExistente);
    logger.info("Item atualizado com sucesso: ID {}", itemSalvo.getId());

    return itemSalvo;
  }

  @Transactional
  public void ativar(Long id) {
    logger.debug("Ativando item ID: {}", id);

    Item item = buscarPorId(id);
    item.ativar();
    itemRepository.save(item);

    logger.info("Item ativado com sucesso: ID {}", id);
  }

  @Transactional
  public void desativar(Long id) {
    logger.debug("Desativando item ID: {}", id);

    Item item = buscarPorId(id);
    item.desativar();
    itemRepository.save(item);

    logger.info("Item desativado com sucesso: ID {}", id);
  }

  private void validarDados(Item item) {
    if (StringUtils.isBlank(item.getDescricao())) {
      throw new RegraDeNegocioException("Descrição do item é obrigatória");
    }

    validarPreco(item.getPrecoUnitario());
  }

  private void validarPreco(BigDecimal preco) {
    if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
      throw new RegraDeNegocioException("Preço unitário deve ser maior que zero");
    }
  }


  private void atualizarDadosItem(Item itemExistente, Item itemAtualizado) {
    itemExistente.setDescricao(itemAtualizado.getDescricao());
    itemExistente.setPrecoUnitario(itemAtualizado.getPrecoUnitario());
    itemExistente.setDataUltimaAtualizacao(LocalDateTime.now());
  }
}

