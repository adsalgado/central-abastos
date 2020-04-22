package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.InventarioHistoricoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.InventarioHistorico}.
 */
public interface InventarioHistoricoService {

    /**
     * Save a inventarioHistorico.
     *
     * @param inventarioHistoricoDTO the entity to save.
     * @return the persisted entity.
     */
    InventarioHistoricoDTO save(InventarioHistoricoDTO inventarioHistoricoDTO);

    /**
     * Get all the inventarioHistoricos.
     *
     * @return the list of entities.
     */
    List<InventarioHistoricoDTO> findAll();


    /**
     * Get the "id" inventarioHistorico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventarioHistoricoDTO> findOne(Long id);

    /**
     * Delete the "id" inventarioHistorico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
