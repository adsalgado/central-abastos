package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mx.com.sharkit.domain.Categoria;
import mx.com.sharkit.service.dto.CategoriaDTO;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {SeccionMapper.class})
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {

    @Mapping(source = "seccion.id", target = "seccionId")
    CategoriaDTO toDto(Categoria categoria);

    @Mapping(source = "seccionId", target = "seccion")
    Categoria toEntity(CategoriaDTO categoriaDTO);

    default Categoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
