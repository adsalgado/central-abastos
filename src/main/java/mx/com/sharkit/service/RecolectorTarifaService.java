package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.RecolectorTarifaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.RecolectorTarifa}.
 */
public interface RecolectorTarifaService {

    /**
     * Save a recolectorTarifa.
     *
     * @param recolectorTarifaDTO the entity to save.
     * @return the persisted entity.
     */
    RecolectorTarifaDTO save(RecolectorTarifaDTO recolectorTarifaDTO);

    /**
     * Get all the recolectorTarifas.
     *
     * @return the list of entities.
     */
    List<RecolectorTarifaDTO> findAll();


    /**
     * Get the "id" recolectorTarifa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecolectorTarifaDTO> findOne(Long id);

    /**
     * Delete the "id" recolectorTarifa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
