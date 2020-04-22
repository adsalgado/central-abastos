package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarritoHistoricoDetalle} and its DTO {@link CarritoHistoricoDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, CarritoHistoricoMapper.class})
public interface CarritoHistoricoDetalleMapper extends EntityMapper<CarritoHistoricoDetalleDTO, CarritoHistoricoDetalle> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "carritoHistorico.id", target = "carritoHistoricoId")
    CarritoHistoricoDetalleDTO toDto(CarritoHistoricoDetalle carritoHistoricoDetalle);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "carritoHistoricoId", target = "carritoHistorico")
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
