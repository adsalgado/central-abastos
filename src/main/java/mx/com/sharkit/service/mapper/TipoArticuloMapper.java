package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.TipoArticulo;
import mx.com.sharkit.service.dto.TipoArticuloDTO;

/**
 * Mapper for the entity {@link TipoArticulo} and its DTO {@link TipoArticuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface TipoArticuloMapper extends EntityMapper<TipoArticuloDTO, TipoArticulo> {

    TipoArticuloDTO toDto(TipoArticulo tipoArticulo);

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
