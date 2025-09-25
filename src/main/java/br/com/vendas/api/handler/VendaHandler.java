package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.VendaItemRequestDTO;
import br.com.vendas.api.controller.dto.request.VendaRequestDTO;
import br.com.vendas.api.controller.dto.response.VendaResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.domain.Venda;
import br.com.vendas.api.enums.StatusVenda;
import br.com.vendas.api.mapper.VendaItemMapper;
import br.com.vendas.api.mapper.VendaMapper;
import br.com.vendas.api.service.ClienteService;
import br.com.vendas.api.service.FilialService;
import br.com.vendas.api.service.ItemService;
import br.com.vendas.api.service.VendaService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VendaHandler {

  private final VendaService vendaService;
  private final ClienteService clienteService;
  private final FilialService filialService;
  private final ItemService itemService;

  public VendaResponseDTO criar(VendaRequestDTO vendaRequestDTO) {
    Cliente cliente = clienteService.buscarPorId(vendaRequestDTO.clienteId());
    Filial filial = filialService.buscarPorId(vendaRequestDTO.filialId());

    Venda venda = VendaMapper.toEntity(vendaRequestDTO, cliente, filial);
    venda.setStatusVenda(StatusVenda.NAO_CANCELADO);
    venda.setDataVenda(LocalDateTime.now());

    adicionarItensVenda(venda, vendaRequestDTO.itens());
    return VendaMapper.toResponseDTO(vendaService.criar(venda));

  }

  public List<VendaResponseDTO> listar() {
    return vendaService.listar().stream()
        .map(VendaMapper::toResponseDTO)
        .toList();
  }

  public VendaResponseDTO buscarPorId(Long id) {
    return VendaMapper.toResponseDTO(vendaService.buscarPorId(id));
  }

  public VendaResponseDTO atualizar(Long id, VendaRequestDTO vendaRequestDTO) {
    Venda venda = vendaService.buscarPorId(id);
    Cliente cliente = clienteService.buscarPorId(venda.getCliente().getId());
    Filial filial = filialService.buscarPorId(venda.getFilial().getId());
    atualizarDados(venda, vendaRequestDTO, cliente, filial);
    return VendaMapper.toResponseDTO(vendaService.atualizar(venda));
  }

  public void deletar(Long id) {
    vendaService.deletar(id);
  }

  public VendaResponseDTO cancelar(Long id) {
    return VendaMapper.toResponseDTO(vendaService.cancelar(id));
  }

  private void atualizarDados(Venda venda, VendaRequestDTO vendaRequestDTO, Cliente cliente,
      Filial filial) {
    venda.setNumeroVenda(vendaRequestDTO.numeroVenda());
    venda.setCliente(cliente);
    venda.setFilial(filial);
    venda.getItens().clear();
    adicionarItensVenda(venda, vendaRequestDTO.itens());
  }


  private void adicionarItensVenda(Venda venda, List<VendaItemRequestDTO> itensDTO) {
    itensDTO.forEach(vendaItemRequestDTO -> {
      Item item = itemService.buscarPorId(vendaItemRequestDTO.itemId());
      venda.adicionarItem(VendaItemMapper.toEntity(vendaItemRequestDTO, item));
    });
  }

}

