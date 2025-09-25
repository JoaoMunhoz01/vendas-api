package br.com.vendas.api.service.impl;

import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.repository.ClienteRepository;
import br.com.vendas.api.exception.ClienteNotFoundException;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.ClienteService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

  private final ClienteRepository clienteRepository;

  @Transactional
  public Cliente criar(Cliente cliente) {
    logger.debug("Criando novo cliente: {}", cliente.getNome());

    validarDados(cliente);

    Cliente clienteSalvo = clienteRepository.save(cliente);
    logger.info("Cliente criado com sucesso: ID {}", clienteSalvo.getId());

    return clienteSalvo;
  }

  public Cliente buscarPorId(Long id) {
    logger.debug("Buscando cliente por ID: {}", id);

    return clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com ID: " + id));
  }

  public List<Cliente> buscarAtivos() {
    logger.debug("Listando clientes ativos");

    return clienteRepository.findByAtivoTrue();
  }

  @Transactional
  public Cliente atualizar(Long id, Cliente clienteAtualizado) {
    logger.debug("Atualizando cliente ID: {}", id);

    Cliente clienteExistente = buscarPorId(id);

    validarDados(clienteAtualizado);

    atualizarDados(clienteExistente, clienteAtualizado);

    Cliente clienteSalvo = clienteRepository.save(clienteExistente);
    logger.info("Cliente atualizado com sucesso: ID {}", clienteSalvo.getId());

    return clienteSalvo;
  }

  @Transactional
  public void ativar(Long id) {
    logger.debug("Ativando cliente ID: {}", id);

    Cliente cliente = buscarPorId(id);
    cliente.ativar();
    clienteRepository.save(cliente);

    logger.info("Cliente ativado com sucesso: ID {}", id);
  }

  @Transactional
  public void desativar(Long id) {
    logger.debug("Desativando cliente ID: {}", id);

    Cliente cliente = buscarPorId(id);
    cliente.desativar();
    clienteRepository.save(cliente);

    logger.info("Cliente desativado com sucesso: ID {}", id);
  }

  private void validarDados(Cliente cliente) {
    if (StringUtils.isBlank(cliente.getNome())) {
      throw new RegraDeNegocioException("Nome do cliente é obrigatório");
    }
  }

  private void atualizarDados(Cliente clienteExistente, Cliente clienteAtualizado) {
    clienteExistente.setNome(clienteAtualizado.getNome());
  }
}

