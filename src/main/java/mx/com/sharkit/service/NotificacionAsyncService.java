package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.NotificacionDTO;

public interface NotificacionAsyncService {

    /**
     * Save a notificacion.
     *
     * @param notificacionDTO the entity to save.
     * @return the persisted entity.
     */
    void save(NotificacionDTO notificacionDTO);

}
