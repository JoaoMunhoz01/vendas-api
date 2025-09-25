package br.com.vendas.api.exception;

public class VendaDuplicadaException extends RuntimeException {

  public VendaDuplicadaException(String numeroVenda) {
    super("Já existe uma venda com o número: " + numeroVenda);
  }
}

