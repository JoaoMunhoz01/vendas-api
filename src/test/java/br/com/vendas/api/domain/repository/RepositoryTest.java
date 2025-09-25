package br.com.vendas.api.domain.repository;

import br.com.vendas.api.domain.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private FilialRepository filialRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Deve encontrar clientes ativos")
    void deveEncontrarClientesAtivos() {
        Cliente cliente1 = Cliente.builder().id(1L).nome("Cliente 1").ativo(true).build();
        Cliente cliente2 = Cliente.builder().id(2L).nome("Cliente 2").ativo(true).build();
        
        when(clienteRepository.findByAtivoTrue()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientesAtivos = clienteRepository.findByAtivoTrue();

        assertAll("Clientes ativos",
            () -> assertEquals(2, clientesAtivos.size()),
            () -> assertTrue(clientesAtivos.stream().allMatch(Cliente::getAtivo))
        );

        verify(clienteRepository, times(1)).findByAtivoTrue();
    }

    @Test
    @DisplayName("Deve verificar existência de número de venda")
    void deveVerificarExistenciaNumeroVenda() {
        when(vendaRepository.existsByNumeroVenda("V001")).thenReturn(true);
        when(vendaRepository.existsByNumeroVenda("V999")).thenReturn(false);

        assertTrue(vendaRepository.existsByNumeroVenda("V001"));
        assertFalse(vendaRepository.existsByNumeroVenda("V999"));

        verify(vendaRepository, times(1)).existsByNumeroVenda("V001");
        verify(vendaRepository, times(1)).existsByNumeroVenda("V999");
    }

    @Test
    @DisplayName("Deve salvar e recuperar entidades")
    void deveSalvarERecuperarEntidades() {
        Cliente cliente = Cliente.builder().nome("Novo Cliente").build();
        Cliente clienteSalvo = Cliente.builder().id(1L).nome("Novo Cliente").build();

        when(clienteRepository.save(cliente)).thenReturn(clienteSalvo);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteSalvo));

        Cliente salvo = clienteRepository.save(cliente);
        Optional<Cliente> encontrado = clienteRepository.findById(1L);

        assertAll("Cliente salvo e recuperado",
            () -> assertEquals(1L, salvo.getId()),
            () -> assertTrue(encontrado.isPresent()),
            () -> assertEquals("Novo Cliente", encontrado.get().getNome())
        );

        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteRepository, times(1)).findById(1L);
    }
}
