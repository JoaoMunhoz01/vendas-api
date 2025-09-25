package br.com.vendas.api.service;

import br.com.vendas.api.domain.Cliente;
import java.util.List;

public interface ClienteService {

  Cliente criar(Cliente cliente);

  Cliente buscarPorId(Long id);

  List<Cliente> buscarAtivos();

  Cliente atualizar(Long id, Cliente clienteAtualizado);

  void ativar(Long id);

  void desativar(Long id);

}

