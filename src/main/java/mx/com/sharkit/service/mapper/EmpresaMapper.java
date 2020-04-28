package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Empresa;
import mx.com.sharkit.service.dto.EmpresaDTO;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {


    @Mapping(target = "clientes", ignore = true)
    @Mapping(target = "removeCliente", ignore = true)
    @Mapping(target = "proveedors", ignore = true)
    @Mapping(target = "removeProveedor", ignore = true)
    @Mapping(target = "recolectors", ignore = true)
    @Mapping(target = "removeRecolector", ignore = true)
    @Mapping(target = "transportistas", ignore = true)
    @Mapping(target = "removeTransportista", ignore = true)
    @Mapping(target = "seccions", ignore = true)
    @Mapping(target = "removeSeccion", ignore = true)
    Empresa toEntity(EmpresaDTO empresaDTO);

    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
