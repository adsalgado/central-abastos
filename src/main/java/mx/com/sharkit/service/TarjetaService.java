package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.TarjetaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Tarjeta}.
 */
public interface TarjetaService {

    /**
     * Save a tarjeta.
     *
     * @param tarjetaDTO the entity to save.
     * @return the persisted entity.
     */
    TarjetaDTO save(TarjetaDTO tarjetaDTO);

    /**
     * Get all the tarjetas.
     *
     * @return the list of entities.
     */
    List<TarjetaDTO> findAll();


    /**
     * Get the "id" tarjeta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarjetaDTO> findOne(Long id);

    /**
     * Delete the "id" tarjeta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
