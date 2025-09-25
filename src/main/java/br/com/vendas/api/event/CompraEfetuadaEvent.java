package br.com.vendas.api.event;

import java.math.BigDecimal;

public class CompraEfetuadaEvent extends VendaEvent {

  private final BigDecimal valorTotal;
  private final Long clienteId;

  public CompraEfetuadaEvent(Long vendaId, String numeroVenda, BigDecimal valorTotal,
      Long clienteId) {
    super(vendaId, numeroVenda);
    this.valorTotal = valorTotal;
    this.clienteId = clienteId;
  }

  @Override
  public String getEventType() {
    return "CompraEfetuada";
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public Long getClienteId() {
    return clienteId;
  }
}

