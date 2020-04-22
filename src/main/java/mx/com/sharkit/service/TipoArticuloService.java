package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.TipoArticuloDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.TipoArticulo}.
 */
public interface TipoArticuloService {

    /**
     * Save a tipoArticulo.
     *
     * @param tipoArticuloDTO the entity to save.
     * @return the persisted entity.
     */
    TipoArticuloDTO save(TipoArticuloDTO tipoArticuloDTO);

    /**
     * Get all the tipoArticulos.
     *
     * @return the list of entities.
     */
    List<TipoArticuloDTO> findAll();


    /**
     * Get the "id" tipoArticulo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoArticuloDTO> findOne(Long id);

    /**
     * Delete the "id" tipoArticulo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
