package br.com.vendas.api.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {
  VENDA_NAO_ENCONTRADA("VENDA_NAO_ENCONTRADA", "Venda não encontrada."),
  VENDA_DUPLICADA("VENDA_DUPLICADA", "Tentativa de criar venda duplicada."),
  CLIENTE_NAO_ENCONTRADO("CLIENTE_NAO_ENCONTRADO", "Cliente não encontrado."),
  FILIAL_NAO_ENCONTRADA("FILIAL_NAO_ENCONTRADA", "Filial não encontrada."),
  REGRA_DE_NEGOCIO_VIOLADA("REGRA_DE_NEGOCIO_VIOLADA", "Regra de negócio violada."),
  DADOS_INVALIDOS("DADOS_INVALIDOS", "Dados de entrada inválidos."),
  FORMATO_JSON_INVALIDO("FORMATO_JSON_INVALIDO", "Formato JSON inválido ou malformado."),
  TIPO_PARAMETRO_INVALIDO("TIPO_PARAMETRO_INVALIDO", "Tipo de parâmetro inválido."),
  PARAMETRO_OBRIGATORIO_AUSENTE("PARAMETRO_OBRIGATORIO_AUSENTE", "Parâmetro obrigatório ausente."),
  METODO_NAO_SUPORTADO("METODO_NAO_SUPORTADO", "Método HTTP não suportado para este endpoint."),
  ENDPOINT_NAO_ENCONTRADO("ENDPOINT_NAO_ENCONTRADO", "Endpoint não encontrado."),
  VIOLACAO_INTEGRIDADE_DADOS("VIOLACAO_INTEGRIDADE_DADOS", "Violação de restrição de dados."),
  DADOS_DUPLICADOS("DADOS_DUPLICADOS", "Já existe um registro com estes dados únicos."),
  REFERENCIA_INVALIDA("REFERENCIA_INVALIDA", "Referência inválida entre dados relacionados."),
  ARGUMENTO_INVALIDO("ARGUMENTO_INVALIDO", "Argumento inválido fornecido."),
  ERRO_INTERNO("ERRO_INTERNO", "Erro interno do servidor. Tente novamente mais tarde.");

  private final String code;
  private final String message;

  ErrorEnum(String code, String message) {
    this.code = code;
    this.message = message;
  }

}


