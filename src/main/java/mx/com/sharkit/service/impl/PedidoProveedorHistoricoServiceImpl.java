package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.domain.PedidoProveedorHistorico;
import mx.com.sharkit.repository.PedidoProveedorHistoricoRepository;
import mx.com.sharkit.service.PedidoHistoricoAsyncService;
import mx.com.sharkit.service.PedidoProveedorHistoricoService;
import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;
import mx.com.sharkit.service.mapper.PedidoProveedorHistoricoMapper;

/**
 * Service Implementation for managing {@link PedidoProveedorHistorico}.
 */
@Service
@Transactional
public class PedidoProveedorHistoricoServiceImpl extends BaseServiceImpl<PedidoProveedorHistorico, Long>
		implements PedidoProveedorHistoricoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1324521931582720668L;

	private final Logger log = LoggerFactory.getLogger(PedidoProveedorHistoricoServiceImpl.class);

	private final PedidoProveedorHistoricoRepository pedidoProveedorHistoricoRepository;

	private final PedidoProveedorHistoricoMapper pedidoProveedorHistoricoMapper;
	
	@Autowired
    TaskExecutor taskExecutor;

	public PedidoProveedorHistoricoServiceImpl(PedidoProveedorHistoricoRepository pedidoProveedorHistoricoRepository,
			PedidoProveedorHistoricoMapper pedidoProveedorHistoricoMapper) {
		this.pedidoProveedorHistoricoRepository = pedidoProveedorHistoricoRepository;
		this.pedidoProveedorHistoricoMapper = pedidoProveedorHistoricoMapper;
	}

	/**
	 * Save a pedidoProveedorHistorico.
	 *
	 * @param pedidoProveedorHistoricoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PedidoProveedorHistoricoDTO save(PedidoProveedorHistoricoDTO pedidoProveedorHistoricoDTO) {
		log.debug("Request to save PedidoProveedorHistorico : {}", pedidoProveedorHistoricoDTO);
		PedidoProveedorHistorico pedidoProveedorHistorico = pedidoProveedorHistoricoMapper
				.toEntity(pedidoProveedorHistoricoDTO);
		pedidoProveedorHistorico = pedidoProveedorHistoricoRepository.save(pedidoProveedorHistorico);
		return pedidoProveedorHistoricoMapper.toDto(pedidoProveedorHistorico);
	}

	/**
	 * Get all the pedidoProveedorHistoricos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PedidoProveedorHistoricoDTO> findAllDTO() {
		log.debug("Request to get all PedidoProveedorHistoricos");
		return pedidoProveedorHistoricoRepository.findAll().stream().map(pedidoProveedorHistoricoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one pedidoProveedorHistorico by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PedidoProveedorHistoricoDTO> findOne(Long id) {
		log.debug("Request to get PedidoProveedorHistorico : {}", id);
		return pedidoProveedorHistoricoRepository.findById(id).map(pedidoProveedorHistoricoMapper::toDto);
	}

	/**
	 * Delete the pedidoProveedorHistorico by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete PedidoProveedorHistorico : {}", id);
		pedidoProveedorHistoricoRepository.deleteById(id);
	}

	@Override
	public List<PedidoProveedorHistoricoDTO> findByPedidoProveedorId(Long pedidoProveedorId) {
		log.debug("Request to get all PedidoProveedorHistoricos");
		return pedidoProveedorHistoricoRepository.findByPedidoProveedorId(pedidoProveedorId).stream().map(pedidoProveedorHistoricoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<PedidoProveedorHistoricoDTO> findByPedidoProveedorIdOrderByFecha(Long pedidoProveedorId) {
		log.debug("Request to get all PedidoProveedorHistoricos");
		return pedidoProveedorHistoricoRepository.findByPedidoProveedorIdOrderByFecha(pedidoProveedorId).stream().map(pedidoProveedorHistoricoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void savePedidoProveedorHistorico(PedidoProveedor pedidoProveedor) {
		PedidoProveedorHistoricoDTO historicoDTO = new PedidoProveedorHistoricoDTO();
		historicoDTO.setPedidoProveedorId(pedidoProveedor.getId());
		historicoDTO.setEstatusId(pedidoProveedor.getEstatusId());
		historicoDTO.setUsuarioId(pedidoProveedor.getUsuarioModificacionId());		
		historicoDTO.setFecha(pedidoProveedor.getFechaModificacion());		
		taskExecutor.execute(() -> this.save(historicoDTO));
	}

}
