package mx.com.sharkit.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Pedido;
import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.repository.PedidoDetalleRepository;
import mx.com.sharkit.repository.PedidoProveedorRepository;
import mx.com.sharkit.repository.PedidoRepository;
import mx.com.sharkit.service.PedidoDetalleService;
import mx.com.sharkit.service.PedidoProveedorHistoricoService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.dto.CalificacionPedidoProveedorDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.TerminarServicioPedidoProveedorDTO;
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

	private final PedidoDetalleService pedidoDetalleService;

	private final PedidoDetalleRepository pedidoDetalleRepository;

	private final PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoProveedorHistoricoService pedidoProveedorHistoricoService;

	public PedidoProveedorServiceImpl(PedidoProveedorRepository pedidoProveedorRepository,
			PedidoProveedorMapper pedidoProveedorMapper, PedidoDetalleService pedidoDetalleService,
			PedidoDetalleRepository pedidoDetalleRepository, PedidoRepository pedidoRepository) {
		this.pedidoProveedorRepository = pedidoProveedorRepository;
		this.pedidoProveedorMapper = pedidoProveedorMapper;
		this.pedidoDetalleService = pedidoDetalleService;
		this.pedidoDetalleRepository = pedidoDetalleRepository;
		this.pedidoRepository = pedidoRepository;
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

	/**
	 * Get all the pedidoProveedors by pedidoId.
	 *
	 * @param pedidoId
	 * @return the list of entities.
	 */
	@Override
	public List<PedidoProveedorDTO> findByPedidoId(Long pedidoId) {
		log.debug("Request to get all PedidoProveedors by pedidoId: {}", pedidoId);
		List<PedidoProveedorDTO> lstPedidoProveedor = pedidoProveedorRepository.findByPedidoId(pedidoId).stream()
				.map(pedidoProveedorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
		lstPedidoProveedor.forEach(pp -> {
			pp.setPedidoDetalles(pedidoDetalleService.findByPedidoProveedorId(pp.getId()));
		});
		return lstPedidoProveedor;
	}

	@Override
	public List<PedidoProveedorDTO> findByPedidoIdAndProveedorId(Long pedidoId, Long proveedorId) {
		log.debug("Request to get all PedidoProveedors by pedidoId: {} and proveedorId: {}", pedidoId, proveedorId);
		List<PedidoProveedorDTO> lstPedidoProveedor = pedidoProveedorRepository
				.findByPedidoIdAndProveedorId(pedidoId, proveedorId).stream().map(pedidoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		lstPedidoProveedor.forEach(pp -> {
			pp.setPedidoDetalles(pedidoDetalleService.findByPedidoProveedorId(pp.getId()));
		});
		return lstPedidoProveedor;
	}

	@Override
	public PedidoProveedorDTO cambiaEstatusPedidoProveedorAndDetalles(Long pedidoProveedorId, Long estatus,
			Long usuarioEstatus) {
		LocalDateTime now = LocalDateTime.now();
		PedidoProveedor pedidoProveedor = pedidoProveedorRepository.findById(pedidoProveedorId).orElse(null);
		if (pedidoProveedor != null) {
			pedidoDetalleRepository.findByPedidoProveedorId(pedidoProveedorId).forEach(
					pd -> pd.setEstatusId(estatus)
			);
			pedidoProveedor.setEstatusId(estatus);
			pedidoProveedor.setUsuarioModificacionId(usuarioEstatus);
			pedidoProveedor.setFechaModificacion(now);

			if (estatus.equals(Estatus.PEDIDO_PREPARADO)) {
				pedidoProveedor.setFechaPreparacion(now);
			} else if (estatus.equals(Estatus.PEDIDO_ENVIADO)) {
				pedidoProveedor.setFechaEnvio(now);
			} else if (estatus.equals(Estatus.PEDIDO_ENTREGADO)) {
				pedidoProveedor.setFechaEntrega(now);
			}
			
			pedidoProveedorHistoricoService.savePedidoProveedorHistorico(pedidoProveedor);

			List<PedidoProveedor> listPedidoPrv = pedidoProveedorRepository
					.findByPedidoId(pedidoProveedor.getPedidoId());
			Long countEstatus = listPedidoPrv.stream().filter(pp -> pp.getEstatusId().equals(estatus)).count();
			if (listPedidoPrv.size() == countEstatus.intValue()) {
				Pedido pedido = pedidoRepository.findById(pedidoProveedor.getPedidoId()).orElse(null);
				if (pedido != null) {
					pedido.setEstatusId(estatus);
				}
			}
		}
		return pedidoProveedorMapper.toDto(pedidoProveedor);
	}
	

	@Override
	public List<PedidoProveedorDTO> findByPedidoIdAndTransportistaId(Long pedidoId, Long transportistaId) {
		log.debug("Request to get all PedidoProveedors by pedidoId: {} and transportistaId: {}", pedidoId,
				transportistaId);
		List<PedidoProveedorDTO> lstPedidoProveedor = pedidoProveedorRepository
				.findByPedidoIdAndTransportistaId(pedidoId, transportistaId).stream().map(pedidoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		lstPedidoProveedor.forEach(pp -> {
			pp.setPedidoDetalles(pedidoDetalleService.findByPedidoProveedorId(pp.getId()));
		});
		return lstPedidoProveedor;

	}

	@Override
	public PedidoProveedorDTO actualizaCalificacionServicio(CalificacionPedidoProveedorDTO calificacionDTO,
			Long usuarioId) {
//		Optional<PedidoProveedor> pedidoProveedor = pedidoProveedorRepository.findById(calificacionDTO.getPedidoProveedorId()).filter(Optional::isPresent).map(Optional::get)

		PedidoProveedor pedidoProveedor = pedidoProveedorRepository.findById(calificacionDTO.getPedidoProveedorId())
				.orElse(null);
		if (pedidoProveedor != null) {
			pedidoProveedor.setCalificacionServicio(calificacionDTO.getCalificacionServicio());
			pedidoProveedor.setComentarios(calificacionDTO.getComentarios());
			pedidoProveedor.setFechaModificacion(LocalDateTime.now());
			pedidoProveedor.setUsuarioModificacionId(usuarioId);
		}

		return pedidoProveedorMapper.toDto(pedidoProveedor);
	}

	@Override
	public PedidoProveedorDTO terminarServicio(TerminarServicioPedidoProveedorDTO terminarDTO, Long usuarioId)
			throws Exception {

		PedidoProveedor pedidoProveedor = pedidoProveedorRepository.findById(terminarDTO.getPedidoProveedorId())
				.orElse(null);

		if (pedidoProveedor != null) {

			if (StringUtils.equals(pedidoProveedor.getToken(), terminarDTO.getToken())) {
				this.cambiaEstatusPedidoProveedorAndDetalles(terminarDTO.getPedidoProveedorId(),
						Estatus.PEDIDO_ENTREGADO, usuarioId);
			} else {
				throw new Exception("token inválido");
			}

		} else {
			throw new Exception("Pedido proveedor id no existe.");
		}

		return pedidoProveedorMapper.toDto(pedidoProveedor);
	}

	/**
	 * Get all the pedidoProveedors by searchParams.
	 *
	 * @param params
	 * @return the list of entities.
	 */
	@Override
	public List<PedidoProveedorDTO> searchProductos(Map<String, Object> params) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PedidoProveedor.class);
		criteria.createAlias("pedido", "pedido");
		criteria.createAlias("pedido.cliente", "cliente");
		criteria.createAlias("proveedor", "proveedor");
		criteria.createAlias("proveedor.transportista", "transportista");

		params.keySet().forEach(key -> {
			switch (key) {
			case "proveedorId":
				criteria.add(Restrictions.eq("proveedorId", Long.parseLong((String) params.get(key))));
				break;
			case "pedidoId":
				criteria.add(Restrictions.eq("pedidoId", Long.parseLong((String) params.get(key))));
				break;
			case "folio":
				criteria.add(Restrictions.eq("pedido.folio", (String) params.get(key)));
				break;
			case "clienteId":
				criteria.add(Restrictions.eq("cliente.id", Long.parseLong((String) params.get(key))));
				break;
			case "transportistaId":
				criteria.add(Restrictions.eq("transportista.id", Long.parseLong((String) params.get(key))));
				break;
			case "estatusId":
				criteria.add(Restrictions.eq("estatusId", Long.parseLong((String) params.get(key))));
				break;
			default:
				break;
			}

		});
		return this.findByCriteria(criteria).stream().map(pedidoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}

}
