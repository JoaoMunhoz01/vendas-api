package br.com.vendas.api.event;

import java.math.BigDecimal;

public class CompraAlteradaEvent extends VendaEvent {

  private final BigDecimal valorAnterior;
  private final BigDecimal valorAtual;

  public CompraAlteradaEvent(Long vendaId, String numeroVenda, BigDecimal valorAnterior,
      BigDecimal valorAtual) {
    super(vendaId, numeroVenda);
    this.valorAnterior = valorAnterior;
    this.valorAtual = valorAtual;
  }

  @Override
  public String getEventType() {
    return "CompraAlterada";
  }

  public BigDecimal getValorAnterior() {
    return valorAnterior;
  }

  public BigDecimal getValorAtual() {
    return valorAtual;
  }
}

