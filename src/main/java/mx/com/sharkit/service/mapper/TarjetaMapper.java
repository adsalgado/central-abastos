package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TarjetaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarjeta} and its DTO {@link TarjetaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface TarjetaMapper extends EntityMapper<TarjetaDTO, Tarjeta> {

    @Mapping(source = "cliente.id", target = "clienteId")
    TarjetaDTO toDto(Tarjeta tarjeta);

    @Mapping(source = "clienteId", target = "cliente")
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
