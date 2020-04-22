package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.ParametrosAplicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParametrosAplicacion} and its DTO {@link ParametrosAplicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdjuntoMapper.class})
public interface ParametrosAplicacionMapper extends EntityMapper<ParametrosAplicacionDTO, ParametrosAplicacion> {

    @Mapping(source = "adjunto.id", target = "adjuntoId")
    ParametrosAplicacionDTO toDto(ParametrosAplicacion parametrosAplicacion);

    @Mapping(source = "adjuntoId", target = "adjunto")
    ParametrosAplicacion toEntity(ParametrosAplicacionDTO parametrosAplicacionDTO);

    default ParametrosAplicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParametrosAplicacion parametrosAplicacion = new ParametrosAplicacion();
        parametrosAplicacion.setId(id);
        return parametrosAplicacion;
    }
}
