package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.DocumentoChecklist;
import mx.com.sharkit.repository.DocumentoChecklistRepository;
import mx.com.sharkit.service.DocumentoChecklistService;
import mx.com.sharkit.service.dto.DocumentoChecklistDTO;
import mx.com.sharkit.service.mapper.DocumentoChecklistMapper;

/**
 * Service Implementation for managing {@link DocumentoChecklist}.
 */
@Service
@Transactional
public class DocumentoChecklistServiceImpl extends BaseServiceImpl<DocumentoChecklist, Long>
		implements DocumentoChecklistService {

	private final Logger log = LoggerFactory.getLogger(DocumentoChecklistServiceImpl.class);

	private final DocumentoChecklistRepository documentoChecklistRepository;

	private final DocumentoChecklistMapper documentoChecklistMapper;

	public DocumentoChecklistServiceImpl(DocumentoChecklistRepository documentoChecklistRepository,
			DocumentoChecklistMapper documentoChecklistMapper) {
		this.documentoChecklistRepository = documentoChecklistRepository;
		this.documentoChecklistMapper = documentoChecklistMapper;
	}

	/**
	 * Save a documentoChecklist.
	 *
	 * @param documentoChecklistDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public DocumentoChecklistDTO save(DocumentoChecklistDTO documentoChecklistDTO) {
		log.debug("Request to save DocumentoChecklist : {}", documentoChecklistDTO);
		DocumentoChecklist documentoChecklist = documentoChecklistMapper.toEntity(documentoChecklistDTO);
		documentoChecklist = documentoChecklistRepository.save(documentoChecklist);
		return documentoChecklistMapper.toDto(documentoChecklist);
	}

	/**
	 * Get all the documentoChecklists.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DocumentoChecklistDTO> findAllDTO() {
		log.debug("Request to get all DocumentoChecklists");
		return documentoChecklistRepository.findAll().stream().map(documentoChecklistMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one documentoChecklist by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DocumentoChecklistDTO> findOne(Long id) {
		log.debug("Request to get DocumentoChecklist : {}", id);
		return documentoChecklistRepository.findById(id).map(documentoChecklistMapper::toDto);
	}

	/**
	 * Delete the documentoChecklist by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete DocumentoChecklist : {}", id);
		documentoChecklistRepository.deleteById(id);
	}


}
