package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TransportistaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transportista} and its DTO {@link TransportistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class})
public interface TransportistaMapper extends EntityMapper<TransportistaDTO, Transportista> {

    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    @Mapping(source = "usuarioModificacion.id", target = "usuarioModificacionId")
    @Mapping(source = "empresa.id", target = "empresaId")
    TransportistaDTO toDto(Transportista transportista);

    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "removePedido", ignore = true)
    @Mapping(target = "transportistaTarifas", ignore = true)
    @Mapping(target = "removeTransportistaTarifa", ignore = true)
    @Mapping(source = "empresaId", target = "empresa")
    Transportista toEntity(TransportistaDTO transportistaDTO);

    default Transportista fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transportista transportista = new Transportista();
        transportista.setId(id);
        return transportista;
    }
}
