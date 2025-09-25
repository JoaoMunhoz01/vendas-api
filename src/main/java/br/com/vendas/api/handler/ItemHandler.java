package br.com.vendas.api.handler;

import br.com.vendas.api.controller.dto.request.ItemRequestDTO;
import br.com.vendas.api.controller.dto.response.ItemResponseDTO;
import br.com.vendas.api.domain.Item;
import br.com.vendas.api.mapper.ItemMapper;
import br.com.vendas.api.service.ItemService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ItemHandler {

  private final ItemService itemService;

  public ItemResponseDTO criar(ItemRequestDTO itemRequestDTO) {
    Item item = ItemMapper.toEntity(itemRequestDTO);

    item.setAtivo(Boolean.TRUE);
    item.setDataCadastro(LocalDateTime.now());
    item.setDataUltimaAtualizacao(LocalDateTime.now());

    Item itemSalvo = itemService.criar(item);
    return ItemMapper.toResponseDTO(itemSalvo);
  }

  public List<ItemResponseDTO> listar() {
    List<Item> itens = itemService.buscarAtivos();
    return ItemMapper.toResponseDTOList(itens);
  }

  public ItemResponseDTO buscarPorId(Long id) {
    Item item = itemService.buscarPorId(id);
    return ItemMapper.toResponseDTO(item);
  }

  public ItemResponseDTO atualizar(ItemRequestDTO itemRequestDTO, Long id) {
    Item itemAtualizado = ItemMapper.toEntity(itemRequestDTO);
    Item itemSalvo = itemService.atualizar(id, itemAtualizado);
    return ItemMapper.toResponseDTO(itemSalvo);
  }

  public void ativar(Long id) {
    itemService.ativar(id);
  }

  public void desativar(Long id) {
    itemService.desativar(id);
  }




}

