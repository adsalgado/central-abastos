package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;

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

}
