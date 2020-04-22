package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.InventarioService;
import mx.com.sharkit.domain.Inventario;
import mx.com.sharkit.repository.InventarioRepository;
import mx.com.sharkit.service.dto.InventarioDTO;
import mx.com.sharkit.service.mapper.InventarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Inventario}.
 */
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);

    private final InventarioRepository inventarioRepository;

    private final InventarioMapper inventarioMapper;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, InventarioMapper inventarioMapper) {
        this.inventarioRepository = inventarioRepository;
        this.inventarioMapper = inventarioMapper;
    }

    /**
     * Save a inventario.
     *
     * @param inventarioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InventarioDTO save(InventarioDTO inventarioDTO) {
        log.debug("Request to save Inventario : {}", inventarioDTO);
        Inventario inventario = inventarioMapper.toEntity(inventarioDTO);
        inventario = inventarioRepository.save(inventario);
        return inventarioMapper.toDto(inventario);
    }

    /**
     * Get all the inventarios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventarioDTO> findAll() {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll().stream()
            .map(inventarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one inventario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InventarioDTO> findOne(Long id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findById(id)
            .map(inventarioMapper::toDto);
    }

    /**
     * Delete the inventario by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.deleteById(id);
    }
}
