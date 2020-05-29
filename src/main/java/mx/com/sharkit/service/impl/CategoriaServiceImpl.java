package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.CategoriaService;
import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.domain.Categoria;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.repository.CategoriaRepository;
import mx.com.sharkit.service.dto.CategoriaDTO;
import mx.com.sharkit.service.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Categoria}.
 */
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;
    
	@Autowired
	private AdjuntoRepository adjuntoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
		if (categoriaDTO.getAdjunto() != null) {
			Adjunto adjunto = null;
			if (categoriaDTO.getAdjunto().getId() != null) {
				adjunto = adjuntoRepository.findById(categoriaDTO.getAdjunto().getId()).orElse(null);
				if (adjunto != null) {
					adjunto.setFileName(categoriaDTO.getAdjunto().getFileName());
					adjunto.setFile(categoriaDTO.getAdjunto().getFile());
					adjunto.setContentType(categoriaDTO.getAdjunto().getContentType());
					adjunto.setFileContentType(categoriaDTO.getAdjunto().getFileContentType());
					adjunto.setSize(categoriaDTO.getAdjunto().getSize());
				}
			}

			if (adjunto == null) {
				adjunto = new Adjunto();
				adjunto.setFileName(categoriaDTO.getAdjunto().getFileName());
				adjunto.setFile(categoriaDTO.getAdjunto().getFile());
				adjunto.setContentType(categoriaDTO.getAdjunto().getContentType());
				adjunto.setFileContentType(categoriaDTO.getAdjunto().getFileContentType());
				adjunto.setSize(categoriaDTO.getAdjunto().getSize());
				adjuntoRepository.save(adjunto);
			}

			categoria.setAdjuntoId(adjunto.getId());
		}

        return categoriaMapper.toDto(categoria);
    }

    /**
     * Get all the categorias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        log.debug("Request to get all Categorias");
        return categoriaRepository.findAll().stream()
            .map(categoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one categoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        return categoriaRepository.findById(id)
            .map(categoriaMapper::toDto);
    }

    /**
     * Delete the categoria by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
    }

	@Override
	public List<CategoriaDTO> findBySeccionId(Long seccionId) {
		log.debug("Request to get all Categorias by seccionId {}", seccionId);
        return categoriaRepository.findBySeccionId(seccionId).stream()
            .map(categoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
	}
}
