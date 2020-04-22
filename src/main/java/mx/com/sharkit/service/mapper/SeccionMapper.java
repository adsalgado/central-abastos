package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.SeccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seccion} and its DTO {@link SeccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface SeccionMapper extends EntityMapper<SeccionDTO, Seccion> {

    @Mapping(source = "empresa.id", target = "empresaId")
    SeccionDTO toDto(Seccion seccion);

    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    @Mapping(source = "empresaId", target = "empresa")
    Seccion toEntity(SeccionDTO seccionDTO);

    default Seccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seccion seccion = new Seccion();
        seccion.setId(id);
        return seccion;
    }
}
