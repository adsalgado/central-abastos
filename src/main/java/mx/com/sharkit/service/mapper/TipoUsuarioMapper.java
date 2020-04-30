package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.service.dto.TipoUsuarioDTO;

/**
 * Mapper for the entity {@link TipoUsuario} and its DTO {@link TipoUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoUsuarioMapper extends EntityMapper<TipoUsuarioDTO, TipoUsuario> {

	TipoUsuarioDTO toDto(TipoUsuario tipoUsuario);
	
    TipoUsuario toEntity(TipoUsuarioDTO tipoUsuarioDTO);

    default TipoUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(id);
        return tipoUsuario;
    }
}
