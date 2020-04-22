package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.PagosDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Pagos}.
 */
public interface PagosService {

    /**
     * Save a pagos.
     *
     * @param pagosDTO the entity to save.
     * @return the persisted entity.
     */
    PagosDTO save(PagosDTO pagosDTO);

    /**
     * Get all the pagos.
     *
     * @return the list of entities.
     */
    List<PagosDTO> findAll();


    /**
     * Get the "id" pagos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PagosDTO> findOne(Long id);

    /**
     * Delete the "id" pagos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
