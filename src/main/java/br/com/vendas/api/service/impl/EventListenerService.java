package br.com.vendas.api.service.impl;

import br.com.vendas.api.event.CompraAlteradaEvent;
import br.com.vendas.api.event.CompraCanceladaEvent;
import br.com.vendas.api.event.CompraEfetuadaEvent;
import br.com.vendas.api.event.ItemCanceladoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventListenerService {

  private static final Logger logger = LoggerFactory.getLogger(EventListenerService.class);

  @EventListener
  public void handleCompraEfetuada(CompraEfetuadaEvent event) {
    logger.info("Evento processado: {} - Venda: {} - Cliente: {} - Valor: {}",
        event.getEventType(),
        event.getNumeroVenda(),
        event.getClienteId(),
        event.getValorTotal());
  }

  @EventListener
  public void handleCompraAlterada(CompraAlteradaEvent event) {
    logger.info("Evento processado: {} - Venda: {} - Valor anterior: {} - Valor atual: {}",
        event.getEventType(),
        event.getNumeroVenda(),
        event.getValorAnterior(),
        event.getValorAtual());
  }

  @EventListener
  public void handleCompraCancelada(CompraCanceladaEvent event) {
    logger.info("Evento processado: {} - Venda: {} - Valor cancelado: {} - Motivo: {}",
        event.getEventType(),
        event.getNumeroVenda(),
        event.getValorCancelado(),
        event.getMotivo());
  }

  @EventListener
  public void handleItemCancelado(ItemCanceladoEvent event) {
    logger.info(
        "Evento processado: {} - Venda: {} - Item: {} - Produto: {} - Quantidade: {} - Valor: {}",
        event.getEventType(),
        event.getNumeroVenda(),
        event.getItemId(),
        event.getProdutoId(),
        event.getQuantidade(),
        event.getValorItem());
  }
}

