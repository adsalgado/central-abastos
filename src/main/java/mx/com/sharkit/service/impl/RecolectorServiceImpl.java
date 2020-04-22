package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.RecolectorService;
import mx.com.sharkit.domain.Recolector;
import mx.com.sharkit.repository.RecolectorRepository;
import mx.com.sharkit.service.dto.RecolectorDTO;
import mx.com.sharkit.service.mapper.RecolectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Recolector}.
 */
@Service
@Transactional
public class RecolectorServiceImpl implements RecolectorService {

    private final Logger log = LoggerFactory.getLogger(RecolectorServiceImpl.class);

    private final RecolectorRepository recolectorRepository;

    private final RecolectorMapper recolectorMapper;

    public RecolectorServiceImpl(RecolectorRepository recolectorRepository, RecolectorMapper recolectorMapper) {
        this.recolectorRepository = recolectorRepository;
        this.recolectorMapper = recolectorMapper;
    }

    /**
     * Save a recolector.
     *
     * @param recolectorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecolectorDTO save(RecolectorDTO recolectorDTO) {
        log.debug("Request to save Recolector : {}", recolectorDTO);
        Recolector recolector = recolectorMapper.toEntity(recolectorDTO);
        recolector = recolectorRepository.save(recolector);
        return recolectorMapper.toDto(recolector);
    }

    /**
     * Get all the recolectors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecolectorDTO> findAll() {
        log.debug("Request to get all Recolectors");
        return recolectorRepository.findAll().stream()
            .map(recolectorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one recolector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecolectorDTO> findOne(Long id) {
        log.debug("Request to get Recolector : {}", id);
        return recolectorRepository.findById(id)
            .map(recolectorMapper::toDto);
    }

    /**
     * Delete the recolector by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recolector : {}", id);
        recolectorRepository.deleteById(id);
    }
}
