package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.CarritoHistoricoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarritoHistorico} and its DTO {@link CarritoHistoricoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CarritoHistoricoMapper extends EntityMapper<CarritoHistoricoDTO, CarritoHistorico> {

    @Mapping(source = "cliente.id", target = "clienteId")
    CarritoHistoricoDTO toDto(CarritoHistorico carritoHistorico);

    @Mapping(target = "carritoHistoricoDetalles", ignore = true)
    @Mapping(target = "removeCarritoHistoricoDetalle", ignore = true)
    @Mapping(source = "clienteId", target = "cliente")
    CarritoHistorico toEntity(CarritoHistoricoDTO carritoHistoricoDTO);

    default CarritoHistorico fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarritoHistorico carritoHistorico = new CarritoHistorico();
        carritoHistorico.setId(id);
        return carritoHistorico;
    }
}
