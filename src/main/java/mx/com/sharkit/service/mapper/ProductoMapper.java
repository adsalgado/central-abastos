package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.ProductoDTO;

import org.mapstruct.*;

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
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "seccion.id", target = "seccionId")
    @Mapping(source = "seccion", target = "seccion")
    @Mapping(source = "estatus.id", target = "estatusId")
    @Mapping(source = "estatus", target = "estatus")
    @Mapping(source = "unidadMedida.id", target = "unidadMedidaId")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa", target = "empresa")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "adjuntoId", target = "adjunto")
    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
    @Mapping(target = "ofertaProveedors", ignore = true)
    @Mapping(target = "removeOfertaProveedor", ignore = true)
    @Mapping(target = "carritoCompras", ignore = true)
    @Mapping(target = "removeCarritoCompra", ignore = true)
    @Mapping(target = "carritoCompraDetalles", ignore = true)
    @Mapping(target = "removeCarritoCompraDetalle", ignore = true)
    @Mapping(target = "pedidoDetalles", ignore = true)
    @Mapping(target = "removePedidoDetalle", ignore = true)
    @Mapping(target = "inventarios", ignore = true)
    @Mapping(target = "removeInventario", ignore = true)
    @Mapping(target = "productoImagens", ignore = true)
    @Mapping(target = "removeProductoImagen", ignore = true)
    @Mapping(source = "tipoArticuloId", target = "tipoArticulo")
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "seccionId", target = "seccion")
    @Mapping(source = "estatusId", target = "estatus")
    @Mapping(source = "unidadMedidaId", target = "unidadMedida")
    @Mapping(source = "empresaId", target = "empresa")
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
