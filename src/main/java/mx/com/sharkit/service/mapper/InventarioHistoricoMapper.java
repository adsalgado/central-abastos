package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.InventarioHistoricoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventarioHistorico} and its DTO {@link InventarioHistoricoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, InventarioMapper.class})
public interface InventarioHistoricoMapper extends EntityMapper<InventarioHistoricoDTO, InventarioHistorico> {

    @Mapping(source = "usuarioMovimiento.id", target = "usuarioMovimientoId")
    @Mapping(source = "inventario.id", target = "inventarioId")
    InventarioHistoricoDTO toDto(InventarioHistorico inventarioHistorico);

    @Mapping(source = "usuarioMovimientoId", target = "usuarioMovimiento")
    @Mapping(source = "inventarioId", target = "inventario")
    InventarioHistorico toEntity(InventarioHistoricoDTO inventarioHistoricoDTO);

    default InventarioHistorico fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventarioHistorico inventarioHistorico = new InventarioHistorico();
        inventarioHistorico.setId(id);
        return inventarioHistorico;
    }
}
