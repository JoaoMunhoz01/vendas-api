package br.com.vendas.api.domain;

import br.com.vendas.api.enums.StatusVenda;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"itens", "cliente", "filial"})
public class Venda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @NotNull
  @Column(name = "numero_venda", nullable = false, unique = true)
  private String numeroVenda;

  @NotNull
  @Column(name = "data_venda", nullable = false)
  private LocalDateTime dataVenda;

  @NotNull
  @PositiveOrZero
  @Column(name = "valor_total_venda", nullable = false, precision = 10, scale = 2)
  private BigDecimal valorTotalVenda = BigDecimal.ZERO;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status_venda", nullable = false)
  private StatusVenda statusVenda = StatusVenda.NAO_CANCELADO;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id", nullable = false)
  private Cliente cliente;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "filial_id", nullable = false)
  private Filial filial;

  @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<VendaItem> itens = new ArrayList<>();

  public Venda(String numeroVenda, Cliente cliente, Filial filial) {
    this.numeroVenda = numeroVenda;
    this.cliente = cliente;
    this.filial = filial;
    this.dataVenda = LocalDateTime.now();
  }

  public void adicionarItem(VendaItem item) {
    item.setVenda(this);
    if (this.itens == null) {
      this.itens = new ArrayList<>();
    }
    this.itens.add(item);
    this.calcularValorTotalVenda();
  }

  public void removerItem(VendaItem item) {
    this.itens.remove(item);
    item.setVenda(null);
    this.calcularValorTotalVenda();
  }

  public void calcularValorTotalVenda() {
    this.valorTotalVenda = itens.stream()
        .map(VendaItem::getValorTotalItem)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void cancelar() {
    this.statusVenda = StatusVenda.CANCELADO;
  }

  public void reativar() {
    this.statusVenda = StatusVenda.NAO_CANCELADO;
  }

  public boolean isCancelada() {
    return StatusVenda.CANCELADO.equals(this.statusVenda);
  }

  public BigDecimal getValorTotalDesconto() {
    return itens.stream()
        .map(VendaItem::getDesconto)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Integer getQuantidadeTotalItens() {
    return itens.stream()
        .mapToInt(VendaItem::getQuantidade)
        .sum();
  }

  public boolean temItens() {
    return !itens.isEmpty();
  }
}

