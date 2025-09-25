package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.controller.impl.FilialController;
import br.com.vendas.api.handler.FilialHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilialControllerTest {

    @Mock
    private FilialHandler filialHandler;

    @InjectMocks
    private FilialController filialController;

    private FilialRequestDTO filialRequestDTO;
    private FilialResponseDTO filialResponseDTO;

    @BeforeEach
    void setUp() {
        filialRequestDTO = FilialRequestDTO.builder()
                .nome("Filial Centro")
                .build();

        filialResponseDTO = FilialResponseDTO.builder()
                .id(1L)
                .nome("Filial Centro")
                .build();
    }

    @Test
    void criar_DeveRetornarFilialCriadaComStatus201() {
        when(filialHandler.criar(any(FilialRequestDTO.class))).thenReturn(filialResponseDTO);

        ResponseEntity<FilialResponseDTO> response = filialController.criar(filialRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(filialResponseDTO, response.getBody());
        verify(filialHandler, times(1)).criar(filialRequestDTO);
    }

    @Test
    void buscarAtivas_DeveRetornarListaDeFiliaisAtivas() {
        List<FilialResponseDTO> filiais = List.of(filialResponseDTO);
        when(filialHandler.buscarAtivas()).thenReturn(filiais);

        ResponseEntity<List<FilialResponseDTO>> response = filialController.buscarAtivas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(filialHandler, times(1)).buscarAtivas();
    }

    @Test
    void buscarPorId_DeveRetornarFilialQuandoExistir() {
        when(filialHandler.buscarPorId(anyLong())).thenReturn(filialResponseDTO);

        ResponseEntity<FilialResponseDTO> response = filialController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(filialResponseDTO, response.getBody());
        verify(filialHandler, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizar_DeveRetornarFilialAtualizada() {
        when(filialHandler.atualizar(any(FilialRequestDTO.class), anyLong())).thenReturn(filialResponseDTO);

        ResponseEntity<FilialResponseDTO> response = filialController.atualizar(1L, filialRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(filialResponseDTO, response.getBody());
        verify(filialHandler, times(1)).atualizar(filialRequestDTO, 1L);
    }

    @Test
    void ativar_DeveRetornarNoContent() {
        doNothing().when(filialHandler).ativar(anyLong());

        ResponseEntity<Void> response = filialController.ativar(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(filialHandler, times(1)).ativar(1L);
    }

    @Test
    void desativar_DeveRetornarNoContent() {
        doNothing().when(filialHandler).desativar(anyLong());

        ResponseEntity<Void> response = filialController.desativar(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(filialHandler, times(1)).desativar(1L);
    }
}
