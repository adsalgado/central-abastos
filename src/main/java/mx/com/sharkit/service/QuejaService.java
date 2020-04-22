package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.QuejaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Queja}.
 */
public interface QuejaService {

    /**
     * Save a queja.
     *
     * @param quejaDTO the entity to save.
     * @return the persisted entity.
     */
    QuejaDTO save(QuejaDTO quejaDTO);

    /**
     * Get all the quejas.
     *
     * @return the list of entities.
     */
    List<QuejaDTO> findAll();


    /**
     * Get the "id" queja.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuejaDTO> findOne(Long id);

    /**
     * Delete the "id" queja.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
