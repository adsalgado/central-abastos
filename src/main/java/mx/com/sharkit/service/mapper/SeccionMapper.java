package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Seccion;
import mx.com.sharkit.service.dto.SeccionDTO;

/**
 * Mapper for the entity {@link Seccion} and its DTO {@link SeccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface SeccionMapper extends EntityMapper<SeccionDTO, Seccion> {

    SeccionDTO toDto(Seccion seccion);

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
