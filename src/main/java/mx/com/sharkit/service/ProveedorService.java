package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import mx.com.sharkit.domain.Proveedor;
import mx.com.sharkit.service.dto.ProveedorDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Proveedor}.
 */
public interface ProveedorService extends BaseService<Proveedor, Long> {

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */
    ProveedorDTO save(ProveedorDTO proveedorDTO);

    /**
     * Get all the proveedors.
     *
     * @return the list of entities.
     */
    List<ProveedorDTO> findAllDTO();


    /**
     * Get the "id" proveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" proveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all the proveedors by productoId.
     *
     * @return the list of entities.
     */
    List<ProveedorDTO> findAllByProductoId(Long productoId);
    
    /**
     * Get the "id" proveedor by usuarioId.
     *
     * @param usuarioId the usuarioId of the entity.
     * @return the entity.
     */
    Optional<ProveedorDTO> findOneByusuarioId(Long usuarioId);

    /**
     * Get the proveedor by userName.
     *
     * @param userName the userName of the user.
     * @return the entity.
     */
	Optional<ProveedorDTO> findOneByUserName(String userName);

	/**
     * Update the transportista of a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */
    ProveedorDTO updateTransportistaProveedor(@Valid ProveedorDTO proveedorDTO);
    
}
