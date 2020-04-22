package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.OfertaProveedorService;
import mx.com.sharkit.domain.OfertaProveedor;
import mx.com.sharkit.repository.OfertaProveedorRepository;
import mx.com.sharkit.service.dto.OfertaProveedorDTO;
import mx.com.sharkit.service.mapper.OfertaProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OfertaProveedor}.
 */
@Service
@Transactional
public class OfertaProveedorServiceImpl implements OfertaProveedorService {

    private final Logger log = LoggerFactory.getLogger(OfertaProveedorServiceImpl.class);

    private final OfertaProveedorRepository ofertaProveedorRepository;

    private final OfertaProveedorMapper ofertaProveedorMapper;

    public OfertaProveedorServiceImpl(OfertaProveedorRepository ofertaProveedorRepository, OfertaProveedorMapper ofertaProveedorMapper) {
        this.ofertaProveedorRepository = ofertaProveedorRepository;
        this.ofertaProveedorMapper = ofertaProveedorMapper;
    }

    /**
     * Save a ofertaProveedor.
     *
     * @param ofertaProveedorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OfertaProveedorDTO save(OfertaProveedorDTO ofertaProveedorDTO) {
        log.debug("Request to save OfertaProveedor : {}", ofertaProveedorDTO);
        OfertaProveedor ofertaProveedor = ofertaProveedorMapper.toEntity(ofertaProveedorDTO);
        ofertaProveedor = ofertaProveedorRepository.save(ofertaProveedor);
        return ofertaProveedorMapper.toDto(ofertaProveedor);
    }

    /**
     * Get all the ofertaProveedors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OfertaProveedorDTO> findAll() {
        log.debug("Request to get all OfertaProveedors");
        return ofertaProveedorRepository.findAll().stream()
            .map(ofertaProveedorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ofertaProveedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfertaProveedorDTO> findOne(Long id) {
        log.debug("Request to get OfertaProveedor : {}", id);
        return ofertaProveedorRepository.findById(id)
            .map(ofertaProveedorMapper::toDto);
    }

    /**
     * Delete the ofertaProveedor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfertaProveedor : {}", id);
        ofertaProveedorRepository.deleteById(id);
    }
}
