package br.com.vendas.api.domain.repository;

import br.com.vendas.api.domain.Filial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {

  List<Filial> findByAtivaTrue();
}

