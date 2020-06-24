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

import mx.com.sharkit.domain.ProveedorTarifa;
import mx.com.sharkit.repository.ProveedorTarifaRepository;
import mx.com.sharkit.service.ProveedorTarifaService;
import mx.com.sharkit.service.dto.ProveedorTarifaDTO;
import mx.com.sharkit.service.mapper.ProveedorTarifaMapper;

/**
 * Service Implementation for managing {@link ProveedorTarifa}.
 */
@Service
@Transactional
public class ProveedorTarifaServiceImpl implements ProveedorTarifaService {

	private final Logger log = LoggerFactory.getLogger(ProveedorTarifaServiceImpl.class);

	private final ProveedorTarifaRepository proveedorTarifaRepository;

	private final ProveedorTarifaMapper proveedorTarifaMapper;

	public ProveedorTarifaServiceImpl(ProveedorTarifaRepository proveedorTarifaRepository,
			ProveedorTarifaMapper proveedorTarifaMapper) {
		this.proveedorTarifaRepository = proveedorTarifaRepository;
		this.proveedorTarifaMapper = proveedorTarifaMapper;
	}

	/**
	 * Save a proveedorTarifa.
	 *
	 * @param proveedorTarifaDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProveedorTarifaDTO save(ProveedorTarifaDTO proveedorTarifaDTO) {
		log.debug("Request to save ProveedorTarifa : {}", proveedorTarifaDTO);
		ProveedorTarifa proveedorTarifa = proveedorTarifaMapper.toEntity(proveedorTarifaDTO);
		proveedorTarifa = proveedorTarifaRepository.save(proveedorTarifa);
		return proveedorTarifaMapper.toDto(proveedorTarifa);
	}

	/**
	 * Get all the proveedorTarifas.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorTarifaDTO> findAll() {
		log.debug("Request to get all ProveedorTarifas");
		return proveedorTarifaRepository.findAll().stream().map(proveedorTarifaMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one proveedorTarifa by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ProveedorTarifaDTO> findOne(Long id) {
		log.debug("Request to get ProveedorTarifa : {}", id);
		return proveedorTarifaRepository.findById(id).map(proveedorTarifaMapper::toDto);
	}

	/**
	 * Delete the proveedorTarifa by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ProveedorTarifa : {}", id);
		proveedorTarifaRepository.deleteById(id);
	}

	@Override
	public BigDecimal calculaTarifaProveedor(Long proveedorId, BigDecimal valor) {
		BigDecimal tarifa = BigDecimal.ZERO;
		if (proveedorId != null) {
			List<ProveedorTarifaDTO> lstTarifas = proveedorTarifaRepository
					.findByRango(valor, valor).stream()
					.map(proveedorTarifaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

			if (!lstTarifas.isEmpty()) {
				tarifa = lstTarifas.get(0).getPorcentajeComision();
			}
		}
		return tarifa;

	}

	@Override
	public List<ProveedorTarifaDTO> findAllOrderByRangoMinimo() {
		log.debug("Request to get all ProveedorTarifas by transportistaId: {}");
		return proveedorTarifaRepository.findAllOrderByRangoMinimo().stream()
				.map(proveedorTarifaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}
}
