package br.com.vendas.api.domain;

import br.com.vendas.api.exception.RegraDeNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "venda_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"venda", "item"})
public class VendaItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;

  @NotNull
  @Positive
  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;

  @NotNull
  @PositiveOrZero
  @Column(name = "desconto", nullable = false, precision = 10, scale = 2)
  private BigDecimal desconto = BigDecimal.ZERO;

  @NotNull
  @PositiveOrZero
  @Column(name = "valor_total_item", nullable = false, precision = 10, scale = 2)
  private BigDecimal valorTotalItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venda_id")
  private Venda venda;


  public void calcularDesconto() {
    if (quantidade < 4) {
      this.desconto = BigDecimal.ZERO;
    } else if (quantidade < 10) {
      this.desconto = item.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade))
          .multiply(BigDecimal.valueOf(0.10));
    } else if (quantidade <= 20) {
      this.desconto = item.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade))
          .multiply(BigDecimal.valueOf(0.20));
    } else {
      throw new RegraDeNegocioException("Não é possível vender acima de 20 itens iguais");
    }
  }

  public void calcularValorTotalItem() {
    BigDecimal valorBruto = item.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade));
    this.valorTotalItem = valorBruto.subtract(desconto);
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
    this.calcularDesconto();
    this.calcularValorTotalItem();
  }

  public String getProdutoDescritivo() {
    return item != null ? item.getDescricao() : null;
  }
}

