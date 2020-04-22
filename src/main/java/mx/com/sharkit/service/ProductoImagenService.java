package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.ProductoImagenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.ProductoImagen}.
 */
public interface ProductoImagenService {

    /**
     * Save a productoImagen.
     *
     * @param productoImagenDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoImagenDTO save(ProductoImagenDTO productoImagenDTO);

    /**
     * Get all the productoImagens.
     *
     * @return the list of entities.
     */
    List<ProductoImagenDTO> findAll();


    /**
     * Get the "id" productoImagen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoImagenDTO> findOne(Long id);

    /**
     * Delete the "id" productoImagen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
