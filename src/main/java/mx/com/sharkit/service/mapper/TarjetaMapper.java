package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Tarjeta;
import mx.com.sharkit.service.dto.TarjetaDTO;

/**
 * Mapper for the entity {@link Tarjeta} and its DTO {@link TarjetaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TarjetaMapper extends EntityMapper<TarjetaDTO, Tarjeta> {

    TarjetaDTO toDto(Tarjeta tarjeta);

    Tarjeta toEntity(TarjetaDTO tarjetaDTO);

    default Tarjeta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(id);
        return tarjeta;
    }
}
