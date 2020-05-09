package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.repository.DocumentoRepository;
import mx.com.sharkit.service.DocumentoService;
import mx.com.sharkit.service.dto.DocumentoDTO;
import mx.com.sharkit.service.mapper.DocumentoMapper;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoServiceImpl extends BaseServiceImpl<Documento, Long>
		implements DocumentoService {

	private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

	private final DocumentoRepository documentoRepository;

	private final DocumentoMapper documentoMapper;

	public DocumentoServiceImpl(DocumentoRepository documentoRepository,
			DocumentoMapper documentoMapper) {
		this.documentoRepository = documentoRepository;
		this.documentoMapper = documentoMapper;
	}

	/**
	 * Save a documento.
	 *
	 * @param documentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public DocumentoDTO save(DocumentoDTO documentoDTO) {
		log.debug("Request to save Documento : {}", documentoDTO);
		Documento documento = documentoMapper.toEntity(documentoDTO);
		documento = documentoRepository.save(documento);
		return documentoMapper.toDto(documento);
	}

	/**
	 * Get all the documentos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DocumentoDTO> findAllDTO() {
		log.debug("Request to get all Documentos");
		return documentoRepository.findAll().stream().map(documentoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one documento by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DocumentoDTO> findOne(Long id) {
		log.debug("Request to get Documento : {}", id);
		return documentoRepository.findById(id).map(documentoMapper::toDto);
	}

	/**
	 * Delete the documento by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Documento : {}", id);
		documentoRepository.deleteById(id);
	}


}
