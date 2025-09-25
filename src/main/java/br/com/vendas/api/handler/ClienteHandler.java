package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.ClienteRequestDTO;
import br.com.vendas.api.controller.dto.response.ClienteResponseDTO;
import br.com.vendas.api.domain.Cliente;
import br.com.vendas.api.mapper.ClienteMapper;
import br.com.vendas.api.service.ClienteService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClienteHandler {

  private final ClienteService clienteService;

  public ClienteResponseDTO criar(ClienteRequestDTO dto) {
    Cliente clienteNovo = ClienteMapper.toEntity(dto);
    clienteNovo.setAtivo(Boolean.TRUE);
    clienteNovo.setDataCadastro(LocalDateTime.now());
    return ClienteMapper.toResponseDTO(clienteService.criar(clienteNovo));
  }

  public List<ClienteResponseDTO> buscarAtivos() {
    List<Cliente> clientes = clienteService.buscarAtivos();
    return ClienteMapper.toResponseDTOList(clientes);
  }

  public ClienteResponseDTO buscarPorId(Long id) {
    Cliente cliente = clienteService.buscarPorId(id);
    return ClienteMapper.toResponseDTO(cliente);
  }

  public ClienteResponseDTO atualizar(ClienteRequestDTO dto, Long id) {
    Cliente clienteAtualizado = ClienteMapper.toEntity(dto);
    Cliente clienteSalvo = clienteService.atualizar(id, clienteAtualizado);
    return ClienteMapper.toResponseDTO(clienteSalvo);
  }

  public void ativar(Long id) {
    clienteService.ativar(id);
  }
  public void desativar(Long id) {
    clienteService.desativar(id);
  }


}

