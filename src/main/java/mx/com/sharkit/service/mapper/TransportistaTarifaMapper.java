package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TransportistaTarifaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransportistaTarifa} and its DTO {@link TransportistaTarifaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransportistaMapper.class})
public interface TransportistaTarifaMapper extends EntityMapper<TransportistaTarifaDTO, TransportistaTarifa> {

    @Mapping(source = "transportista.id", target = "transportistaId")
    TransportistaTarifaDTO toDto(TransportistaTarifa transportistaTarifa);

    @Mapping(source = "transportistaId", target = "transportista")
    TransportistaTarifa toEntity(TransportistaTarifaDTO transportistaTarifaDTO);

    default TransportistaTarifa fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransportistaTarifa transportistaTarifa = new TransportistaTarifa();
        transportistaTarifa.setId(id);
        return transportistaTarifa;
    }
}
