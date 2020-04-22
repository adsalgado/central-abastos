package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.SeccionService;
import mx.com.sharkit.domain.Seccion;
import mx.com.sharkit.repository.SeccionRepository;
import mx.com.sharkit.service.dto.SeccionDTO;
import mx.com.sharkit.service.mapper.SeccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Seccion}.
 */
@Service
@Transactional
public class SeccionServiceImpl implements SeccionService {

    private final Logger log = LoggerFactory.getLogger(SeccionServiceImpl.class);

    private final SeccionRepository seccionRepository;

    private final SeccionMapper seccionMapper;

    public SeccionServiceImpl(SeccionRepository seccionRepository, SeccionMapper seccionMapper) {
        this.seccionRepository = seccionRepository;
        this.seccionMapper = seccionMapper;
    }

    /**
     * Save a seccion.
     *
     * @param seccionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SeccionDTO save(SeccionDTO seccionDTO) {
        log.debug("Request to save Seccion : {}", seccionDTO);
        Seccion seccion = seccionMapper.toEntity(seccionDTO);
        seccion = seccionRepository.save(seccion);
        return seccionMapper.toDto(seccion);
    }

    /**
     * Get all the seccions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeccionDTO> findAll() {
        log.debug("Request to get all Seccions");
        return seccionRepository.findAll().stream()
            .map(seccionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one seccion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeccionDTO> findOne(Long id) {
        log.debug("Request to get Seccion : {}", id);
        return seccionRepository.findById(id)
            .map(seccionMapper::toDto);
    }

    /**
     * Delete the seccion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seccion : {}", id);
        seccionRepository.deleteById(id);
    }
}
