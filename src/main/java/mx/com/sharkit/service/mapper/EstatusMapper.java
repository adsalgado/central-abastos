package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.EstatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estatus} and its DTO {@link EstatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusMapper extends EntityMapper<EstatusDTO, Estatus> {


    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    @Mapping(target = "ofertaProveedors", ignore = true)
    @Mapping(target = "removeOfertaProveedor", ignore = true)
    @Mapping(target = "clientes", ignore = true)
    @Mapping(target = "removeCliente", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "removePedido", ignore = true)
    @Mapping(target = "pedidoDetalles", ignore = true)
    @Mapping(target = "removePedidoDetalle", ignore = true)
    Estatus toEntity(EstatusDTO estatusDTO);

    default Estatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estatus estatus = new Estatus();
        estatus.setId(id);
        return estatus;
    }
}
