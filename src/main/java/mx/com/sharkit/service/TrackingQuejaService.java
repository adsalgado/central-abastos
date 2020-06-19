package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.TrackingQuejaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.TrackingQueja}.
 */
public interface TrackingQuejaService {

    /**
     * Save a trackingQueja.
     *
     * @param trackingQuejaDTO the entity to save.
     * @return the persisted entity.
     */
    TrackingQuejaDTO save(TrackingQuejaDTO trackingQuejaDTO);

    /**
     * Get all the trackingQuejas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrackingQuejaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" trackingQueja.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrackingQuejaDTO> findOne(Long id);

    /**
     * Delete the "id" trackingQueja.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
