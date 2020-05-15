package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.CarritoCompra;
import mx.com.sharkit.service.dto.CarritoCompraDTO;

/**
 * Mapper for the entity {@link CarritoCompra} and its DTO {@link CarritoCompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductoProveedorMapper.class})
public interface CarritoCompraMapper extends EntityMapper<CarritoCompraDTO, CarritoCompra> {

    CarritoCompraDTO toDto(CarritoCompra carritoCompra);

    CarritoCompra toEntity(CarritoCompraDTO carritoCompraDTO);

    default CarritoCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarritoCompra carritoCompra = new CarritoCompra();
        carritoCompra.setId(id);
        return carritoCompra;
    }
}
