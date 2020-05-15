package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Cliente;
import mx.com.sharkit.service.dto.ClienteDTO;

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
