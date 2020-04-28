package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.PedidoDetalle;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;

/**
 * Mapper for the entity {@link PedidoDetalle} and its DTO {@link PedidoDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {PedidoMapper.class, ProductoProveedorMapper.class, EstatusMapper.class})
public interface PedidoDetalleMapper extends EntityMapper<PedidoDetalleDTO, PedidoDetalle> {

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "estatus.id", target = "estatusId")
    PedidoDetalleDTO toDto(PedidoDetalle pedidoDetalle);

    @Mapping(source = "pedidoId", target = "pedido")
    @Mapping(source = "estatusId", target = "estatus")
    PedidoDetalle toEntity(PedidoDetalleDTO pedidoDetalleDTO);

    default PedidoDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setId(id);
        return pedidoDetalle;
    }
}
