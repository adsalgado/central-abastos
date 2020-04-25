package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.TipoArticulo;
import mx.com.sharkit.service.dto.TipoArticuloDTO;

/**
 * Mapper for the entity {@link TipoArticulo} and its DTO {@link TipoArticuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface TipoArticuloMapper extends EntityMapper<TipoArticuloDTO, TipoArticulo> {

    @Mapping(source = "categoria.id", target = "categoriaId")
    TipoArticuloDTO toDto(TipoArticulo tipoArticulo);


    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    @Mapping(source = "categoriaId", target = "categoria")
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
