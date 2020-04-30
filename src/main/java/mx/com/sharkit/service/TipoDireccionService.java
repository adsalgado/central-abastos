package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.TipoDireccion;
import mx.com.sharkit.service.dto.TipoDireccionDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.TipoDireccion}.
 */
public interface TipoDireccionService extends BaseService<TipoDireccion, Long> {

    /**
     * Save a tipoDireccion.
     *
     * @param tipoDireccionDTO the entity to save.
     * @return the persisted entity.
     */
    TipoDireccionDTO save(TipoDireccionDTO tipoDireccionDTO);

    /**
     * Get all the tipoDireccions.
     *
     * @return the list of entities.
     */
    List<TipoDireccionDTO> findAllDTO();


    /**
     * Get the "id" tipoDireccion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDireccionDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDireccion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
