package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import mx.com.sharkit.service.dto.UsuarioDocumentoDTO;

/**
 * Service Interface for managing
 * {@link mx.com.sharkit.domain.UsuarioDocumento}.
 */
public interface UsuarioDocumentoService {

	/**
	 * Save a UsuarioDocumento.
	 *
	 * @param UsuarioDocumentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	UsuarioDocumentoDTO save(UsuarioDocumentoDTO UsuarioDocumentoDTO);

	/**
	 * Get all the UsuarioDocumentos.
	 *
	 * @return the list of entities.
	 */
	List<UsuarioDocumentoDTO> findAllDTO();

	/**
	 * Get the "id" UsuarioDocumento.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<UsuarioDocumentoDTO> findOne(Long id);

	/**
	 * Delete the "id" UsuarioDocumento.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the UsuarioDocumentos by usuarioId.
	 *
	 * @param usuarioId id of user
	 * @return the list of entities.
	 */
	List<UsuarioDocumentoDTO> findByUsuarioId(Long usuarioId);
	
	Optional<UsuarioDocumentoDTO> findOneByUsuarioIdAndDocumentoId(Long usuarioId, Long documentoId);
	
	List<Map<String, Object>> findDocumentosUsuarioByTipo(Long usuarioId, Long tipoUsuarioId);

}
