package br.com.vendas.api.service;

import br.com.vendas.api.domain.Item;
import java.util.List;

public interface ItemService {

  Item criar(Item item);

  Item buscarPorId(Long id);

  List<Item> buscarAtivos();

  Item atualizar(Long id, Item itemAtualizado);

  void ativar(Long id);

  void desativar(Long id);

}
