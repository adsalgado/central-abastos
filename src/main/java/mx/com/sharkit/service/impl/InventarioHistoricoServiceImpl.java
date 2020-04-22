package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.InventarioHistoricoService;
import mx.com.sharkit.domain.InventarioHistorico;
import mx.com.sharkit.repository.InventarioHistoricoRepository;
import mx.com.sharkit.service.dto.InventarioHistoricoDTO;
import mx.com.sharkit.service.mapper.InventarioHistoricoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InventarioHistorico}.
 */
@Service
@Transactional
public class InventarioHistoricoServiceImpl implements InventarioHistoricoService {

    private final Logger log = LoggerFactory.getLogger(InventarioHistoricoServiceImpl.class);

    private final InventarioHistoricoRepository inventarioHistoricoRepository;

    private final InventarioHistoricoMapper inventarioHistoricoMapper;

    public InventarioHistoricoServiceImpl(InventarioHistoricoRepository inventarioHistoricoRepository, InventarioHistoricoMapper inventarioHistoricoMapper) {
        this.inventarioHistoricoRepository = inventarioHistoricoRepository;
        this.inventarioHistoricoMapper = inventarioHistoricoMapper;
    }

    /**
     * Save a inventarioHistorico.
     *
     * @param inventarioHistoricoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InventarioHistoricoDTO save(InventarioHistoricoDTO inventarioHistoricoDTO) {
        log.debug("Request to save InventarioHistorico : {}", inventarioHistoricoDTO);
        InventarioHistorico inventarioHistorico = inventarioHistoricoMapper.toEntity(inventarioHistoricoDTO);
        inventarioHistorico = inventarioHistoricoRepository.save(inventarioHistorico);
        return inventarioHistoricoMapper.toDto(inventarioHistorico);
    }

    /**
     * Get all the inventarioHistoricos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventarioHistoricoDTO> findAll() {
        log.debug("Request to get all InventarioHistoricos");
        return inventarioHistoricoRepository.findAll().stream()
            .map(inventarioHistoricoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one inventarioHistorico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InventarioHistoricoDTO> findOne(Long id) {
        log.debug("Request to get InventarioHistorico : {}", id);
        return inventarioHistoricoRepository.findById(id)
            .map(inventarioHistoricoMapper::toDto);
    }

    /**
     * Delete the inventarioHistorico by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InventarioHistorico : {}", id);
        inventarioHistoricoRepository.deleteById(id);
    }
}
