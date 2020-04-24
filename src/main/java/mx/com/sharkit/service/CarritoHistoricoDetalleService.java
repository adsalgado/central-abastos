package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.CarritoHistoricoDetalle}.
 */
public interface CarritoHistoricoDetalleService {

    /**
     * Save a carritoHistoricoDetalle.
     *
     * @param carritoHistoricoDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    CarritoHistoricoDetalleDTO save(CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO);

    /**
     * Save a carritoHistoricoDetalle list.
     *
     * @param lstCarritoHistoricoDetalleDTO list entity to save.
     * @return the persisted entity list.
     */
    List<CarritoHistoricoDetalleDTO> saveAll(List<CarritoHistoricoDetalleDTO> lstCarritoHistoricoDetalleDTO);

    /**
     * Get all the carritoHistoricoDetalles.
     *
     * @return the list of entities.
     */
    List<CarritoHistoricoDetalleDTO> findAll();


    /**
     * Get the "id" carritoHistoricoDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarritoHistoricoDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" carritoHistoricoDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the carritoHistoricoDetalles.
     *
     * @return the list of entities.
     */
	List<CarritoHistoricoDetalleDTO> findByCarritoHistoricoId(Long carritoHistoricoId);

}
