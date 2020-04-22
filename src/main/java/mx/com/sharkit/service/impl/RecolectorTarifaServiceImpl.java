package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.RecolectorTarifaService;
import mx.com.sharkit.domain.RecolectorTarifa;
import mx.com.sharkit.repository.RecolectorTarifaRepository;
import mx.com.sharkit.service.dto.RecolectorTarifaDTO;
import mx.com.sharkit.service.mapper.RecolectorTarifaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RecolectorTarifa}.
 */
@Service
@Transactional
public class RecolectorTarifaServiceImpl implements RecolectorTarifaService {

    private final Logger log = LoggerFactory.getLogger(RecolectorTarifaServiceImpl.class);

    private final RecolectorTarifaRepository recolectorTarifaRepository;

    private final RecolectorTarifaMapper recolectorTarifaMapper;

    public RecolectorTarifaServiceImpl(RecolectorTarifaRepository recolectorTarifaRepository, RecolectorTarifaMapper recolectorTarifaMapper) {
        this.recolectorTarifaRepository = recolectorTarifaRepository;
        this.recolectorTarifaMapper = recolectorTarifaMapper;
    }

    /**
     * Save a recolectorTarifa.
     *
     * @param recolectorTarifaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecolectorTarifaDTO save(RecolectorTarifaDTO recolectorTarifaDTO) {
        log.debug("Request to save RecolectorTarifa : {}", recolectorTarifaDTO);
        RecolectorTarifa recolectorTarifa = recolectorTarifaMapper.toEntity(recolectorTarifaDTO);
        recolectorTarifa = recolectorTarifaRepository.save(recolectorTarifa);
        return recolectorTarifaMapper.toDto(recolectorTarifa);
    }

    /**
     * Get all the recolectorTarifas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecolectorTarifaDTO> findAll() {
        log.debug("Request to get all RecolectorTarifas");
        return recolectorTarifaRepository.findAll().stream()
            .map(recolectorTarifaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one recolectorTarifa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecolectorTarifaDTO> findOne(Long id) {
        log.debug("Request to get RecolectorTarifa : {}", id);
        return recolectorTarifaRepository.findById(id)
            .map(recolectorTarifaMapper::toDto);
    }

    /**
     * Delete the recolectorTarifa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecolectorTarifa : {}", id);
        recolectorTarifaRepository.deleteById(id);
    }
}
