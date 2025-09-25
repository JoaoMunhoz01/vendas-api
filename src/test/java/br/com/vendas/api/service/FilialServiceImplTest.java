package br.com.vendas.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.repository.FilialRepository;
import br.com.vendas.api.exception.FilialNotFoundException;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.impl.FilialServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilialServiceImplTest {

  @Mock
  private FilialRepository filialRepository;

  @InjectMocks
  private FilialServiceImpl filialService;

  private Filial filial;
  private Filial filialAtualizada;

  @BeforeEach
  void setUp() {
    filial = new Filial();
    filial.setId(1L);
    filial.setNome("Filial Centro");
    filial.setAtiva(true);

    filialAtualizada = new Filial();
    filialAtualizada.setNome("Filial Centro Atualizada");
  }

  @Test
  void criar_DeveSalvarFilial_QuandoDadosValidos() {
    when(filialRepository.save(any(Filial.class))).thenReturn(filial);

    Filial resultado = filialService.criar(filial);

    assertNotNull(resultado);
    assertTrue(resultado.isAtiva());
    assertNotNull(resultado.getDataCadastro());
    verify(filialRepository, times(1)).save(filial);
  }

  @Test
  void criar_DeveLancarExcecao_QuandoNomeNulo() {
    filial.setNome(null);

    RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
        () -> filialService.criar(filial));

    assertEquals("Nome da filial é obrigatório", exception.getMessage());
    verify(filialRepository, never()).save(any());
  }

  @Test
  void buscarPorId_DeveRetornarFilial_QuandoExistir() {
    when(filialRepository.findById(1L)).thenReturn(Optional.of(filial));

    Filial resultado = filialService.buscarPorId(1L);

    assertNotNull(resultado);
    assertEquals(filial.getId(), resultado.getId());
    verify(filialRepository, times(1)).findById(1L);
  }

  @Test
  void buscarPorId_DeveLancarExcecao_QuandoNaoExistir() {
    when(filialRepository.findById(1L)).thenReturn(Optional.empty());

    FilialNotFoundException exception = assertThrows(FilialNotFoundException.class,
        () -> filialService.buscarPorId(1L));

    assertEquals("Filial não encontrada com ID: 1", exception.getMessage());
    verify(filialRepository, times(1)).findById(1L);
  }

  @Test
  void buscarAtivas_DeveRetornarListaFiliaisAtivas() {
    List<Filial> filiaisAtivas = Collections.singletonList(filial);
    when(filialRepository.findByAtivaTrue()).thenReturn(filiaisAtivas);

    List<Filial> resultado = filialService.buscarAtivas();

    assertFalse(resultado.isEmpty());
    assertEquals(1, resultado.size());
    verify(filialRepository, times(1)).findByAtivaTrue();
  }

  @Test
  void atualizar_DeveAtualizarFilial_QuandoDadosValidos() {
    when(filialRepository.findById(1L)).thenReturn(Optional.of(filial));
    when(filialRepository.save(any(Filial.class))).thenReturn(filial);

    Filial resultado = filialService.atualizar(1L, filialAtualizada);

    assertNotNull(resultado);
    assertEquals("Filial Centro Atualizada", filial.getNome());
    verify(filialRepository, times(1)).findById(1L);
    verify(filialRepository, times(1)).save(filial);
  }

  @Test
  void ativar_DeveAtivarFilial() {
    filial.setAtiva(false);
    when(filialRepository.findById(1L)).thenReturn(Optional.of(filial));
    when(filialRepository.save(any(Filial.class))).thenReturn(filial);

    filialService.ativar(1L);

    assertTrue(filial.isAtiva());
    verify(filialRepository, times(1)).findById(1L);
    verify(filialRepository, times(1)).save(filial);
  }

  @Test
  void desativar_DeveDesativarFilial() {
    when(filialRepository.findById(1L)).thenReturn(Optional.of(filial));
    when(filialRepository.save(any(Filial.class))).thenReturn(filial);

    filialService.desativar(1L);

    assertFalse(filial.isAtiva());
    verify(filialRepository, times(1)).findById(1L);
    verify(filialRepository, times(1)).save(filial);
  }
}
