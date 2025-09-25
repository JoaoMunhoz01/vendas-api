package br.com.vendas.api.service;

import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.repository.ItemRepository;
import br.com.vendas.api.exception.RegraDeNegocioException;
import br.com.vendas.api.service.impl.ItemServiceImpl;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private Item item;
    private Item itemAtualizado;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setDescricao("Notebook Dell");
        item.setPrecoUnitario(new BigDecimal("2500.00"));
        item.setAtivo(true);

        itemAtualizado = new Item();
        itemAtualizado.setDescricao("Notebook Dell Atualizado");
        itemAtualizado.setPrecoUnitario(new BigDecimal("2700.00"));
    }

    @Test
    void criar_DeveSalvarItem_QuandoDadosValidos() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item resultado = itemService.criar(item);

        assertNotNull(resultado);
        assertEquals(item.getId(), resultado.getId());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void criar_DeveLancarExcecao_QuandoDescricaoNula() {
        item.setDescricao(null);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> itemService.criar(item));

        assertEquals("Descrição do item é obrigatória", exception.getMessage());
        verify(itemRepository, never()).save(any());
    }

    @Test
    void criar_DeveLancarExcecao_QuandoPrecoZero() {
        item.setPrecoUnitario(BigDecimal.ZERO);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> itemService.criar(item));

        assertEquals("Preço unitário deve ser maior que zero", exception.getMessage());
        verify(itemRepository, never()).save(any());
    }

    @Test
    void criar_DeveLancarExcecao_QuandoPrecoNegativo() {
        item.setPrecoUnitario(new BigDecimal("-100.00"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> itemService.criar(item));

        assertEquals("Preço unitário deve ser maior que zero", exception.getMessage());
        verify(itemRepository, never()).save(any());
    }

    @Test
    void buscarPorId_DeveRetornarItem_QuandoExistir() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item resultado = itemService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(item.getId(), resultado.getId());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoExistir() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class,
            () -> itemService.buscarPorId(1L));

        assertEquals("Item não encontrado com ID: 1", exception.getMessage());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void buscarAtivos_DeveRetornarListaItensAtivos() {
        List<Item> itensAtivos = Collections.singletonList(item);
        when(itemRepository.findByAtivoTrue()).thenReturn(itensAtivos);

        List<Item> resultado = itemService.buscarAtivos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(itemRepository, times(1)).findByAtivoTrue();
    }

    @Test
    void atualizar_DeveAtualizarItem_QuandoDadosValidos() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item resultado = itemService.atualizar(1L, itemAtualizado);

        assertNotNull(resultado);
        assertEquals("Notebook Dell Atualizado", item.getDescricao());
        assertEquals(new BigDecimal("2700.00"), item.getPrecoUnitario());
        assertNotNull(item.getDataUltimaAtualizacao());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void ativar_DeveAtivarItem() {
        item.setAtivo(false);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        itemService.ativar(1L);

        assertTrue(item.getAtivo());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void desativar_DeveDesativarItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        itemService.desativar(1L);

        assertFalse(item.getAtivo());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }
}
