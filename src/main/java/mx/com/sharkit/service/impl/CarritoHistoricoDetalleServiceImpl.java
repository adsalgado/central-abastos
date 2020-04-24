package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.CarritoHistoricoDetalleService;
import mx.com.sharkit.domain.CarritoHistoricoDetalle;
import mx.com.sharkit.repository.CarritoHistoricoDetalleRepository;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;
import mx.com.sharkit.service.mapper.CarritoHistoricoDetalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CarritoHistoricoDetalle}.
 */
@Service
@Transactional
public class CarritoHistoricoDetalleServiceImpl implements CarritoHistoricoDetalleService {

    private final Logger log = LoggerFactory.getLogger(CarritoHistoricoDetalleServiceImpl.class);

    private final CarritoHistoricoDetalleRepository carritoHistoricoDetalleRepository;

    private final CarritoHistoricoDetalleMapper carritoHistoricoDetalleMapper;

    public CarritoHistoricoDetalleServiceImpl(CarritoHistoricoDetalleRepository carritoHistoricoDetalleRepository, CarritoHistoricoDetalleMapper carritoHistoricoDetalleMapper) {
        this.carritoHistoricoDetalleRepository = carritoHistoricoDetalleRepository;
        this.carritoHistoricoDetalleMapper = carritoHistoricoDetalleMapper;
    }

    /**
     * Save a carritoHistoricoDetalle.
     *
     * @param carritoHistoricoDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarritoHistoricoDetalleDTO save(CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO) {
        log.debug("Request to save CarritoHistoricoDetalle : {}", carritoHistoricoDetalleDTO);
        CarritoHistoricoDetalle carritoHistoricoDetalle = carritoHistoricoDetalleMapper.toEntity(carritoHistoricoDetalleDTO);
        carritoHistoricoDetalle = carritoHistoricoDetalleRepository.save(carritoHistoricoDetalle);
        return carritoHistoricoDetalleMapper.toDto(carritoHistoricoDetalle);
    }

    /**
     * Get all the carritoHistoricoDetalles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoHistoricoDetalleDTO> findAll() {
        log.debug("Request to get all CarritoHistoricoDetalles");
        return carritoHistoricoDetalleRepository.findAll().stream()
            .map(carritoHistoricoDetalleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carritoHistoricoDetalle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarritoHistoricoDetalleDTO> findOne(Long id) {
        log.debug("Request to get CarritoHistoricoDetalle : {}", id);
        return carritoHistoricoDetalleRepository.findById(id)
            .map(carritoHistoricoDetalleMapper::toDto);
    }

    /**
     * Delete the carritoHistoricoDetalle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarritoHistoricoDetalle : {}", id);
        carritoHistoricoDetalleRepository.deleteById(id);
    }

	@Override
	public List<CarritoHistoricoDetalleDTO> saveAll(List<CarritoHistoricoDetalleDTO> lstCarritoHistoricoDetalleDTO) {
        
		List<CarritoHistoricoDetalle> lstCarrito = lstCarritoHistoricoDetalleDTO.stream()
				.map(dto -> carritoHistoricoDetalleMapper.toEntity(dto))
				.collect(Collectors.toList());
		
		return carritoHistoricoDetalleRepository.saveAll(lstCarrito).stream()
	            .map(carritoHistoricoDetalleMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
		
	}

	@Override
	public List<CarritoHistoricoDetalleDTO> findByCarritoHistoricoId(Long carritoHistoricoId) {
		log.debug("Request to get all CarritoHistoricoDetalles by carritoHistoricoId");
        return carritoHistoricoDetalleRepository.findByCarritoHistoricoId(carritoHistoricoId).stream()
            .map(carritoHistoricoDetalleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
	}
	
}
