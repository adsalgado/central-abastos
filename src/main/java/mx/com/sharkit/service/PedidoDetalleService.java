package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.PedidoDetalleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.PedidoDetalle}.
 */
public interface PedidoDetalleService {

    /**
     * Save a pedidoDetalle.
     *
     * @param pedidoDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    PedidoDetalleDTO save(PedidoDetalleDTO pedidoDetalleDTO);

    /**
     * Get all the pedidoDetalles.
     *
     * @return the list of entities.
     */
    List<PedidoDetalleDTO> findAll();


    /**
     * Get the "id" pedidoDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidoDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" pedidoDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the pedidoDetalles by pedidoProveedorId.
     *
     * @param pedidoProveedorId
     * @return the list of entities.
     */
    List<PedidoDetalleDTO> findByPedidoProveedorId(Long pedidoProveedorId);
    
}
