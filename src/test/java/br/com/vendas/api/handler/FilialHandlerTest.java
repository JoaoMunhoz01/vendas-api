// FilialHandlerTest.java
package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.service.FilialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilialHandlerTest {

    @Mock
    private FilialService filialService;

    @InjectMocks
    private FilialHandler filialHandler;

    @Test
    void criar_DeveRetornarFilialResponseDTO_QuandoRequestValido() {
        // Arrange
        FilialRequestDTO requestDTO = FilialRequestDTO.builder()
                .nome("Filial Centro")
                .build();

        Filial filialSalva = Filial.builder()
                .id(1L)
                .nome("Filial Centro")
                .ativa(true)
                .build();

        when(filialService.criar(any(Filial.class))).thenReturn(filialSalva);

        // Act
        FilialResponseDTO response = filialHandler.criar(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(filialSalva.getId(), response.id());
        assertEquals(filialSalva.getNome(), response.nome());
        verify(filialService, times(1)).criar(any(Filial.class));
    }

    @Test
    void buscarAtivas_DeveRetornarListaFiliaisAtivas() {
        // Arrange
        Filial filial1 = Filial.builder().id(1L).nome("Filial 1").ativa(true).build();
        Filial filial2 = Filial.builder().id(2L).nome("Filial 2").ativa(true).build();
        List<Filial> filiais = List.of(filial1, filial2);

        when(filialService.buscarAtivas()).thenReturn(filiais);

        // Act
        List<FilialResponseDTO> response = filialHandler.buscarAtivas();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(filialService, times(1)).buscarAtivas();
    }

    @Test
    void buscarPorId_DeveRetornarFilial_QuandoIdExistente() {
        // Arrange
        Long id = 1L;
        Filial filial = Filial.builder()
                .id(id)
                .nome("Filial Teste")
                .ativa(true)
                .build();

        when(filialService.buscarPorId(id)).thenReturn(filial);

        // Act
        FilialResponseDTO response = filialHandler.buscarPorId(id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(filial.getNome(), response.nome());
        verify(filialService, times(1)).buscarPorId(id);
    }

    @Test
    void atualizar_DeveRetornarFilialAtualizada_QuandoDadosValidos() {
        // Arrange
        Long id = 1L;
        FilialRequestDTO requestDTO = FilialRequestDTO.builder()
                .nome("Nova Filial")
                .build();

        Filial filialAtualizada = Filial.builder()
                .id(id)
                .nome("Nova Filial")
                .ativa(true)
                .build();

        when(filialService.atualizar(eq(id), any(Filial.class))).thenReturn(filialAtualizada);

        // Act
        FilialResponseDTO response = filialHandler.atualizar(requestDTO, id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(requestDTO.nome(), response.nome());
        verify(filialService, times(1)).atualizar(eq(id), any(Filial.class));
    }

    @Test
    void ativar_DeveChamarServico_QuandoIdValido() {
        // Arrange
        Long id = 1L;
        doNothing().when(filialService).ativar(id);

        // Act
        filialHandler.ativar(id);

        // Assert
        verify(filialService, times(1)).ativar(id);
    }

    @Test
    void desativar_DeveChamarServico_QuandoIdValido() {
        // Arrange
        Long id = 1L;
        doNothing().when(filialService).desativar(id);

        // Act
        filialHandler.desativar(id);

        // Assert
        verify(filialService, times(1)).desativar(id);
    }
}
