package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.domain.DocumentoChecklist;
import mx.com.sharkit.service.dto.DocumentoChecklistDTO;

/**
 * Mapper for the entity {@link DocumentoChecklist} and its DTO
 * {@link DocumentoChecklistoDTO}.
 */
@Mapper(componentModel = "spring", uses = { TipoUsuarioMapper.class, DocumentoMapper.class })
public interface DocumentoChecklistMapper extends EntityMapper<DocumentoChecklistDTO, DocumentoChecklist> {

	DocumentoChecklistDTO toDto(DocumentoChecklist documentoChecklist);

	DocumentoChecklist toEntity(DocumentoChecklistDTO documentoChecklistDTO);

	default DocumentoChecklist fromId(Long id) {
		if (id == null) {
			return null;
		}
		DocumentoChecklist documento = new DocumentoChecklist();
		documento.setId(id);
		return documento;
	}
}
