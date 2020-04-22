package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.UnidadMedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadMedida} and its DTO {@link UnidadMedidaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadMedidaMapper extends EntityMapper<UnidadMedidaDTO, UnidadMedida> {


    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    UnidadMedida toEntity(UnidadMedidaDTO unidadMedidaDTO);

    default UnidadMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadMedida unidadMedida = new UnidadMedida();
        unidadMedida.setId(id);
        return unidadMedida;
    }
}
