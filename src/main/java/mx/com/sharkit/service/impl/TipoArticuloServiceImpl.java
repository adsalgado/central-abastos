package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.TipoArticuloService;
import mx.com.sharkit.domain.TipoArticulo;
import mx.com.sharkit.repository.TipoArticuloRepository;
import mx.com.sharkit.service.dto.TipoArticuloDTO;
import mx.com.sharkit.service.mapper.TipoArticuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TipoArticulo}.
 */
@Service
@Transactional
public class TipoArticuloServiceImpl implements TipoArticuloService {

    private final Logger log = LoggerFactory.getLogger(TipoArticuloServiceImpl.class);

    private final TipoArticuloRepository tipoArticuloRepository;

    private final TipoArticuloMapper tipoArticuloMapper;

    public TipoArticuloServiceImpl(TipoArticuloRepository tipoArticuloRepository, TipoArticuloMapper tipoArticuloMapper) {
        this.tipoArticuloRepository = tipoArticuloRepository;
        this.tipoArticuloMapper = tipoArticuloMapper;
    }

    /**
     * Save a tipoArticulo.
     *
     * @param tipoArticuloDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoArticuloDTO save(TipoArticuloDTO tipoArticuloDTO) {
        log.debug("Request to save TipoArticulo : {}", tipoArticuloDTO);
        TipoArticulo tipoArticulo = tipoArticuloMapper.toEntity(tipoArticuloDTO);
        tipoArticulo = tipoArticuloRepository.save(tipoArticulo);
        return tipoArticuloMapper.toDto(tipoArticulo);
    }

    /**
     * Get all the tipoArticulos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoArticuloDTO> findAll() {
        log.debug("Request to get all TipoArticulos");
        return tipoArticuloRepository.findAll().stream()
            .map(tipoArticuloMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tipoArticulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoArticuloDTO> findOne(Long id) {
        log.debug("Request to get TipoArticulo : {}", id);
        return tipoArticuloRepository.findById(id)
            .map(tipoArticuloMapper::toDto);
    }

    /**
     * Delete the tipoArticulo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoArticulo : {}", id);
        tipoArticuloRepository.deleteById(id);
    }
}
