package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.Promocion;
import mx.com.sharkit.service.dto.PromocionDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Promocion}.
 */
public interface PromocionService extends BaseService<Promocion, Long> {

    /**
     * Save a promocion.
     *
     * @param promocionDTO the entity to save.
     * @return the persisted entity.
     */
    PromocionDTO save(PromocionDTO promocionDTO);

    /**
     * Get all the promocions.
     *
     * @return the list of entities.
     */
    List<PromocionDTO> findAllDTO();


    /**
     * Get the "id" promocion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PromocionDTO> findOne(Long id);

    /**
     * Delete the "id" promocion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
