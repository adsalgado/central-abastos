package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import com.stripe.model.Charge;

import mx.com.sharkit.service.dto.PedidoAltaDTO;
import mx.com.sharkit.service.dto.PedidoDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Pedido}.
 */
public interface PedidoService {

	/**
	 * Save a pedido.
	 *
	 * @param pedidoDTO the entity to save.
	 * @return the persisted entity.
	 */
	PedidoDTO save(PedidoDTO pedidoDTO);

	/**
	 * Get all the pedidos.
	 *
	 * @return the list of entities.
	 */
	List<PedidoDTO> findAll();

	/**
	 * Get the "id" pedido.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PedidoDTO> findOne(Long id);

	/**
	 * Delete the "id" pedido.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Save a pedido from list.
	 *
	 * @param pedidoAltaDTO lista de productos
	 * @return the persisted entity
	 */
	PedidoDTO generaNuevoPedido(PedidoAltaDTO pedidoAltaDTO) throws Exception;

	/**
	 * Get all the pedidos by clienteId.
	 *
	 * @param clienteId
	 * @return the list of entities.
	 */
	List<PedidoDTO> findByClienteId(Long clienteId);

	/**
	 * Get all the pedidos by proveedorId.
	 *
	 * @param proveedorId
	 * @return the list of entities.
	 */
	List<PedidoDTO> findByProveedorId(Long proveedorId);

	/**
	 * Get all the pedidos by transportistaId.
	 *
	 * @param transportistaId
	 * @return the list of entities.
	 */
	List<PedidoDTO> findByTransportistaId(Long transportistaId);

	/**
	 * Change status a pedido.
	 *
	 * @param pedidoId
	 * @param estatus
	 * @param usuarioEstatus
	 * @return the persisted entity.
	 */
	PedidoDTO cambiaEstatusPedidoAndPedidoProveedores(Long pedidoId, Long estatus, Long usuarioEstatus);

	/**
	 * Registra pago exitoso Stripe
	 * 
	 * @param pedidoId
	 * @param charge
	 * @param usuarioEstatus
	 * @return
	 */
	PedidoDTO registraPagoPedido(Long pedidoId, Charge charge, Long usuarioEstatus);

}
