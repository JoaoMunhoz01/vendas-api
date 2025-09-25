package br.com.vendas.api.controller;

import br.com.vendas.api.controller.dto.request.VendaItemRequestDTO;
import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.controller.impl.VendaController;
import br.com.vendas.api.enums.StatusVenda;
import br.com.vendas.api.handler.VendaHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaControllerTest {

    @Mock
    private VendaHandler vendaHandler;

    @InjectMocks
    private VendaController vendaController;

    private VendaRequestDTO vendaRequestDTO;
    private VendaResponseDTO vendaResponseDTO;

    @BeforeEach
    void setUp() {
        vendaRequestDTO = VendaRequestDTO.builder()
                .numeroVenda("V001")
                .clienteId(1L)
                .filialId(1L)
                .itens(List.of(
                    VendaItemRequestDTO.builder()
                        .itemId(1L)
                        .quantidade(2)
                        .build()
                ))
                .build();

        vendaResponseDTO = VendaResponseDTO.builder()
                .id(1L)
                .numeroVenda("V001")
                .dataVenda(LocalDateTime.now())
                .valorTotalVenda(BigDecimal.valueOf(200.00))
                .statusVenda(StatusVenda.NAO_CANCELADO)
                .build();
    }

    @Test
    void criar_DeveRetornarVendaCriadaComStatus201() {
        when(vendaHandler.criar(any(VendaRequestDTO.class))).thenReturn(vendaResponseDTO);

        ResponseEntity<VendaResponseDTO> response = vendaController.criar(vendaRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaResponseDTO, response.getBody());
        verify(vendaHandler, times(1)).criar(vendaRequestDTO);
    }

    @Test
    void listar_DeveRetornarListaDeVendas() {
        List<VendaResponseDTO> vendas = List.of(vendaResponseDTO);
        when(vendaHandler.listar()).thenReturn(vendas);

        ResponseEntity<List<VendaResponseDTO>> response = vendaController.listar();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(vendaHandler, times(1)).listar();
    }

    @Test
    void buscarPorId_DeveRetornarVendaQuandoExistir() {
        when(vendaHandler.buscarPorId(anyLong())).thenReturn(vendaResponseDTO);

        ResponseEntity<VendaResponseDTO> response = vendaController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaResponseDTO, response.getBody());
        verify(vendaHandler, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizar_DeveRetornarVendaAtualizada() {
        when(vendaHandler.atualizar(anyLong(), any(VendaRequestDTO.class))).thenReturn(vendaResponseDTO);

        ResponseEntity<VendaResponseDTO> response = vendaController.atualizar(1L, vendaRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaResponseDTO, response.getBody());
        verify(vendaHandler, times(1)).atualizar(1L, vendaRequestDTO);
    }

    @Test
    void deletar_DeveRetornarNoContent() {
        doNothing().when(vendaHandler).deletar(anyLong());

        ResponseEntity<Void> response = vendaController.deletar(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(vendaHandler, times(1)).deletar(1L);
    }
}
