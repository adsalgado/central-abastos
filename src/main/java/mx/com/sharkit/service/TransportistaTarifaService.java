package mx.com.sharkit.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.TransportistaTarifaDTO;

/**
 * Service Interface for managing
 * {@link mx.com.sharkit.domain.TransportistaTarifa}.
 */
public interface TransportistaTarifaService {

	/**
	 * Save a transportistaTarifa.
	 *
	 * @param transportistaTarifaDTO the entity to save.
	 * @return the persisted entity.
	 */
	TransportistaTarifaDTO save(TransportistaTarifaDTO transportistaTarifaDTO);

	/**
	 * Get all the transportistaTarifas.
	 *
	 * @return the list of entities.
	 */
	List<TransportistaTarifaDTO> findAll();

	/**
	 * Get the "id" transportistaTarifa.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<TransportistaTarifaDTO> findOne(Long id);

	/**
	 * Delete the "id" transportistaTarifa.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	BigDecimal calculaTarifaTransportista(Long transportistaId, BigDecimal valor);

	/**
	 * Get all the transportistaTarifas by transportistaId.
	 *
	 * @param transportistaId
	 * @return the list of entities.
	 */
	List<TransportistaTarifaDTO> findAllByTransportistaId(Long transportistaId);

}
