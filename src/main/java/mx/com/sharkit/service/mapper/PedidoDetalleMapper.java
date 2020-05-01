package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.PedidoDetalle;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;

/**
 * Mapper for the entity {@link PedidoDetalle} and its DTO {@link PedidoDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {PedidoProveedorMapper.class, ProductoProveedorMapper.class, EstatusMapper.class})
public interface PedidoDetalleMapper extends EntityMapper<PedidoDetalleDTO, PedidoDetalle> {

    PedidoDetalleDTO toDto(PedidoDetalle pedidoDetalle);

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
