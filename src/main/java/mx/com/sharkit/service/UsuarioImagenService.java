package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.UsuarioImagenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.UsuarioImagen}.
 */
public interface UsuarioImagenService {

    /**
     * Save a usuarioImagen.
     *
     * @param usuarioImagenDTO the entity to save.
     * @return the persisted entity.
     */
    UsuarioImagenDTO save(UsuarioImagenDTO usuarioImagenDTO);

    /**
     * Get all the usuarioImagens.
     *
     * @return the list of entities.
     */
    List<UsuarioImagenDTO> findAll();


    /**
     * Get the "id" usuarioImagen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioImagenDTO> findOne(Long id);

    /**
     * Delete the "id" usuarioImagen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
