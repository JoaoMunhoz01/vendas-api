package br.com.vendas.api.service;

import br.com.vendas.api.domain.Filial;
import java.util.List;

public interface FilialService {

  Filial criar(Filial filial);

  Filial buscarPorId(Long id);

  List<Filial> buscarAtivas();

  Filial atualizar(Long id, Filial filialAtualizada);

  void ativar(Long id);

  void desativar(Long id);

}

