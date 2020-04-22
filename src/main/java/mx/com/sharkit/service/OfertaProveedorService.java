package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.OfertaProveedorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.OfertaProveedor}.
 */
public interface OfertaProveedorService {

    /**
     * Save a ofertaProveedor.
     *
     * @param ofertaProveedorDTO the entity to save.
     * @return the persisted entity.
     */
    OfertaProveedorDTO save(OfertaProveedorDTO ofertaProveedorDTO);

    /**
     * Get all the ofertaProveedors.
     *
     * @return the list of entities.
     */
    List<OfertaProveedorDTO> findAll();


    /**
     * Get the "id" ofertaProveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfertaProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" ofertaProveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
