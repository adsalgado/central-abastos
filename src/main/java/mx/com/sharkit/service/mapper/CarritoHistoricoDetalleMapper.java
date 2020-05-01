package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.CarritoHistoricoDetalle;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;

/**
 * Mapper for the entity {@link CarritoHistoricoDetalle} and its DTO {@link CarritoHistoricoDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoProveedorMapper.class, CarritoHistoricoMapper.class})
public interface CarritoHistoricoDetalleMapper extends EntityMapper<CarritoHistoricoDetalleDTO, CarritoHistoricoDetalle> {

    CarritoHistoricoDetalleDTO toDto(CarritoHistoricoDetalle carritoHistoricoDetalle);

    CarritoHistoricoDetalle toEntity(CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO);

    default CarritoHistoricoDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarritoHistoricoDetalle carritoHistoricoDetalle = new CarritoHistoricoDetalle();
        carritoHistoricoDetalle.setId(id);
        return carritoHistoricoDetalle;
    }
}
