package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.ParametrosAplicacionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.ParametrosAplicacion}.
 */
public interface ParametrosAplicacionService {

    /**
     * Save a parametrosAplicacion.
     *
     * @param parametrosAplicacionDTO the entity to save.
     * @return the persisted entity.
     */
    ParametrosAplicacionDTO save(ParametrosAplicacionDTO parametrosAplicacionDTO);

    /**
     * Get all the parametrosAplicacions.
     *
     * @return the list of entities.
     */
    List<ParametrosAplicacionDTO> findAll();


    /**
     * Get the "id" parametrosAplicacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametrosAplicacionDTO> findOne(Long id);

    /**
     * Delete the "id" parametrosAplicacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
