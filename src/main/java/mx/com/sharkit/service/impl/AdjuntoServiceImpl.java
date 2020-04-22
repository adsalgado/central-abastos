package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.AdjuntoService;
import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.mapper.AdjuntoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Adjunto}.
 */
@Service
@Transactional
public class AdjuntoServiceImpl implements AdjuntoService {

    private final Logger log = LoggerFactory.getLogger(AdjuntoServiceImpl.class);

    private final AdjuntoRepository adjuntoRepository;

    private final AdjuntoMapper adjuntoMapper;

    public AdjuntoServiceImpl(AdjuntoRepository adjuntoRepository, AdjuntoMapper adjuntoMapper) {
        this.adjuntoRepository = adjuntoRepository;
        this.adjuntoMapper = adjuntoMapper;
    }

    /**
     * Save a adjunto.
     *
     * @param adjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdjuntoDTO save(AdjuntoDTO adjuntoDTO) {
        log.debug("Request to save Adjunto : {}", adjuntoDTO);
        Adjunto adjunto = adjuntoMapper.toEntity(adjuntoDTO);
        adjunto = adjuntoRepository.save(adjunto);
        return adjuntoMapper.toDto(adjunto);
    }

    /**
     * Get all the adjuntos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AdjuntoDTO> findAll() {
        log.debug("Request to get all Adjuntos");
        return adjuntoRepository.findAll().stream()
            .map(adjuntoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one adjunto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdjuntoDTO> findOne(Long id) {
        log.debug("Request to get Adjunto : {}", id);
        return adjuntoRepository.findById(id)
            .map(adjuntoMapper::toDto);
    }

    /**
     * Delete the adjunto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adjunto : {}", id);
        adjuntoRepository.deleteById(id);
    }
}
