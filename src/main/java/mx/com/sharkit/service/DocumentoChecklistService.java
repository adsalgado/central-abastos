package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.domain.DocumentoChecklist;
import mx.com.sharkit.service.dto.DocumentoChecklistDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.DocumentoChecklist}.
 */
public interface DocumentoChecklistService extends BaseService<DocumentoChecklist, Long> {

    /**
     * Save a documentoChecklist.
     *
     * @param documentoChecklistDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentoChecklistDTO save(DocumentoChecklistDTO documentoChecklistDTO);

    /**
     * Get all the documentoChecklists.
     *
     * @return the list of entities.
     */
    List<DocumentoChecklistDTO> findAllDTO();


    /**
     * Get the "id" documentoChecklist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentoChecklistDTO> findOne(Long id);

    /**
     * Delete the "id" documentoChecklist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
