package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.PagosService;
import mx.com.sharkit.domain.Pagos;
import mx.com.sharkit.repository.PagosRepository;
import mx.com.sharkit.service.dto.PagosDTO;
import mx.com.sharkit.service.mapper.PagosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pagos}.
 */
@Service
@Transactional
public class PagosServiceImpl implements PagosService {

    private final Logger log = LoggerFactory.getLogger(PagosServiceImpl.class);

    private final PagosRepository pagosRepository;

    private final PagosMapper pagosMapper;

    public PagosServiceImpl(PagosRepository pagosRepository, PagosMapper pagosMapper) {
        this.pagosRepository = pagosRepository;
        this.pagosMapper = pagosMapper;
    }

    /**
     * Save a pagos.
     *
     * @param pagosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PagosDTO save(PagosDTO pagosDTO) {
        log.debug("Request to save Pagos : {}", pagosDTO);
        Pagos pagos = pagosMapper.toEntity(pagosDTO);
        pagos = pagosRepository.save(pagos);
        return pagosMapper.toDto(pagos);
    }

    /**
     * Get all the pagos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PagosDTO> findAll() {
        log.debug("Request to get all Pagos");
        return pagosRepository.findAll().stream()
            .map(pagosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pagos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PagosDTO> findOne(Long id) {
        log.debug("Request to get Pagos : {}", id);
        return pagosRepository.findById(id)
            .map(pagosMapper::toDto);
    }

    /**
     * Delete the pagos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagos : {}", id);
        pagosRepository.deleteById(id);
    }
}
