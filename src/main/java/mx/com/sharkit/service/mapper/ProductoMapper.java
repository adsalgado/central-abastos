package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.service.dto.ProductoDTO;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdjuntoMapper.class, UserMapper.class, TipoArticuloMapper.class, CategoriaMapper.class, SeccionMapper.class, EstatusMapper.class, UnidadMedidaMapper.class, EmpresaMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "adjunto.id", target = "adjuntoId")
    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    @Mapping(source = "usuarioModificacion.id", target = "usuarioModificacionId")
    @Mapping(source = "tipoArticulo.id", target = "tipoArticuloId")
    @Mapping(source = "tipoArticulo", target = "tipoArticulo")
    @Mapping(source = "estatus.id", target = "estatusId")
    @Mapping(source = "estatus", target = "estatus")
    @Mapping(source = "unidadMedida.id", target = "unidadMedidaId")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "adjuntoId", target = "adjunto")
    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
    @Mapping(target = "ofertaProveedors", ignore = true)
    @Mapping(target = "removeOfertaProveedor", ignore = true)
    @Mapping(target = "pedidoDetalles", ignore = true)
    @Mapping(target = "removePedidoDetalle", ignore = true)
    @Mapping(target = "inventarios", ignore = true)
    @Mapping(target = "removeInventario", ignore = true)
    @Mapping(source = "tipoArticuloId", target = "tipoArticulo")
    @Mapping(source = "estatusId", target = "estatus")
    @Mapping(source = "unidadMedidaId", target = "unidadMedida")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
