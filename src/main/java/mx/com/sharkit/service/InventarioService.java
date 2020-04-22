package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.InventarioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Inventario}.
 */
public interface InventarioService {

    /**
     * Save a inventario.
     *
     * @param inventarioDTO the entity to save.
     * @return the persisted entity.
     */
    InventarioDTO save(InventarioDTO inventarioDTO);

    /**
     * Get all the inventarios.
     *
     * @return the list of entities.
     */
    List<InventarioDTO> findAll();


    /**
     * Get the "id" inventario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventarioDTO> findOne(Long id);

    /**
     * Delete the "id" inventario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
