package mx.com.sharkit.service;

import mx.com.sharkit.domain.CarritoCompra;
import mx.com.sharkit.service.dto.CarritoCompraDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.CarritoCompra}.
 */
public interface CarritoCompraService {

	/**
	 * Save a carritoCompra.
	 *
	 * @param carritoCompraDTO the entity to save.
	 * @return the persisted entity.
	 */
	CarritoCompraDTO save(CarritoCompraDTO carritoCompraDTO);

	/**
	 * Get all the carritoCompras.
	 *
	 * @return the list of entities.
	 */
	List<CarritoCompraDTO> findAll();

	/**
	 * Get the "id" carritoCompra.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<CarritoCompraDTO> findOne(Long id);

	/**
	 * Delete the "id" carritoCompra.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the carritoCompras of clienteId.
	 *
	 * @return the list of entities.
	 */
	List<CarritoCompraDTO> findAllByClienteId(Long clienteId);

	/**
	 * Delete the "id" carritoCompra.
	 *
	 * @param id the id of the entity.
	 */
	void deleteByClienteIdAnProductoProveedorId(Long clienteId, Long productoId);

	/**
	 * Delete the carritoCompra by clienteId.
	 *
	 * @param id the id of the entity.
	 */
	void deleteByClienteId(Long clienteId);

	/**
	 * Get the "id" carritoCompra.
	 * 
	 * @param clienteId
	 * @param productoId
	 * @return the entity 
	 */
	Optional<CarritoCompraDTO> findOneClienteIdAndProductoProveedorId(Long clienteId, Long productoId);

}
