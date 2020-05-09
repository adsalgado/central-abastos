package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.service.dto.DocumentoDTO;

/**
 * Mapper for the entity {@link Documento} and its DTO {@link DocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentoMapper extends EntityMapper<DocumentoDTO, Documento> {

	DocumentoDTO toDto(Documento documento);
	
    Documento toEntity(DocumentoDTO documentoDTO);

    default Documento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
    }
}
