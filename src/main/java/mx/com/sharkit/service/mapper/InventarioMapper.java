package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Inventario;
import mx.com.sharkit.service.dto.InventarioDTO;

/**
 * Mapper for the entity {@link Inventario} and its DTO {@link InventarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoProveedorMapper.class})
public interface InventarioMapper extends EntityMapper<InventarioDTO, Inventario> {

    InventarioDTO toDto(Inventario inventario);

    Inventario toEntity(InventarioDTO inventarioDTO);

    default Inventario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inventario inventario = new Inventario();
        inventario.setId(id);
        return inventario;
    }
}
