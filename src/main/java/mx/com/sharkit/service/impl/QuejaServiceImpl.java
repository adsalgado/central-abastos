package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.QuejaService;
import mx.com.sharkit.domain.Queja;
import mx.com.sharkit.repository.QuejaRepository;
import mx.com.sharkit.service.dto.QuejaDTO;
import mx.com.sharkit.service.mapper.QuejaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Queja}.
 */
@Service
@Transactional
public class QuejaServiceImpl extends BaseServiceImpl<Queja, Long> implements QuejaService {

    private final Logger log = LoggerFactory.getLogger(QuejaServiceImpl.class);

    private final QuejaRepository quejaRepository;

    private final QuejaMapper quejaMapper;

    public QuejaServiceImpl(QuejaRepository quejaRepository, QuejaMapper quejaMapper) {
        this.quejaRepository = quejaRepository;
        this.quejaMapper = quejaMapper;
    }

    /**
     * Save a queja.
     *
     * @param quejaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuejaDTO save(QuejaDTO quejaDTO) {
        log.debug("Request to save Queja : {}", quejaDTO);
        Queja queja = quejaMapper.toEntity(quejaDTO);
        queja = quejaRepository.save(queja);
        return quejaMapper.toDto(queja);
    }

    /**
     * Get all the quejas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuejaDTO> findAllDTO() {
        log.debug("Request to get all Quejas");
        return quejaRepository.findAll().stream()
            .map(quejaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one queja by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuejaDTO> findOne(Long id) {
        log.debug("Request to get Queja : {}", id);
        return quejaRepository.findById(id)
            .map(quejaMapper::toDto);
    }

    /**
     * Delete the queja by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Queja : {}", id);
        quejaRepository.deleteById(id);
    }
}
