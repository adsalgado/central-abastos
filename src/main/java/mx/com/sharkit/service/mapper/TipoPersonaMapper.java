package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.TipoPersona;
import mx.com.sharkit.service.dto.TipoPersonaDTO;

/**
 * Mapper for the entity {@link TipoPersona} and its DTO {@link TipoPersonaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoPersonaMapper extends EntityMapper<TipoPersonaDTO, TipoPersona> {

	TipoPersonaDTO toDto(TipoPersona tipoPersona);
	
    TipoPersona toEntity(TipoPersonaDTO tipoPersonaDTO);

    default TipoPersona fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoPersona tipoPersona = new TipoPersona();
        tipoPersona.setId(id);
        return tipoPersona;
    }
}
