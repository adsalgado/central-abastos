package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

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

}
