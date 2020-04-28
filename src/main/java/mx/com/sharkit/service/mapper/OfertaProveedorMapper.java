package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.OfertaProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfertaProveedor} and its DTO {@link OfertaProveedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ProductoProveedorMapper.class, EstatusMapper.class, TipoOfertaMapper.class})
public interface OfertaProveedorMapper extends EntityMapper<OfertaProveedorDTO, OfertaProveedor> {

    @Mapping(source = "estatus.id", target = "estatusId")
    @Mapping(source = "tipoOferta.id", target = "tipoOfertaId")
    OfertaProveedorDTO toDto(OfertaProveedor ofertaProveedor);

    @Mapping(source = "estatusId", target = "estatus")
    @Mapping(source = "tipoOfertaId", target = "tipoOferta")
    OfertaProveedor toEntity(OfertaProveedorDTO ofertaProveedorDTO);

    default OfertaProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfertaProveedor ofertaProveedor = new OfertaProveedor();
        ofertaProveedor.setId(id);
        return ofertaProveedor;
    }
}
