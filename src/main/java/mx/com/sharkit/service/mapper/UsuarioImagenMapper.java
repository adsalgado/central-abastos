package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.UsuarioImagenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioImagen} and its DTO {@link UsuarioImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdjuntoMapper.class})
public interface UsuarioImagenMapper extends EntityMapper<UsuarioImagenDTO, UsuarioImagen> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "adjunto.id", target = "adjuntoId")
    UsuarioImagenDTO toDto(UsuarioImagen usuarioImagen);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "adjuntoId", target = "adjunto")
    UsuarioImagen toEntity(UsuarioImagenDTO usuarioImagenDTO);

    default UsuarioImagen fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioImagen usuarioImagen = new UsuarioImagen();
        usuarioImagen.setId(id);
        return usuarioImagen;
    }
}
