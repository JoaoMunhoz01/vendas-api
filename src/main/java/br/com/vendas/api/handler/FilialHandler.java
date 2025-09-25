package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.FilialRequestDTO;
import br.com.vendas.api.controller.dto.response.FilialResponseDTO;
import br.com.vendas.api.domain.Filial;
import br.com.vendas.api.mapper.FilialMapper;
import br.com.vendas.api.service.FilialService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FilialHandler {

  private final FilialService filialService;

  public FilialResponseDTO criar(FilialRequestDTO filialRequestDTO) {
    Filial filial = FilialMapper.toEntity(filialRequestDTO);
    Filial filialSalva = filialService.criar(filial);
    return FilialMapper.toResponseDTO(filialSalva);
  }

  public List<FilialResponseDTO> buscarAtivas() {
    List<Filial> filiais = filialService.buscarAtivas();
    return FilialMapper.toResponseDTOList(filiais);
  }

  public FilialResponseDTO buscarPorId(Long id) {
    Filial filial = filialService.buscarPorId(id);
    return FilialMapper.toResponseDTO(filial);
  }

  public FilialResponseDTO atualizar(FilialRequestDTO filialRequestDTO, Long id) {
    Filial filialAtualizada = FilialMapper.toEntity(filialRequestDTO);
    Filial filialSalva = filialService.atualizar(id, filialAtualizada);
    return FilialMapper.toResponseDTO(filialSalva);
  }

  public void ativar(Long id) {
    filialService.ativar(id);
  }

  public void desativar(Long id) {
    filialService.desativar(id);
  }


}

