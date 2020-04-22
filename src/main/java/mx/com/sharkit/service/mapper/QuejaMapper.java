package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.QuejaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Queja} and its DTO {@link QuejaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuejaMapper extends EntityMapper<QuejaDTO, Queja> {



    default Queja fromId(Long id) {
        if (id == null) {
            return null;
        }
        Queja queja = new Queja();
        queja.setId(id);
        return queja;
    }
}
