package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Promocion;
import mx.com.sharkit.service.dto.PromocionDTO;

/**
 * Mapper for the entity {@link Promocion} and its DTO {@link PromocionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PromocionMapper extends EntityMapper<PromocionDTO, Promocion> {

    PromocionDTO toDto(Promocion promocion);

    Promocion toEntity(PromocionDTO promocionDTO);

    default Promocion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Promocion promocion = new Promocion();
        promocion.setId(id);
        return promocion;
    }
}
