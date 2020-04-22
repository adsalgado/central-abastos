package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.EmpresaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Empresa}.
 */
public interface EmpresaService {

    /**
     * Save a empresa.
     *
     * @param empresaDTO the entity to save.
     * @return the persisted entity.
     */
    EmpresaDTO save(EmpresaDTO empresaDTO);

    /**
     * Get all the empresas.
     *
     * @return the list of entities.
     */
    List<EmpresaDTO> findAll();


    /**
     * Get the "id" empresa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpresaDTO> findOne(Long id);

    /**
     * Delete the "id" empresa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
