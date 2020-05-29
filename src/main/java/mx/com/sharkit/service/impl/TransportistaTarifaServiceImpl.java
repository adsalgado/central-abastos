package mx.com.sharkit.service.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.TransportistaTarifa;
import mx.com.sharkit.repository.TransportistaTarifaRepository;
import mx.com.sharkit.service.TransportistaTarifaService;
import mx.com.sharkit.service.dto.TransportistaTarifaDTO;
import mx.com.sharkit.service.mapper.TransportistaTarifaMapper;

/**
 * Service Implementation for managing {@link TransportistaTarifa}.
 */
@Service
@Transactional
public class TransportistaTarifaServiceImpl implements TransportistaTarifaService {

	private final Logger log = LoggerFactory.getLogger(TransportistaTarifaServiceImpl.class);

	private final TransportistaTarifaRepository transportistaTarifaRepository;

	private final TransportistaTarifaMapper transportistaTarifaMapper;

	public TransportistaTarifaServiceImpl(TransportistaTarifaRepository transportistaTarifaRepository,
			TransportistaTarifaMapper transportistaTarifaMapper) {
		this.transportistaTarifaRepository = transportistaTarifaRepository;
		this.transportistaTarifaMapper = transportistaTarifaMapper;
	}

	/**
	 * Save a transportistaTarifa.
	 *
	 * @param transportistaTarifaDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public TransportistaTarifaDTO save(TransportistaTarifaDTO transportistaTarifaDTO) {
		log.debug("Request to save TransportistaTarifa : {}", transportistaTarifaDTO);
		TransportistaTarifa transportistaTarifa = transportistaTarifaMapper.toEntity(transportistaTarifaDTO);
		transportistaTarifa = transportistaTarifaRepository.save(transportistaTarifa);
		return transportistaTarifaMapper.toDto(transportistaTarifa);
	}

	/**
	 * Get all the transportistaTarifas.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TransportistaTarifaDTO> findAll() {
		log.debug("Request to get all TransportistaTarifas");
		return transportistaTarifaRepository.findAll().stream().map(transportistaTarifaMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one transportistaTarifa by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TransportistaTarifaDTO> findOne(Long id) {
		log.debug("Request to get TransportistaTarifa : {}", id);
		return transportistaTarifaRepository.findById(id).map(transportistaTarifaMapper::toDto);
	}

	/**
	 * Delete the transportistaTarifa by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete TransportistaTarifa : {}", id);
		transportistaTarifaRepository.deleteById(id);
	}

	@Override
	public BigDecimal calculaTarifaTransportista(Long transportistaId, BigDecimal valor) {
		BigDecimal tarifa = BigDecimal.ZERO;
		if (transportistaId != null) {
			List<TransportistaTarifaDTO> lstTarifas = transportistaTarifaRepository
					.findTarifaTransportista(transportistaId, valor, valor).stream()
					.map(transportistaTarifaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

			if (!lstTarifas.isEmpty()) {
				tarifa = lstTarifas.get(0).getPrecio();
			}
		}
		return tarifa;
	}

	/**
	 * Get all the transportistaTarifas by transportistaId.
	 *
	 * @param transportistaId
	 * @return the list of entities.
	 */
	@Override
	public List<TransportistaTarifaDTO> findAllByTransportistaId(Long transportistaId) {
		log.debug("Request to get all TransportistaTarifas by transportistaId: {}");
		return transportistaTarifaRepository.findByTransportistaIdOrderByRangoMinimo(transportistaId).stream()
				.map(transportistaTarifaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}
}
