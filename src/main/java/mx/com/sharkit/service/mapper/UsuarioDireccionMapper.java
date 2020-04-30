package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.UsuarioDireccion;
import mx.com.sharkit.domain.UsuarioImagen;
import mx.com.sharkit.service.dto.UsuarioDireccionDTO;
import mx.com.sharkit.service.dto.UsuarioImagenDTO;

/**
 * Mapper for the entity {@link UsuarioImagen} and its DTO {@link UsuarioImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DireccionMapper.class})
public interface UsuarioDireccionMapper extends EntityMapper<UsuarioDireccionDTO, UsuarioDireccion> {

    UsuarioDireccionDTO toDto(UsuarioDireccion usuarioImagen);

    UsuarioDireccion toEntity(UsuarioDireccionDTO usuarioImagenDTO);

    default UsuarioDireccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioDireccion usuarioImagen = new UsuarioDireccion();
        usuarioImagen.setId(id);
        return usuarioImagen;
    }
}
