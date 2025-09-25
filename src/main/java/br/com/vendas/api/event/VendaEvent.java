package br.com.vendas.api.event;

import java.time.LocalDateTime;

public abstract class VendaEvent {

  private final Long vendaId;
  private final String numeroVenda;
  private final LocalDateTime timestamp;

  public VendaEvent(Long vendaId, String numeroVenda) {
    this.vendaId = vendaId;
    this.numeroVenda = numeroVenda;
    this.timestamp = LocalDateTime.now();
  }

  public Long getVendaId() {
    return vendaId;
  }

  public String getNumeroVenda() {
    return numeroVenda;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public abstract String getEventType();
}

