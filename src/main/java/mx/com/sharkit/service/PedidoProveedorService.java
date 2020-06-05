package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.criterion.Order;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.service.dto.CalificacionPedidoProveedorDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.TerminarServicioPedidoProveedorDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.PedidoProveedor}.
 */
public interface PedidoProveedorService extends BaseService<PedidoProveedor, Long> {

	/**
	 * Save a pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	PedidoProveedorDTO save(PedidoProveedorDTO pedidoProveedorDTO);

	/**
	 * Get all the pedidoProveedors.
	 *
	 * @return the list of entities.
	 */
	List<PedidoProveedorDTO> findAllDTO();

	/**
	 * Get the "id" pedidoProveedor.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PedidoProveedorDTO> findOne(Long id);

	/**
	 * Delete the "id" pedidoProveedor.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the pedidoProveedors by pedidoId.
	 *
	 * @param pedidoId
	 * @return the list of entities.
	 */
	List<PedidoProveedorDTO> findByPedidoId(Long pedidoId);

	/**
	 * Get all the pedidoProveedors by pedidoId and proveedorId.
	 *
	 * @param pedidoId
	 * @param proveedorId
	 * 
	 * @return the list of entities.
	 */
	List<PedidoProveedorDTO> findByPedidoIdAndProveedorId(Long pedidoId, Long proveedorId);

	/**
	 * Get all the pedidoProveedors by pedidoId and transportistaId.
	 *
	 * @param pedidoId
	 * @param proveedorId
	 * 
	 * @return the list of entities.
	 */
	List<PedidoProveedorDTO> findByPedidoIdAndTransportistaId(Long pedidoId, Long transportistaId);

	/**
	 * Save a pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	PedidoProveedorDTO cambiaEstatusPedidoProveedorAndDetalles(Long pedidoProveedorId, Long estatus,
			Long usuarioEstatus);

	/**
	 * Save a pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	PedidoProveedorDTO actualizaCalificacionServicio(CalificacionPedidoProveedorDTO calificacionDTO, Long usuarioId);

	/**
	 * Terminar servicio.
	 * 
	 * @param terminarDTO
	 * @param usuarioId
	 * @return
	 * @throws Exception
	 */
	PedidoProveedorDTO terminarServicio(TerminarServicioPedidoProveedorDTO terminarDTO, Long usuarioId)
			throws Exception;

	/**
	 * Get all the pedidoProveedors by searchParams.
	 *
	 * @param params
	 * @return the list of entities.
	 */
	List<PedidoProveedorDTO> searchProductos(Map<String, Object> params);

}
