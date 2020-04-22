package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import mx.com.sharkit.service.dto.ProductoDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Producto}.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoDTO save(ProductoDTO productoDTO);

    /**
     * Get all the productos.
     *
     * @return the list of entities.
     */
    List<ProductoDTO> findAll();


    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoDTO> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the productos.
     *
     * @return the list of entities.
     */
    List<ProductoDTO> searchProductos(Map<String, Object> params, Pageable pageable);
}
