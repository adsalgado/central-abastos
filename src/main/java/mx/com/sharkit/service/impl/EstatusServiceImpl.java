package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.EstatusService;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.repository.EstatusRepository;
import mx.com.sharkit.service.dto.EstatusDTO;
import mx.com.sharkit.service.mapper.EstatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Estatus}.
 */
@Service
@Transactional
public class EstatusServiceImpl implements EstatusService {

    private final Logger log = LoggerFactory.getLogger(EstatusServiceImpl.class);

    private final EstatusRepository estatusRepository;

    private final EstatusMapper estatusMapper;

    public EstatusServiceImpl(EstatusRepository estatusRepository, EstatusMapper estatusMapper) {
        this.estatusRepository = estatusRepository;
        this.estatusMapper = estatusMapper;
    }

    /**
     * Save a estatus.
     *
     * @param estatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusDTO save(EstatusDTO estatusDTO) {
        log.debug("Request to save Estatus : {}", estatusDTO);
        Estatus estatus = estatusMapper.toEntity(estatusDTO);
        estatus = estatusRepository.save(estatus);
        return estatusMapper.toDto(estatus);
    }

    /**
     * Get all the estatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EstatusDTO> findAll() {
        log.debug("Request to get all Estatuses");
        return estatusRepository.findAll().stream()
            .map(estatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one estatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusDTO> findOne(Long id) {
        log.debug("Request to get Estatus : {}", id);
        return estatusRepository.findById(id)
            .map(estatusMapper::toDto);
    }

    /**
     * Delete the estatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estatus : {}", id);
        estatusRepository.deleteById(id);
    }
}
