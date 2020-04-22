package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EstatusMapper.class, EmpresaMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    @Mapping(source = "usuarioModificacion.id", target = "usuarioModificacionId")
    @Mapping(source = "estatus.id", target = "estatusId")
    @Mapping(source = "empresa.id", target = "empresaId")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
    @Mapping(target = "tarjetas", ignore = true)
    @Mapping(target = "removeTarjeta", ignore = true)
    @Mapping(target = "direccions", ignore = true)
    @Mapping(target = "removeDireccion", ignore = true)
    @Mapping(target = "carritoCompras", ignore = true)
    @Mapping(target = "removeCarritoCompra", ignore = true)
    @Mapping(target = "carritoHistoricos", ignore = true)
    @Mapping(target = "removeCarritoHistorico", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "removePedido", ignore = true)
    @Mapping(source = "estatusId", target = "estatus")
    @Mapping(source = "empresaId", target = "empresa")
    Cliente toEntity(ClienteDTO clienteDTO);

    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
