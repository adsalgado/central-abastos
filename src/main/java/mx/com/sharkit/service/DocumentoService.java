package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.service.dto.DocumentoDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Documento}.
 */
public interface DocumentoService extends BaseService<Documento, Long> {

    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentoDTO save(DocumentoDTO documentoDTO);

    /**
     * Get all the documentos.
     *
     * @return the list of entities.
     */
    List<DocumentoDTO> findAllDTO();


    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentoDTO> findOne(Long id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
