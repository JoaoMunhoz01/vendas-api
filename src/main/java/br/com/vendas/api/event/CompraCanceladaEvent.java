package br.com.vendas.api.event;

import java.math.BigDecimal;

public class CompraCanceladaEvent extends VendaEvent {

  private final BigDecimal valorCancelado;
  private final String motivo;

  public CompraCanceladaEvent(Long vendaId, String numeroVenda, BigDecimal valorCancelado,
      String motivo) {
    super(vendaId, numeroVenda);
    this.valorCancelado = valorCancelado;
    this.motivo = motivo;
  }

  @Override
  public String getEventType() {
    return "CompraCancelada";
  }

  public BigDecimal getValorCancelado() {
    return valorCancelado;
  }

  public String getMotivo() {
    return motivo;
  }
}

