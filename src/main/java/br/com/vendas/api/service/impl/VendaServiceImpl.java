package br.com.vendas.api.service.impl;


import br.com.vendas.api.domain.Venda;
import br.com.vendas.api.domain.repository.VendaRepository;
import br.com.vendas.api.event.CompraAlteradaEvent;
import br.com.vendas.api.event.CompraCanceladaEvent;
import br.com.vendas.api.event.CompraEfetuadaEvent;
import br.com.vendas.api.exception.VendaDuplicadaException;
import br.com.vendas.api.exception.VendaNotFoundException;
import br.com.vendas.api.service.VendaService;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VendaServiceImpl implements VendaService {

  private static final Logger logger = LoggerFactory.getLogger(VendaServiceImpl.class);

  private final VendaRepository vendaRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public Venda criar(Venda venda) {
    logger.debug("Criando nova venda: {}", venda.getNumeroVenda());

    verificarNumeroVendaUnico(venda.getNumeroVenda());

    publicarEventoCompraEfetuada(venda);

    vendaRepository.save(venda);

    logger.info("Venda criada com sucesso: {}", venda.getNumeroVenda());
    return venda;
  }

  public List<Venda> listar() {
    logger.debug("Listando todas as vendas");
    return vendaRepository.findAll();
  }

  public Venda buscarPorId(Long id) {
    logger.debug("Buscando venda por ID: {}", id);

    return vendaRepository.findById(id)
        .orElseThrow(() -> new VendaNotFoundException("Venda não encontrada com ID: " + id));
  }

  @Transactional
  public Venda atualizar(Venda venda) {
    logger.debug("Atualizando venda ID: {}", venda.getId());

    BigDecimal valorAnterior = venda.getValorTotalVenda();

    verificarNumeroVendaUnicoParaAtualizacao(venda, venda.getNumeroVenda());

    venda = vendaRepository.save(venda);

    publicarEventoCompraAlterada(venda, valorAnterior);

    logger.info("Venda atualizada com sucesso: {}", venda.getNumeroVenda());
    return venda;
  }

  @Transactional
  public void deletar(Long id) {
    logger.debug("Deletando venda ID: {}", id);

    Venda venda = buscarPorId(id);

    publicarEventoCompraCancelada(venda, "Venda deletada");

    vendaRepository.delete(venda);
    logger.info("Venda deletada com sucesso: {}", venda.getNumeroVenda());
  }

  @Transactional
  public Venda cancelar(Long id) {
    logger.debug("Cancelando venda ID: {}", id);

    Venda venda = buscarPorId(id);

    venda.cancelar();
    venda = vendaRepository.save(venda);

    publicarEventoCompraCancelada(venda, "Venda cancelada pelo usuário");

    logger.info("Venda cancelada com sucesso: {}", venda.getNumeroVenda());
    return venda;
  }


  private void verificarNumeroVendaUnico(String numeroVenda) {
    if (vendaRepository.existsByNumeroVenda(numeroVenda)) {
      throw new VendaDuplicadaException("Já existe uma venda com o número: " + numeroVenda);
    }
  }

  private void verificarNumeroVendaUnicoParaAtualizacao(Venda venda, String novoNumeroVenda) {
    if (!venda.getNumeroVenda().equals(novoNumeroVenda)
        && vendaRepository.existsByNumeroVenda(novoNumeroVenda)) {
      throw new VendaDuplicadaException("Já existe uma venda com o número: " + novoNumeroVenda);
    }
  }

  private boolean numeroVendaAtualizacaoDisponivel(Venda venda, String novoNumeroVenda) {
    return !venda.getNumeroVenda().equals(novoNumeroVenda) &&
        vendaRepository.existsByNumeroVenda(novoNumeroVenda);
  }

  private void publicarEventoCompraEfetuada(Venda venda) {
    CompraEfetuadaEvent event = new CompraEfetuadaEvent(
        venda.getId(),
        venda.getNumeroVenda(),
        venda.getValorTotalVenda(),
        venda.getCliente().getId()
    );
    eventPublisher.publishEvent(event);
  }

  private void publicarEventoCompraAlterada(Venda venda, BigDecimal valorAnterior) {
    CompraAlteradaEvent event = new CompraAlteradaEvent(
        venda.getId(),
        venda.getNumeroVenda(),
        valorAnterior,
        venda.getValorTotalVenda()
    );
    eventPublisher.publishEvent(event);
  }

  private void publicarEventoCompraCancelada(Venda venda, String motivo) {
    CompraCanceladaEvent event = new CompraCanceladaEvent(
        venda.getId(),
        venda.getNumeroVenda(),
        venda.getValorTotalVenda(),
        motivo
    );
    eventPublisher.publishEvent(event);
  }
}

