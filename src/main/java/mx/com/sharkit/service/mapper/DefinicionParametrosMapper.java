package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.DefinicionParametros;
import mx.com.sharkit.service.dto.DefinicionParametrosDTO;

/**
 * Mapper for the entity {@link DefinicionParametros} and its DTO {@link DefinicionParametrosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DefinicionParametrosMapper extends EntityMapper<DefinicionParametrosDTO, DefinicionParametros> {

    DefinicionParametrosDTO toDto(DefinicionParametros definicionParametros);

    DefinicionParametros toEntity(DefinicionParametrosDTO definicionParametrosDTO);

    default DefinicionParametros fromId(Integer id) {
        if (id == null) {
            return null;
        }
        DefinicionParametros definicionParametros = new DefinicionParametros();
        definicionParametros.setId(id);
        return definicionParametros;
    }
}