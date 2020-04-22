package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TipoArticuloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoArticulo} and its DTO {@link TipoArticuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoArticuloMapper extends EntityMapper<TipoArticuloDTO, TipoArticulo> {


    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    TipoArticulo toEntity(TipoArticuloDTO tipoArticuloDTO);

    default TipoArticulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoArticulo tipoArticulo = new TipoArticulo();
        tipoArticulo.setId(id);
        return tipoArticulo;
    }
}
