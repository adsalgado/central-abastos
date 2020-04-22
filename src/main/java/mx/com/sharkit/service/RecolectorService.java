package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.RecolectorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Recolector}.
 */
public interface RecolectorService {

    /**
     * Save a recolector.
     *
     * @param recolectorDTO the entity to save.
     * @return the persisted entity.
     */
    RecolectorDTO save(RecolectorDTO recolectorDTO);

    /**
     * Get all the recolectors.
     *
     * @return the list of entities.
     */
    List<RecolectorDTO> findAll();


    /**
     * Get the "id" recolector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecolectorDTO> findOne(Long id);

    /**
     * Delete the "id" recolector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
