package br.com.vendas.api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "vendaItens")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @NotBlank
  @Size(max = 200)
  @Column(name = "descricao", nullable = false, length = 200)
  private String descricao;

  @NotNull
  @Positive
  @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
  private BigDecimal precoUnitario;

  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;

  @Column(name = "data_cadastro", nullable = false)
  private LocalDateTime dataCadastro = LocalDateTime.now();

  @Column(name = "data_ultima_atualizacao")
  private LocalDateTime dataUltimaAtualizacao;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<VendaItem> vendaItens = new HashSet<>();

  public void ativar() {
    this.ativo = true;
    this.dataUltimaAtualizacao = LocalDateTime.now();
  }

  public void desativar() {
    this.ativo = false;
    this.dataUltimaAtualizacao = LocalDateTime.now();
  }


  public void atualizarPreco(BigDecimal novoPreco) {
    this.precoUnitario = novoPreco;
    this.dataUltimaAtualizacao = LocalDateTime.now();
  }


  @PreUpdate
  private void preUpdate() {
    this.dataUltimaAtualizacao = LocalDateTime.now();
  }
}

