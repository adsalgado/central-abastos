package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.PagosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagos} and its DTO {@link PagosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PagosMapper extends EntityMapper<PagosDTO, Pagos> {



    default Pagos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagos pagos = new Pagos();
        pagos.setId(id);
        return pagos;
    }
}
