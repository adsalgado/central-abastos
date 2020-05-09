package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.UsuarioDocumento;
import mx.com.sharkit.service.dto.UsuarioDocumentoDTO;

/**
 * Mapper for the entity {@link UsuarioDocumento} and its DTO {@link UsuarioDocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DocumentoMapper.class})
public interface UsuarioDocumentoMapper extends EntityMapper<UsuarioDocumentoDTO, UsuarioDocumento> {

    UsuarioDocumentoDTO toDto(UsuarioDocumento usuarioDocumento);

    UsuarioDocumento toEntity(UsuarioDocumentoDTO usuarioDocumentoDTO);

    default UsuarioDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioDocumento usuarioDocumento = new UsuarioDocumento();
        usuarioDocumento.setId(id);
        return usuarioDocumento;
    }
}
