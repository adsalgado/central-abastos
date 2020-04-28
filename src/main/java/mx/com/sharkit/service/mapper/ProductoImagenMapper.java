package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.ProductoImagen;
import mx.com.sharkit.service.dto.ProductoImagenDTO;

/**
 * Mapper for the entity {@link ProductoImagen} and its DTO {@link ProductoImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductoProveedorMapper.class, AdjuntoMapper.class})
public interface ProductoImagenMapper extends EntityMapper<ProductoImagenDTO, ProductoImagen> {

    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
    @Mapping(source = "adjunto.id", target = "adjuntoId")
    @Mapping(source = "adjunto", target = "adjunto")
    ProductoImagenDTO toDto(ProductoImagen productoImagen);

    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
    @Mapping(source = "adjuntoId", target = "adjunto")
    ProductoImagen toEntity(ProductoImagenDTO productoImagenDTO);

    default ProductoImagen fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoImagen productoImagen = new ProductoImagen();
        productoImagen.setId(id);
        return productoImagen;
    }
}
