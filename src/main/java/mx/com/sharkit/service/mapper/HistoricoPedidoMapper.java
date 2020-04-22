package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.HistoricoPedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistoricoPedido} and its DTO {@link HistoricoPedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PedidoMapper.class})
public interface HistoricoPedidoMapper extends EntityMapper<HistoricoPedidoDTO, HistoricoPedido> {

    @Mapping(source = "pedido.id", target = "pedidoId")
    HistoricoPedidoDTO toDto(HistoricoPedido historicoPedido);

    @Mapping(source = "pedidoId", target = "pedido")
    HistoricoPedido toEntity(HistoricoPedidoDTO historicoPedidoDTO);

    default HistoricoPedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoricoPedido historicoPedido = new HistoricoPedido();
        historicoPedido.setId(id);
        return historicoPedido;
    }
}
