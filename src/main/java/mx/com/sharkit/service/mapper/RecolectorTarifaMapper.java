package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.RecolectorTarifaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecolectorTarifa} and its DTO {@link RecolectorTarifaDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecolectorMapper.class})
public interface RecolectorTarifaMapper extends EntityMapper<RecolectorTarifaDTO, RecolectorTarifa> {

    @Mapping(source = "recolector.id", target = "recolectorId")
    RecolectorTarifaDTO toDto(RecolectorTarifa recolectorTarifa);

    @Mapping(source = "recolectorId", target = "recolector")
    RecolectorTarifa toEntity(RecolectorTarifaDTO recolectorTarifaDTO);

    default RecolectorTarifa fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecolectorTarifa recolectorTarifa = new RecolectorTarifa();
        recolectorTarifa.setId(id);
        return recolectorTarifa;
    }
}
