package mx.com.sharkit.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.ProveedorTarifaDTO;

/**
 * Service Interface for managing
 * {@link mx.com.sharkit.domain.ProveedorTarifa}.
 */
public interface ProveedorTarifaService {

	/**
	 * Save a proveedorTarifa.
	 *
	 * @param proveedorTarifaDTO the entity to save.
	 * @return the persisted entity.
	 */
	ProveedorTarifaDTO save(ProveedorTarifaDTO proveedorTarifaDTO);

	/**
	 * Get all the proveedorTarifas.
	 *
	 * @return the list of entities.
	 */
	List<ProveedorTarifaDTO> findAll();

	/**
	 * Get the "id" proveedorTarifa.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ProveedorTarifaDTO> findOne(Long id);

	/**
	 * Delete the "id" proveedorTarifa.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	BigDecimal calculaTarifaProveedor(Long proveedorId, BigDecimal valor);

	/**
	 * Get all the proveedorTarifas by transportistaId.
	 *
	 * @param transportistaId
	 * @return the list of entities.
	 */
	List<ProveedorTarifaDTO> findAllOrderByRangoMinimo();

}
