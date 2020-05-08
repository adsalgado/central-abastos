package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.TipoPersona;
import mx.com.sharkit.service.dto.TipoPersonaDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.TipoPersona}.
 */
public interface TipoPersonaService extends BaseService<TipoPersona, Long> {

    /**
     * Save a tipoPersona.
     *
     * @param tipoPersonaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoPersonaDTO save(TipoPersonaDTO tipoPersonaDTO);

    /**
     * Get all the tipoPersonas.
     *
     * @return the list of entities.
     */
    List<TipoPersonaDTO> findAllDTO();


    /**
     * Get the "id" tipoPersona.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPersonaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoPersona.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
