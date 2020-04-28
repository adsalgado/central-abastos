package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.CarritoCompra;
import mx.com.sharkit.repository.CarritoCompraRepository;
import mx.com.sharkit.service.CarritoCompraService;
import mx.com.sharkit.service.dto.CarritoCompraDTO;
import mx.com.sharkit.service.mapper.CarritoCompraMapper;

/**
 * Service Implementation for managing {@link CarritoCompra}.
 */
@Service
@Transactional
public class CarritoCompraServiceImpl implements CarritoCompraService {

    private final Logger log = LoggerFactory.getLogger(CarritoCompraServiceImpl.class);

    private final CarritoCompraRepository carritoCompraRepository;

    private final CarritoCompraMapper carritoCompraMapper;

    public CarritoCompraServiceImpl(CarritoCompraRepository carritoCompraRepository, CarritoCompraMapper carritoCompraMapper) {
        this.carritoCompraRepository = carritoCompraRepository;
        this.carritoCompraMapper = carritoCompraMapper;
    }

    /**
     * Save a carritoCompra.
     *
     * @param carritoCompraDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarritoCompraDTO save(CarritoCompraDTO carritoCompraDTO) {
        log.debug("Request to save CarritoCompra : {}", carritoCompraDTO);
        CarritoCompra carritoCompra = carritoCompraMapper.toEntity(carritoCompraDTO);
        carritoCompra = carritoCompraRepository.save(carritoCompra);
        return carritoCompraMapper.toDto(carritoCompra);
    }

    /**
     * Get all the carritoCompras.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoCompraDTO> findAll() {
        log.debug("Request to get all CarritoCompras");
        return carritoCompraRepository.findAll().stream()
            .map(carritoCompraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carritoCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarritoCompraDTO> findOne(Long id) {
        log.debug("Request to get CarritoCompra : {}", id);
        return carritoCompraRepository.findById(id)
            .map(carritoCompraMapper::toDto);
    }

    /**
     * Delete the carritoCompra by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarritoCompra : {}", id);
        carritoCompraRepository.deleteById(id);
    }

    /**
     * Get all the carritoCompras of clienteId.
     *
     * @return the list of entities.
     */
    @Override
	public List<CarritoCompraDTO> findAllByClienteId(Long clienteId) {
		log.debug("Request to get all CarritoCompras");
        return carritoCompraRepository.findByClienteIdOrderByNombre(clienteId).stream()
            .map(carritoCompraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void deleteByClienteIdAnProductoProveedorId(Long clienteId, Long productoId) {
		log.debug("Request to delete CarritoCompra : {}, {}", clienteId, productoId);
        carritoCompraRepository.deleteByClienteIdAndProductoProveedorId(clienteId, productoId);
	}

	@Override
	public void deleteByClienteId(Long clienteId) {
		log.debug("Request to delete CarritoCompra : {}, {}", clienteId);
        carritoCompraRepository.deleteByClienteId(clienteId);
	}

	@Override
	public Optional<CarritoCompraDTO> findOneClienteIdAndProductoProveedorId(Long clienteId, Long productoId) {
		log.debug("Request to get CarritoCompra : cte {}, prod {}", clienteId, productoId);
        return carritoCompraRepository.findOneByClienteIdAndProductoProveedorId(clienteId, productoId)
            .map(carritoCompraMapper::toDto);
	}
	
}
