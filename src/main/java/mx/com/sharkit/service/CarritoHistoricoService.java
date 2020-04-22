package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.CarritoHistoricoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.CarritoHistorico}.
 */
public interface CarritoHistoricoService {

    /**
     * Save a carritoHistorico.
     *
     * @param carritoHistoricoDTO the entity to save.
     * @return the persisted entity.
     */
    CarritoHistoricoDTO save(CarritoHistoricoDTO carritoHistoricoDTO);

    /**
     * Get all the carritoHistoricos.
     *
     * @return the list of entities.
     */
    List<CarritoHistoricoDTO> findAll();


    /**
     * Get the "id" carritoHistorico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarritoHistoricoDTO> findOne(Long id);

    /**
     * Delete the "id" carritoHistorico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
