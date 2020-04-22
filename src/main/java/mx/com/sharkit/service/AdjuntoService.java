package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.AdjuntoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Adjunto}.
 */
public interface AdjuntoService {

    /**
     * Save a adjunto.
     *
     * @param adjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    AdjuntoDTO save(AdjuntoDTO adjuntoDTO);

    /**
     * Get all the adjuntos.
     *
     * @return the list of entities.
     */
    List<AdjuntoDTO> findAll();


    /**
     * Get the "id" adjunto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdjuntoDTO> findOne(Long id);

    /**
     * Delete the "id" adjunto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
