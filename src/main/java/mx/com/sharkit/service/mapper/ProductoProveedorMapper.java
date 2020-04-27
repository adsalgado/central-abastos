package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;

/**
 * Mapper for the entity {@link ProductoImagen} and its DTO {@link ProductoImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstatusMapper.class, ProductoMapper.class, ProveedorMapper.class})
public interface ProductoProveedorMapper extends EntityMapper<ProductoProveedorDTO, ProductoProveedor> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "estatus.id", target = "estatusId")
    ProductoProveedorDTO toDto(ProductoProveedor productoProveedor);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "proveedorId", target = "proveedor")
    @Mapping(source = "estatusId", target = "estatus")
    ProductoProveedor toEntity(ProductoProveedorDTO productoProveedorDTO);

    default ProductoProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoProveedor productoProveedor = new ProductoProveedor();
        productoProveedor.setId(id);
        return productoProveedor;
    }
}
