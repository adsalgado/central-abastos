package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;

/**
 * Mapper for the entity {@link PedidoProveedor} and its DTO {@link PedidoProveedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {PedidoMapper.class, EstatusMapper.class, ProveedorMapper.class})
public interface PedidoProveedorMapper extends EntityMapper<PedidoProveedorDTO, PedidoProveedor> {

    PedidoProveedorDTO toDto(PedidoProveedor pedidoProveedor);

    PedidoProveedor toEntity(PedidoProveedorDTO pedidoProveedorDTO);

    default PedidoProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        PedidoProveedor pedidoProveedor = new PedidoProveedor();
        pedidoProveedor.setId(id);
        return pedidoProveedor;
    }
}
