package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.CarritoHistorico;
import mx.com.sharkit.service.dto.CarritoHistoricoDTO;

/**
 * Mapper for the entity {@link CarritoHistorico} and its DTO {@link CarritoHistoricoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CarritoHistoricoMapper extends EntityMapper<CarritoHistoricoDTO, CarritoHistorico> {

    CarritoHistoricoDTO toDto(CarritoHistorico carritoHistorico);

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
