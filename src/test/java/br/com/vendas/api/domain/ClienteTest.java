package br.com.vendas.api.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteTest {

  @Test
  @DisplayName("Deve criar cliente com builder corretamente")
  void deveCriarClienteComBuilder() {
    LocalDateTime dataCadastro = LocalDateTime.now();

    Cliente cliente = Cliente.builder()
        .id(1L)
        .nome("João Silva")
        .ativo(true)
        .dataCadastro(dataCadastro)
        .vendas(new HashSet<>())
        .build();

    assertAll("Cliente criado com builder",
        () -> assertEquals(1L, cliente.getId()),
        () -> assertEquals("João Silva", cliente.getNome()),
        () -> assertTrue(cliente.getAtivo()),
        () -> assertEquals(dataCadastro, cliente.getDataCadastro()),
        () -> assertNotNull(cliente.getVendas()),
        () -> assertTrue(cliente.getVendas().isEmpty())
    );
  }

  @Test
  @DisplayName("Deve criar cliente com construtor personalizado")
  void deveCriarClienteComConstrutorPersonalizado() {
    Cliente cliente = new Cliente("Maria Oliveira");

    assertAll("Cliente criado com construtor personalizado",
        () -> assertNull(cliente.getId()),
        () -> assertEquals("Maria Oliveira", cliente.getNome()),
        () -> assertTrue(cliente.getAtivo()),
        () -> assertNotNull(cliente.getDataCadastro()),
        () -> assertTrue(cliente.isAtivo())
    );
  }

  @Test
  @DisplayName("Deve ativar e desativar cliente")
  void deveAtivarEDesativarCliente() {
    Cliente cliente = Cliente.builder()
        .nome("Cliente Teste")
        .ativo(false)
        .build();

    cliente.ativar();
    assertTrue(cliente.getAtivo());
    assertTrue(cliente.isAtivo());

    cliente.desativar();
    assertFalse(cliente.getAtivo());
    assertFalse(cliente.isAtivo());
  }

  @Test
  @DisplayName("Deve verificar equals e hashCode")
  void deveVerificarEqualsHashCode() {
    Cliente cliente1 = Cliente.builder().id(1L).nome("Cliente 1").build();
    Cliente cliente2 = Cliente.builder().id(1L).nome("Cliente 2").build();
    Cliente cliente3 = Cliente.builder().id(2L).nome("Cliente 1").build();

    assertEquals(cliente1, cliente2);
    assertNotEquals(cliente1, cliente3);
    assertEquals(cliente1.hashCode(), cliente2.hashCode());
  }

  @Test
  @DisplayName("Deve usar toBuilder corretamente")
  void deveUsarToBuilder() {
    Cliente clienteOriginal = Cliente.builder()
        .id(1L)
        .nome("Original")
        .ativo(true)
        .build();

    Cliente clienteModificado = clienteOriginal.toBuilder()
        .nome("Modificado")
        .ativo(false)
        .build();

    assertAll("Cliente modificado com toBuilder",
        () -> assertEquals(1L, clienteModificado.getId()),
        () -> assertEquals("Modificado", clienteModificado.getNome()),
        () -> assertFalse(clienteModificado.getAtivo())
    );
  }
}
