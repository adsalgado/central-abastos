package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.CategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {

    @Mapping(source = "empresa.id", target = "empresaId")
    CategoriaDTO toDto(Categoria categoria);

    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "removeProducto", ignore = true)
    @Mapping(source = "empresaId", target = "empresa")
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
