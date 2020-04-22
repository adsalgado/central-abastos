package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.SeccionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Seccion}.
 */
public interface SeccionService {

    /**
     * Save a seccion.
     *
     * @param seccionDTO the entity to save.
     * @return the persisted entity.
     */
    SeccionDTO save(SeccionDTO seccionDTO);

    /**
     * Get all the seccions.
     *
     * @return the list of entities.
     */
    List<SeccionDTO> findAll();


    /**
     * Get the "id" seccion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeccionDTO> findOne(Long id);

    /**
     * Delete the "id" seccion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
