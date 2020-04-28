package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.UsuarioDireccionDTO;

/**
 * Service Interface for managing
 * {@link mx.com.sharkit.domain.UsuarioDireccion}.
 */
public interface UsuarioDireccionService {

	/**
	 * Save a UsuarioDireccion.
	 *
	 * @param UsuarioDireccionDTO the entity to save.
	 * @return the persisted entity.
	 */
	UsuarioDireccionDTO save(UsuarioDireccionDTO UsuarioDireccionDTO);

	/**
	 * Get all the UsuarioDireccions.
	 *
	 * @return the list of entities.
	 */
	List<UsuarioDireccionDTO> findAll();

	/**
	 * Get the "id" UsuarioDireccion.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<UsuarioDireccionDTO> findOne(Long id);

	/**
	 * Delete the "id" UsuarioDireccion.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the UsuarioDireccions by usuarioId.
	 *
	 * @param usuarioId id of user
	 * @return the list of entities.
	 */
	List<UsuarioDireccionDTO> findByUsuarioId(Long usuarioId);

}
