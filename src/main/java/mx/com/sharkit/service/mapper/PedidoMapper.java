package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, EstatusMapper.class, TransportistaMapper.class, RecolectorMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "estatus.id", target = "estatusId")
    @Mapping(source = "transportista.id", target = "transportistaId")
    @Mapping(source = "recolector.id", target = "recolectorId")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(target = "pedidoDetalles", ignore = true)
    @Mapping(target = "removePedidoDetalle", ignore = true)
    @Mapping(target = "historicoPedidos", ignore = true)
    @Mapping(target = "removeHistoricoPedido", ignore = true)
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "estatusId", target = "estatus")
    @Mapping(source = "transportistaId", target = "transportista")
    @Mapping(source = "recolectorId", target = "recolector")
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }
}
