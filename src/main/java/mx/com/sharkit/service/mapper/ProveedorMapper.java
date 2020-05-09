package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proveedor} and its DTO {@link ProveedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class, DireccionMapper.class})
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {

//    @Mapping(source = "usuarioAlta.id", target = "usuarioAltaId")
//    @Mapping(source = "usuarioModificacion.id", target = "usuarioModificacionId")
//    @Mapping(source = "empresa.id", target = "empresaId")
//    @Mapping(source = "direccion.id", target = "direccionId")
    ProveedorDTO toDto(Proveedor proveedor);

//    @Mapping(source = "usuarioAltaId", target = "usuarioAlta")
//    @Mapping(source = "usuarioModificacionId", target = "usuarioModificacion")
//    @Mapping(source = "empresaId", target = "empresa")
    Proveedor toEntity(ProveedorDTO proveedorDTO);

    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
