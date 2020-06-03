package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.NotificacionDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Notificacion}.
 */
public interface NotificacionService {

    /**
     * Save a notificacion.
     *
     * @param notificacionDTO the entity to save.
     * @return the persisted entity.
     */
    NotificacionDTO save(NotificacionDTO notificacionDTO);

    /**
     * Get all the notificacions.
     *
     * @return the list of entities.
     */
    List<NotificacionDTO> findAll();


    /**
     * Get the "id" notificacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificacionDTO> findOne(Long id);

    /**
     * Delete the "id" notificacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the notificacions by usuarioId.
     *
     * @return the list of entities.
     */
    List<NotificacionDTO> findByUsuarioId(Long usuarioId);
}
