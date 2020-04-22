package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.CarritoCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarritoCompra} and its DTO {@link CarritoCompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ProductoMapper.class})
public interface CarritoCompraMapper extends EntityMapper<CarritoCompraDTO, CarritoCompra> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "producto.id", target = "productoId")
    CarritoCompraDTO toDto(CarritoCompra carritoCompra);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "productoId", target = "producto")
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
