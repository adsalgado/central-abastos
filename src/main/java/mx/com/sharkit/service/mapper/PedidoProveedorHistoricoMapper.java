package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.PedidoProveedorHistorico;
import mx.com.sharkit.domain.ProductoImagen;
import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;

/**
 * Mapper for the entity {@link ProductoImagen} and its DTO {@link PedidoProveedorHistoricoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstatusMapper.class})
public interface PedidoProveedorHistoricoMapper extends EntityMapper<PedidoProveedorHistoricoDTO, PedidoProveedorHistorico> {

	PedidoProveedorHistoricoDTO toDto(PedidoProveedorHistorico pedidoProveedorHistorico);

	PedidoProveedorHistorico toEntity(PedidoProveedorHistoricoDTO pedidoProveedorHistoricoDTO);

    default PedidoProveedorHistorico fromId(Long id) {
        if (id == null) {
            return null;
        }
        PedidoProveedorHistorico pedidoProveedorHistorico = new PedidoProveedorHistorico();
        pedidoProveedorHistorico.setId(id);
        return pedidoProveedorHistorico;
    }
}
