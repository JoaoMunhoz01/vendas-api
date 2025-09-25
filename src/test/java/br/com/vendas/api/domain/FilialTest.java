package br.com.vendas.api.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FilialTest {

  @Test
  @DisplayName("Deve criar filial com builder corretamente")
  void deveCriarFilialComBuilder() {
    LocalDateTime dataCadastro = LocalDateTime.now();

    Filial filial = Filial.builder()
        .id(1L)
        .nome("Filial Centro")
        .ativa(true)
        .dataCadastro(dataCadastro)
        .vendas(new HashSet<>())
        .build();

    assertAll("Filial criada com builder",
        () -> assertEquals(1L, filial.getId()),
        () -> assertEquals("Filial Centro", filial.getNome()),
        () -> assertTrue(filial.getAtiva()),
        () -> assertEquals(dataCadastro, filial.getDataCadastro()),
        () -> assertNotNull(filial.getVendas()),
        () -> assertTrue(filial.getVendas().isEmpty())
    );
  }

  @Test
  @DisplayName("Deve criar filial com construtor personalizado")
  void deveCriarFilialComConstrutorPersonalizado() {
    Filial filial = new Filial("Filial Zona Sul");

    assertAll("Filial criada com construtor personalizado",
        () -> assertNull(filial.getId()),
        () -> assertEquals("Filial Zona Sul", filial.getNome()),
        () -> assertTrue(filial.getAtiva()),
        () -> assertNotNull(filial.getDataCadastro()),
        () -> assertTrue(filial.isAtiva())
    );
  }

  @Test
  @DisplayName("Deve ativar e desativar filial")
  void deveAtivarEDesativarFilial() {
    Filial filial = Filial.builder()
        .nome("Filial Teste")
        .ativa(false)
        .build();

    filial.ativar();
    assertTrue(filial.getAtiva());
    assertTrue(filial.isAtiva());

    filial.desativar();
    assertFalse(filial.getAtiva());
    assertFalse(filial.isAtiva());
  }
}
