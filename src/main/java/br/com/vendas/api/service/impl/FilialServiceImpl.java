package br.com.vendas.api.service.impl;

import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.repository.FilialRepository;
import br.com.vendas.api.exception.FilialNotFoundException;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.FilialService;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FilialServiceImpl implements FilialService {

  private static final Logger logger = LoggerFactory.getLogger(FilialServiceImpl.class);

  private final FilialRepository filialRepository;

  @Transactional
  public Filial criar(Filial filial) {
    logger.debug("Criando nova filial: {}", filial.getNome());

    validarDados(filial);

    filial.setAtiva(Boolean.TRUE);
    filial.setDataCadastro(LocalDateTime.now());

    Filial filialSalva = filialRepository.save(filial);
    logger.info("Filial criada com sucesso: ID {}", filialSalva.getId());

    return filialSalva;
  }

  public Filial buscarPorId(Long id) {
    logger.debug("Buscando filial por ID: {}", id);

    return filialRepository.findById(id)
        .orElseThrow(() -> new FilialNotFoundException("Filial não encontrada com ID: " + id));
  }

  public List<Filial> buscarAtivas() {
    logger.debug("Listando filiais ativas");

    return filialRepository.findByAtivaTrue();
  }

  @Transactional
  public Filial atualizar(Long id, Filial filialAtualizada) {
    logger.debug("Atualizando filial ID: {}", id);

    Filial filialExistente = buscarPorId(id);

    validarDados(filialAtualizada);

    atualizarDados(filialExistente, filialAtualizada);

    Filial filialSalva = filialRepository.save(filialExistente);
    logger.info("Filial atualizada com sucesso: ID {}", filialSalva.getId());

    return filialSalva;
  }

  @Transactional
  public void ativar(Long id) {
    logger.debug("Ativando filial ID: {}", id);

    Filial filial = buscarPorId(id);
    filial.ativar();
    filialRepository.save(filial);

    logger.info("Filial ativada com sucesso: ID {}", id);
  }

  @Transactional
  public void desativar(Long id) {
    logger.debug("Desativando filial ID: {}", id);

    Filial filial = buscarPorId(id);
    filial.desativar();
    filialRepository.save(filial);

    logger.info("Filial desativada com sucesso: ID {}", id);
  }

  private void validarDados(Filial filial) {
    if (StringUtils.isBlank(filial.getNome())) {
      throw new RegraDeNegocioException("Nome da filial é obrigatório");
    }
  }

  private void atualizarDados(Filial filialExistente, Filial filialAtualizada) {
    filialExistente.setNome(filialAtualizada.getNome());
  }
}

