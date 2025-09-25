package br.com.vendas.api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "vendas")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @NotBlank
  @Size(max = 100)
  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;

  @Column(name = "data_cadastro", nullable = false)
  private LocalDateTime dataCadastro = LocalDateTime.now();

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Venda> vendas = new HashSet<>();

  public Cliente(String nome) {
    this.nome = nome;
    this.dataCadastro = LocalDateTime.now();
    this.ativo = true;
  }

  public void ativar() {
    this.ativo = true;
  }

  public void desativar() {
    this.ativo = false;
  }

  public boolean isAtivo() {
    return Boolean.TRUE.equals(this.ativo);
  }
}

