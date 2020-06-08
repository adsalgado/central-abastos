package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import mx.com.sharkit.domain.DefinicionParametros;
import mx.com.sharkit.service.dto.DefinicionParametrosDTO;

public interface DefinicionParametrosService extends BaseService<DefinicionParametros, Integer> {

	/**
	 * Save a DefinicionParametros.
	 *
	 * @param DefinicionParametrosDTO the entity to save.
	 * @return the persisted entity.
	 */
	DefinicionParametrosDTO save(DefinicionParametrosDTO DefinicionParametrosDTO);

	/**
	 * Get all the DefinicionParametross.
	 *
	 * @return the list of entities.
	 */
	List<DefinicionParametrosDTO> findAllDTO();

	/**
	 * Get the "id" DefinicionParametros.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<DefinicionParametrosDTO> findOne(Integer id);

	/**
	 * Delete the "id" DefinicionParametros.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Integer id);

	List<Map<String, Object>> findAllByQueryNativeToMap(String sSql);
	
	<TEntity> List<TEntity> findAllByQueryNativeToMap(final Class<TEntity> eClazz, String sSql);

}
