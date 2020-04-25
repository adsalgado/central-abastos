package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
public interface ProductoProveedorService extends BaseService<ProductoProveedor, Long> {

    /**
     * Save a productoProveedor.
     *
     * @param productoProveedorDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoProveedorDTO save(ProductoProveedorDTO productoProveedorDTO);

    /**
     * Get all the productoProveedors.
     *
     * @return the list of entities.
     */
    List<ProductoProveedorDTO> findAllDTO();


    /**
     * Get the "id" productoProveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" productoProveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
