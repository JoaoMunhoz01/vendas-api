package br.com.vendas.api.exception;

public class FilialNotFoundException extends RuntimeException {

  public FilialNotFoundException(String message) {
    super(message);
  }

  public FilialNotFoundException(Long id) {
    super("Filial não encontrada com ID: " + id);
  }
}

