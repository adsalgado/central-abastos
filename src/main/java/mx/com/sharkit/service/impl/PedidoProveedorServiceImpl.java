package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.repository.PedidoProveedorRepository;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.mapper.PedidoProveedorMapper;

/**
 * Service Implementation for managing {@link PedidoProveedor}.
 */
@Service
@Transactional
public class PedidoProveedorServiceImpl extends BaseServiceImpl<PedidoProveedor, Long>
		implements PedidoProveedorService {

	private final Logger log = LoggerFactory.getLogger(PedidoProveedorServiceImpl.class);

	private final PedidoProveedorRepository pedidoProveedorRepository;

	private final PedidoProveedorMapper pedidoProveedorMapper;

	public PedidoProveedorServiceImpl(PedidoProveedorRepository pedidoProveedorRepository,
			PedidoProveedorMapper pedidoProveedorMapper) {
		this.pedidoProveedorRepository = pedidoProveedorRepository;
		this.pedidoProveedorMapper = pedidoProveedorMapper;
	}

	/**
	 * Save a pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PedidoProveedorDTO save(PedidoProveedorDTO pedidoProveedorDTO) {
		log.debug("Request to save PedidoProveedor : {}", pedidoProveedorDTO);
		PedidoProveedor pedidoProveedor = pedidoProveedorMapper.toEntity(pedidoProveedorDTO);
		pedidoProveedor = pedidoProveedorRepository.save(pedidoProveedor);
		return pedidoProveedorMapper.toDto(pedidoProveedor);
	}

	/**
	 * Get all the pedidoProveedors.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PedidoProveedorDTO> findAllDTO() {
		log.debug("Request to get all PedidoProveedors");
		return pedidoProveedorRepository.findAll().stream().map(pedidoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one pedidoProveedor by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PedidoProveedorDTO> findOne(Long id) {
		log.debug("Request to get PedidoProveedor : {}", id);
		return pedidoProveedorRepository.findById(id).map(pedidoProveedorMapper::toDto);
	}

	/**
	 * Delete the pedidoProveedor by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete PedidoProveedor : {}", id);
		pedidoProveedorRepository.deleteById(id);
	}


}
