package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.TipoOfertaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.TipoOferta}.
 */
public interface TipoOfertaService {

    /**
     * Save a tipoOferta.
     *
     * @param tipoOfertaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoOfertaDTO save(TipoOfertaDTO tipoOfertaDTO);

    /**
     * Get all the tipoOfertas.
     *
     * @return the list of entities.
     */
    List<TipoOfertaDTO> findAll();


    /**
     * Get the "id" tipoOferta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoOfertaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoOferta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
