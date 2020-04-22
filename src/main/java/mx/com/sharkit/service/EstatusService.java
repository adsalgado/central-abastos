package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.EstatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Estatus}.
 */
public interface EstatusService {

    /**
     * Save a estatus.
     *
     * @param estatusDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusDTO save(EstatusDTO estatusDTO);

    /**
     * Get all the estatuses.
     *
     * @return the list of entities.
     */
    List<EstatusDTO> findAll();


    /**
     * Get the "id" estatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusDTO> findOne(Long id);

    /**
     * Delete the "id" estatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
