package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.TransportistaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Transportista}.
 */
public interface TransportistaService {

    /**
     * Save a transportista.
     *
     * @param transportistaDTO the entity to save.
     * @return the persisted entity.
     */
    TransportistaDTO save(TransportistaDTO transportistaDTO);

    /**
     * Get all the transportistas.
     *
     * @return the list of entities.
     */
    List<TransportistaDTO> findAll();


    /**
     * Get the "id" transportista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransportistaDTO> findOne(Long id);

    /**
     * Delete the "id" transportista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
