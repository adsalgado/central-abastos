package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.RecolectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recolector} and its DTO {@link RecolectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class})
public interface RecolectorMapper extends EntityMapper<RecolectorDTO, Recolector> {

    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    @Mapping(source = "usuarioModificacion.id", target = "usuarioModificacionId")
    @Mapping(source = "empresa.id", target = "empresaId")
    RecolectorDTO toDto(Recolector recolector);

    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
    @Mapping(target = "recolectorTarifas", ignore = true)
    @Mapping(target = "removeRecolectorTarifa", ignore = true)
    @Mapping(source = "empresaId", target = "empresa")
    Recolector toEntity(RecolectorDTO recolectorDTO);

    default Recolector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recolector recolector = new Recolector();
        recolector.setId(id);
        return recolector;
    }
}
