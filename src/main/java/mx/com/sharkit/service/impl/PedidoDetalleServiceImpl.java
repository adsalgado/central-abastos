package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.PedidoDetalleService;
import mx.com.sharkit.domain.PedidoDetalle;
import mx.com.sharkit.repository.PedidoDetalleRepository;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;
import mx.com.sharkit.service.mapper.PedidoDetalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PedidoDetalle}.
 */
@Service
@Transactional
public class PedidoDetalleServiceImpl implements PedidoDetalleService {

    private final Logger log = LoggerFactory.getLogger(PedidoDetalleServiceImpl.class);

    private final PedidoDetalleRepository pedidoDetalleRepository;

    private final PedidoDetalleMapper pedidoDetalleMapper;

    public PedidoDetalleServiceImpl(PedidoDetalleRepository pedidoDetalleRepository, PedidoDetalleMapper pedidoDetalleMapper) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoDetalleMapper = pedidoDetalleMapper;
    }

    /**
     * Save a pedidoDetalle.
     *
     * @param pedidoDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PedidoDetalleDTO save(PedidoDetalleDTO pedidoDetalleDTO) {
        log.debug("Request to save PedidoDetalle : {}", pedidoDetalleDTO);
        PedidoDetalle pedidoDetalle = pedidoDetalleMapper.toEntity(pedidoDetalleDTO);
        pedidoDetalle = pedidoDetalleRepository.save(pedidoDetalle);
        return pedidoDetalleMapper.toDto(pedidoDetalle);
    }

    /**
     * Get all the pedidoDetalles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoDetalleDTO> findAll() {
        log.debug("Request to get all PedidoDetalles");
        return pedidoDetalleRepository.findAll().stream()
            .map(pedidoDetalleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pedidoDetalle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDetalleDTO> findOne(Long id) {
        log.debug("Request to get PedidoDetalle : {}", id);
        return pedidoDetalleRepository.findById(id)
            .map(pedidoDetalleMapper::toDto);
    }

    /**
     * Delete the pedidoDetalle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PedidoDetalle : {}", id);
        pedidoDetalleRepository.deleteById(id);
    }
    
    /**
     * Get all the pedidoDetalles by pedidoProveedorId.
     *
     * @param pedidoProveedorId
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoDetalleDTO> findByPedidoProveedorId(Long pedidoProveedorId) {
        log.debug("Request to get all PedidoDetalles");
        return pedidoDetalleRepository.findByPedidoProveedorId(pedidoProveedorId).stream()
            .map(pedidoDetalleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
