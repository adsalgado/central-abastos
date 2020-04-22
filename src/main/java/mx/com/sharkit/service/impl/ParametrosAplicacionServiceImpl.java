package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.ParametrosAplicacionService;
import mx.com.sharkit.domain.ParametrosAplicacion;
import mx.com.sharkit.repository.ParametrosAplicacionRepository;
import mx.com.sharkit.service.dto.ParametrosAplicacionDTO;
import mx.com.sharkit.service.mapper.ParametrosAplicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ParametrosAplicacion}.
 */
@Service
@Transactional
public class ParametrosAplicacionServiceImpl implements ParametrosAplicacionService {

    private final Logger log = LoggerFactory.getLogger(ParametrosAplicacionServiceImpl.class);

    private final ParametrosAplicacionRepository parametrosAplicacionRepository;

    private final ParametrosAplicacionMapper parametrosAplicacionMapper;

    public ParametrosAplicacionServiceImpl(ParametrosAplicacionRepository parametrosAplicacionRepository, ParametrosAplicacionMapper parametrosAplicacionMapper) {
        this.parametrosAplicacionRepository = parametrosAplicacionRepository;
        this.parametrosAplicacionMapper = parametrosAplicacionMapper;
    }

    /**
     * Save a parametrosAplicacion.
     *
     * @param parametrosAplicacionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParametrosAplicacionDTO save(ParametrosAplicacionDTO parametrosAplicacionDTO) {
        log.debug("Request to save ParametrosAplicacion : {}", parametrosAplicacionDTO);
        ParametrosAplicacion parametrosAplicacion = parametrosAplicacionMapper.toEntity(parametrosAplicacionDTO);
        parametrosAplicacion = parametrosAplicacionRepository.save(parametrosAplicacion);
        return parametrosAplicacionMapper.toDto(parametrosAplicacion);
    }

    /**
     * Get all the parametrosAplicacions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParametrosAplicacionDTO> findAll() {
        log.debug("Request to get all ParametrosAplicacions");
        return parametrosAplicacionRepository.findAll().stream()
            .map(parametrosAplicacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one parametrosAplicacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametrosAplicacionDTO> findOne(Long id) {
        log.debug("Request to get ParametrosAplicacion : {}", id);
        return parametrosAplicacionRepository.findById(id)
            .map(parametrosAplicacionMapper::toDto);
    }

    /**
     * Delete the parametrosAplicacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParametrosAplicacion : {}", id);
        parametrosAplicacionRepository.deleteById(id);
    }
}
