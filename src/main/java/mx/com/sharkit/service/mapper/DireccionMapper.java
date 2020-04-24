package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Direccion;
import mx.com.sharkit.service.dto.DireccionDTO;

/**
 * Mapper for the entity {@link Direccion} and its DTO {@link DireccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface DireccionMapper extends EntityMapper<DireccionDTO, Direccion> {

    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    DireccionDTO toDto(Direccion direccion);

    @Mapping(source = "usuarioAltaId", target = "usuarioAltaId")
    Direccion toEntity(DireccionDTO direccionDTO);

    default Direccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Direccion direccion = new Direccion();
        direccion.setId(id);
        return direccion;
    }
}
