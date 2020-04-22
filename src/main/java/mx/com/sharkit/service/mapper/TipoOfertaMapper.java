package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TipoOfertaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoOferta} and its DTO {@link TipoOfertaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoOfertaMapper extends EntityMapper<TipoOfertaDTO, TipoOferta> {


    @Mapping(target = "ofertaProveedors", ignore = true)
    @Mapping(target = "removeOfertaProveedor", ignore = true)
    TipoOferta toEntity(TipoOfertaDTO tipoOfertaDTO);

    default TipoOferta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoOferta tipoOferta = new TipoOferta();
        tipoOferta.setId(id);
        return tipoOferta;
    }
}
