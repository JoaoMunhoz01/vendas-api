package br.com.vendas.api.event;

import java.math.BigDecimal;

public class ItemCanceladoEvent extends VendaEvent {

  private final Long itemId;
  private final Long produtoId;
  private final Integer quantidade;
  private final BigDecimal valorItem;

  public ItemCanceladoEvent(Long vendaId, String numeroVenda, Long itemId,
      Long produtoId, Integer quantidade, BigDecimal valorItem) {
    super(vendaId, numeroVenda);
    this.itemId = itemId;
    this.produtoId = produtoId;
    this.quantidade = quantidade;
    this.valorItem = valorItem;
  }

  @Override
  public String getEventType() {
    return "ItemCancelado";
  }

  public Long getItemId() {
    return itemId;
  }

  public Long getProdutoId() {
    return produtoId;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public BigDecimal getValorItem() {
    return valorItem;
  }
}

