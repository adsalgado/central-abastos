package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.enumeration.TipoEstatus;
import mx.com.sharkit.service.dto.EstatusDTO;

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
    
    /**
     * Get all the estatuses by tipoEstatus.
     *
     * @return the list of entities.
     */
    List<EstatusDTO> findAllByTipoEstatus(TipoEstatus tipoEstatus);

}
