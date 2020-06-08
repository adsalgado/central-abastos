package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.domain.PedidoProveedorHistorico;
import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.PedidoProveedorHistorico}.
 */
public interface PedidoProveedorHistoricoService extends BaseService<PedidoProveedorHistorico, Long> {

	/**
	 * Save a pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	PedidoProveedorHistoricoDTO save(PedidoProveedorHistoricoDTO pedidoProveedorDTO);

	/**
	 * Get all the pedidoProveedors.
	 *
	 * @return the list of entities.
	 */
	List<PedidoProveedorHistoricoDTO> findAllDTO();

	/**
	 * Get the "id" pedidoProveedor.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PedidoProveedorHistoricoDTO> findOne(Long id);

	/**
	 * Delete the "id" pedidoProveedor.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the pedidoProveedors by pedidoId.
	 *
	 * @param pedidoProveedorId
	 * @return the list of entities.
	 */
	List<PedidoProveedorHistoricoDTO> findByPedidoProveedorId(Long pedidoProveedorId);

	/**
	 * Get all the pedidoProveedors by pedidoId and proveedorId.
	 *
	 * @param pedidoProveedorId
	 * 
	 * @return the list of entities.
	 */
	List<PedidoProveedorHistoricoDTO> findByPedidoProveedorIdOrderByFecha(Long pedidoProveedorId);
	
	void savePedidoProveedorHistorico(PedidoProveedor pedidoProveedor);

}
