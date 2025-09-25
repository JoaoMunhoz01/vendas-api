package br.com.vendas.api.domain.repository;

import br.com.vendas.api.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

  boolean existsByNumeroVenda(String numeroVenda);
}

