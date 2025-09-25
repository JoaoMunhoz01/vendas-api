package br.com.vendas.api.exception;

public class VendaNotFoundException extends RuntimeException {

  public VendaNotFoundException(String message) {
    super(message);
  }

  public VendaNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public VendaNotFoundException(Long id) {
    super("Venda não encontrada com ID: " + id);
  }

  public VendaNotFoundException(String numeroVenda, boolean byNumero) {
    super("Venda não encontrada com número: " + numeroVenda);
  }
}

