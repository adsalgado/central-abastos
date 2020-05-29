package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.service.dto.ProductoDTO;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TipoArticuloMapper.class, CategoriaMapper.class, SeccionMapper.class, EstatusMapper.class, UnidadMedidaMapper.class, EmpresaMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    ProductoDTO toDto(Producto producto);

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
