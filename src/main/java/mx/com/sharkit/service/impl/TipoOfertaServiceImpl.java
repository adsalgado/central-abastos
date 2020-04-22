package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.TipoOfertaService;
import mx.com.sharkit.domain.TipoOferta;
import mx.com.sharkit.repository.TipoOfertaRepository;
import mx.com.sharkit.service.dto.TipoOfertaDTO;
import mx.com.sharkit.service.mapper.TipoOfertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TipoOferta}.
 */
@Service
@Transactional
public class TipoOfertaServiceImpl implements TipoOfertaService {

    private final Logger log = LoggerFactory.getLogger(TipoOfertaServiceImpl.class);

    private final TipoOfertaRepository tipoOfertaRepository;

    private final TipoOfertaMapper tipoOfertaMapper;

    public TipoOfertaServiceImpl(TipoOfertaRepository tipoOfertaRepository, TipoOfertaMapper tipoOfertaMapper) {
        this.tipoOfertaRepository = tipoOfertaRepository;
        this.tipoOfertaMapper = tipoOfertaMapper;
    }

    /**
     * Save a tipoOferta.
     *
     * @param tipoOfertaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoOfertaDTO save(TipoOfertaDTO tipoOfertaDTO) {
        log.debug("Request to save TipoOferta : {}", tipoOfertaDTO);
        TipoOferta tipoOferta = tipoOfertaMapper.toEntity(tipoOfertaDTO);
        tipoOferta = tipoOfertaRepository.save(tipoOferta);
        return tipoOfertaMapper.toDto(tipoOferta);
    }

    /**
     * Get all the tipoOfertas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoOfertaDTO> findAll() {
        log.debug("Request to get all TipoOfertas");
        return tipoOfertaRepository.findAll().stream()
            .map(tipoOfertaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tipoOferta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoOfertaDTO> findOne(Long id) {
        log.debug("Request to get TipoOferta : {}", id);
        return tipoOfertaRepository.findById(id)
            .map(tipoOfertaMapper::toDto);
    }

    /**
     * Delete the tipoOferta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoOferta : {}", id);
        tipoOfertaRepository.deleteById(id);
    }
}
