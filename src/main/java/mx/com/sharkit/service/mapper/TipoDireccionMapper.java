package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.TipoDireccion;
import mx.com.sharkit.service.dto.TipoDireccionDTO;

/**
 * Mapper for the entity {@link TipoDireccion} and its DTO {@link TipoDireccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDireccionMapper extends EntityMapper<TipoDireccionDTO, TipoDireccion> {

	TipoDireccionDTO toDto(TipoDireccion tipoDireccion);
	
    TipoDireccion toEntity(TipoDireccionDTO tipoDireccionDTO);

    default TipoDireccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDireccion tipoDireccion = new TipoDireccion();
        tipoDireccion.setId(id);
        return tipoDireccion;
    }
}
