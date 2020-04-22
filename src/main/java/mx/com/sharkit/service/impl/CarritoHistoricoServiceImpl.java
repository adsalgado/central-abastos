package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.CarritoHistoricoService;
import mx.com.sharkit.domain.CarritoHistorico;
import mx.com.sharkit.repository.CarritoHistoricoRepository;
import mx.com.sharkit.service.dto.CarritoHistoricoDTO;
import mx.com.sharkit.service.mapper.CarritoHistoricoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CarritoHistorico}.
 */
@Service
@Transactional
public class CarritoHistoricoServiceImpl implements CarritoHistoricoService {

    private final Logger log = LoggerFactory.getLogger(CarritoHistoricoServiceImpl.class);

    private final CarritoHistoricoRepository carritoHistoricoRepository;

    private final CarritoHistoricoMapper carritoHistoricoMapper;

    public CarritoHistoricoServiceImpl(CarritoHistoricoRepository carritoHistoricoRepository, CarritoHistoricoMapper carritoHistoricoMapper) {
        this.carritoHistoricoRepository = carritoHistoricoRepository;
        this.carritoHistoricoMapper = carritoHistoricoMapper;
    }

    /**
     * Save a carritoHistorico.
     *
     * @param carritoHistoricoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarritoHistoricoDTO save(CarritoHistoricoDTO carritoHistoricoDTO) {
        log.debug("Request to save CarritoHistorico : {}", carritoHistoricoDTO);
        CarritoHistorico carritoHistorico = carritoHistoricoMapper.toEntity(carritoHistoricoDTO);
        carritoHistorico = carritoHistoricoRepository.save(carritoHistorico);
        return carritoHistoricoMapper.toDto(carritoHistorico);
    }

    /**
     * Get all the carritoHistoricos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoHistoricoDTO> findAll() {
        log.debug("Request to get all CarritoHistoricos");
        return carritoHistoricoRepository.findAll().stream()
            .map(carritoHistoricoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carritoHistorico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarritoHistoricoDTO> findOne(Long id) {
        log.debug("Request to get CarritoHistorico : {}", id);
        return carritoHistoricoRepository.findById(id)
            .map(carritoHistoricoMapper::toDto);
    }

    /**
     * Delete the carritoHistorico by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarritoHistorico : {}", id);
        carritoHistoricoRepository.deleteById(id);
    }
}
