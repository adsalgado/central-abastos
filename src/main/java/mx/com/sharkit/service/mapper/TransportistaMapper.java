package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.service.dto.TransportistaDTO;

/**
 * Mapper for the entity {@link Transportista} and its DTO {@link TransportistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class})
public interface TransportistaMapper extends EntityMapper<TransportistaDTO, Transportista> {

    @Mapping(source = "empresa.id", target = "empresaId")
    TransportistaDTO toDto(Transportista transportista);

    @Mapping(source = "empresaId", target = "empresa")
    Transportista toEntity(TransportistaDTO transportistaDTO);

    default Transportista fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transportista transportista = new Transportista();
        transportista.setId(id);
        return transportista;
    }
}
