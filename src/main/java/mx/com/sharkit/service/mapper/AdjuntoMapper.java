package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.AdjuntoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adjunto} and its DTO {@link AdjuntoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdjuntoMapper extends EntityMapper<AdjuntoDTO, Adjunto> {

    @Mapping(target = "productoImagens", ignore = true)
    @Mapping(target = "removeProductoImagen", ignore = true)
    @Mapping(target = "usuarioImagens", ignore = true)
    @Mapping(target = "removeUsuarioImagen", ignore = true)
    @Mapping(target = "parametrosAplicacions", ignore = true)
    @Mapping(target = "removeParametrosAplicacion", ignore = true)
    Adjunto toEntity(AdjuntoDTO adjuntoDTO);

    default Adjunto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adjunto adjunto = new Adjunto();
        adjunto.setId(id);
        return adjunto;
    }
}
