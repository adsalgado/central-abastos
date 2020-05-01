package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Pedido;
import mx.com.sharkit.service.dto.PedidoDTO;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EstatusMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    PedidoDTO toDto(Pedido pedido);

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
