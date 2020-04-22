package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.InventarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventario} and its DTO {@link InventarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ProductoMapper.class})
public interface InventarioMapper extends EntityMapper<InventarioDTO, Inventario> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "producto.id", target = "productoId")
    InventarioDTO toDto(Inventario inventario);

    @Mapping(target = "inventarioHistoricos", ignore = true)
    @Mapping(target = "removeInventarioHistorico", ignore = true)
    @Mapping(source = "proveedorId", target = "proveedor")
    @Mapping(source = "productoId", target = "producto")
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
