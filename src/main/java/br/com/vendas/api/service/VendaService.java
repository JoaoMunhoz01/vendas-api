package br.com.vendas.api.service;

import br.com.vendas.api.domain.Venda;
import java.util.List;


public interface VendaService {

  Venda criar(Venda venda);

  List<Venda> listar();

  Venda buscarPorId(Long id);

  Venda atualizar(Venda venda);

  void deletar(Long id);

  Venda cancelar(Long id);
}
