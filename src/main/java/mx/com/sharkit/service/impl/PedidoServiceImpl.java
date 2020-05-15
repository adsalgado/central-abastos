package mx.com.sharkit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.model.Charge;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Pedido;
import mx.com.sharkit.domain.PedidoDetalle;
import mx.com.sharkit.domain.PedidoProveedor;
import mx.com.sharkit.repository.PedidoDetalleRepository;
import mx.com.sharkit.repository.PedidoProveedorRepository;
import mx.com.sharkit.repository.PedidoRepository;
import mx.com.sharkit.repository.UserRepository;
import mx.com.sharkit.service.DireccionService;
import mx.com.sharkit.service.GoogleService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.TransportistaService;
import mx.com.sharkit.service.dto.DireccionDTO;
import mx.com.sharkit.service.dto.PedidoAltaDTO;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.dto.PedidoDetalleAltaDTO;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.dto.UserDTO;
import mx.com.sharkit.service.mapper.PedidoDetalleMapper;
import mx.com.sharkit.service.mapper.PedidoMapper;
import mx.com.sharkit.service.mapper.PedidoProveedorMapper;
import mx.com.sharkit.service.util.RandomUtil;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

	private static final int SIZE_TOKEN_PEDIDO = 5;

	private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	private final PedidoRepository pedidoRepository;

	private final PedidoMapper pedidoMapper;

	private final PedidoProveedorMapper pedidoProveedorMapper;

	private final PedidoDetalleMapper pedidoDetalleMapper;

	private final ProductoProveedorService productoProveedorService;

	private final PedidoProveedorRepository pedidoProveedorRepository;

	private final PedidoDetalleRepository pedidoDetalleRepository;

	private final DireccionService direccionService;

	@Autowired
	private GoogleService googleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransportistaService transportistaService;

	public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			ProductoProveedorService productoProveedorService, PedidoProveedorRepository pedidoProveedorRepository,
			PedidoDetalleRepository pedidoDetalleRepository, DireccionService direccionService,
			PedidoProveedorMapper pedidoProveedorMapper, PedidoDetalleMapper pedidoDetalleMapper) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.productoProveedorService = productoProveedorService;
		this.pedidoProveedorRepository = pedidoProveedorRepository;
		this.pedidoDetalleRepository = pedidoDetalleRepository;
		this.direccionService = direccionService;
		this.pedidoProveedorMapper = pedidoProveedorMapper;
		this.pedidoDetalleMapper = pedidoDetalleMapper;
	}

	/**
	 * Save a pedido.
	 *
	 * @param pedidoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PedidoDTO save(PedidoDTO pedidoDTO) {
		log.debug("Request to save Pedido : {}", pedidoDTO);
		Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
		pedido = pedidoRepository.save(pedido);
		return pedidoMapper.toDto(pedido);
	}

	/**
	 * Get all the pedidos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PedidoDTO> findAll() {
		log.debug("Request to get all Pedidos");
		return pedidoRepository.findAll().stream().map(pedidoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one pedido by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PedidoDTO> findOne(Long id) {
		log.debug("Request to get Pedido : {}", id);
		return pedidoRepository.findById(id).map(pedidoMapper::toDto);
	}

	/**
	 * Delete the pedido by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Pedido : {}", id);
		pedidoRepository.deleteById(id);
	}

	@Override
	public PedidoDTO generaNuevoPedido(PedidoAltaDTO pedidoAltaDTO) throws Exception {

		Map<Long, PedidoProveedorDTO> mapProveedores = new HashMap<>();
		Map<Long, BigDecimal> sumaProveedor = new HashMap<>();
		Map<Long, BigDecimal> sumaSinIvaProveedor = new HashMap<>();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal totalSinIva = BigDecimal.ZERO;

		LocalDateTime fechaAlta = LocalDateTime.now();

		// Crear pedido
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setEstatusId(Estatus.PEDIDO_SOLICITADO);
		pedidoDTO.setClienteId(pedidoAltaDTO.getUsuarioId());
		pedidoDTO.setFechaAlta(fechaAlta);
		pedidoDTO.setUsuarioAltaId(pedidoAltaDTO.getUsuarioId());
		pedidoDTO.setTotal(BigDecimal.ZERO);
		pedidoDTO.setTotalSinIva(BigDecimal.ZERO);
		pedidoDTO.setComisionTransportista(BigDecimal.ZERO);
		pedidoDTO.setComisionPreparador(BigDecimal.ZERO);
		pedidoDTO.setNombreContacto(pedidoAltaDTO.getNombreContacto());
		pedidoDTO.setTelefonoContacto(pedidoAltaDTO.getTelefonoContacto());
		pedidoDTO.setCorreoContacto(pedidoAltaDTO.getCorreoContacto());

//		User userPedido = userRepository.findById(pedidoAltaDTO.getUsuarioId()).orElse(null);

		for (PedidoDetalleAltaDTO prod : pedidoAltaDTO.getProductos()) {

			Optional<ProductoProveedorDTO> prodProvDTO = productoProveedorService
					.findOne(prod.getProductoProveedorId());

			if (prodProvDTO.isPresent()) {
				ProductoProveedorDTO prodProdDTO = prodProvDTO.get();
				ProveedorDTO proveedorDTO = prodProdDTO.getProveedor();

				if (!mapProveedores.containsKey(proveedorDTO.getId())) { // Ya se dio de alta el proveedor
					PedidoProveedorDTO pedidoProveedorDTO = new PedidoProveedorDTO();
					pedidoProveedorDTO.setEstatusId(Estatus.PEDIDO_SOLICITADO);
					pedidoProveedorDTO.setTotal(BigDecimal.ZERO);
					pedidoProveedorDTO.setTotalSinIva(BigDecimal.ZERO);
					pedidoProveedorDTO.setComisionTransportista(BigDecimal.ZERO);
					pedidoProveedorDTO.setComisionPreparador(BigDecimal.ZERO);
					pedidoProveedorDTO.setFechaAlta(fechaAlta);
					pedidoProveedorDTO.setUsuarioAltaId(pedidoAltaDTO.getUsuarioId());
					pedidoProveedorDTO.setProveedorId(proveedorDTO.getId());
					pedidoProveedorDTO.setTransportistaId(proveedorDTO.getTransportistaId());

//					String direccionProveedor = String.format("%s,%s", proveedorDTO.getDireccion().getLatitud(),
//							proveedorDTO.getDireccion().getLongitud());
//					String direccionUsuario = "";
//					if (userPedido != null) {
//						direccionUsuario = String.format("%s,%s", proveedorDTO.getDireccion().getLatitud(),
//								proveedorDTO.getDireccion().getLongitud());
//
//					}
//
//					Long distanciaKm = googleService.getDistanciaKilometros(direccionProveedor, direccionUsuario);
					

					pedidoDTO.getPedidoProveedores().add(pedidoProveedorDTO);
					mapProveedores.put(proveedorDTO.getId(), pedidoProveedorDTO);
					sumaProveedor.put(proveedorDTO.getId(), BigDecimal.ZERO);
					sumaSinIvaProveedor.put(proveedorDTO.getId(), BigDecimal.ZERO);

				}

				PedidoProveedorDTO pedidoProveedor = mapProveedores.get(proveedorDTO.getId());

				PedidoDetalleDTO pedidoDetalleDTO = new PedidoDetalleDTO();
				pedidoDetalleDTO.setCantidad(prod.getCantidad());
				pedidoDetalleDTO.setEstatusId(Estatus.PEDIDO_SOLICITADO);
				pedidoDetalleDTO.setPrecio(prodProdDTO.getPrecio());
				pedidoDetalleDTO.setPrecioSinIva(prodProdDTO.getPrecioSinIva());
				pedidoDetalleDTO.setProductoProveedorId(prod.getProductoProveedorId());
				pedidoDetalleDTO.setTotal(prodProdDTO.getPrecio().multiply(prod.getCantidad()));
				pedidoDetalleDTO.setTotalSinIva(prodProdDTO.getPrecioSinIva().multiply(prod.getCantidad()));

				BigDecimal totalProveedor = sumaProveedor.get(proveedorDTO.getId()).add(pedidoDetalleDTO.getTotal());
				BigDecimal totalSinIvaProveedor = sumaSinIvaProveedor.get(proveedorDTO.getId())
						.add(pedidoDetalleDTO.getTotalSinIva());

				sumaProveedor.put(proveedorDTO.getId(), totalProveedor);
				sumaSinIvaProveedor.put(proveedorDTO.getId(), totalSinIvaProveedor);

				total = total.add(pedidoDetalleDTO.getTotalSinIva());
				totalSinIva = totalSinIva.add(pedidoDetalleDTO.getTotalSinIva());

				pedidoProveedor.getPedidoDetalles().add(pedidoDetalleDTO);

			} else {
				throw new Exception("No se encontró el producto en la base.");
			}

		}

		// Guardar dirección de contacto
		if (pedidoAltaDTO.getDireccionContacto() != null) {
			DireccionDTO direccion = pedidoAltaDTO.getDireccionContacto();
			if (direccion.getId() != null && direccion.getId() > 0L) {
				pedidoDTO.setDireccionContactoId(direccion.getId());
			} else {
				direccion.setId(null);
				DireccionDTO direccionNew = direccionService.save(direccion);
				pedidoDTO.setDireccionContactoId(direccionNew.getId());
			}
		}

		UserDTO user = new UserDTO();
		user.setId(pedidoDTO.getClienteId());
		pedidoDTO.setCliente(user);
		pedidoDTO.setTotal(total);
		pedidoDTO.setTotalSinIva(totalSinIva);

		Pedido pedidoEntity = pedidoMapper.toEntity(pedidoDTO);
		pedidoEntity = pedidoRepository.save(pedidoEntity);
		pedidoEntity.setFolio("P" + StringUtils.leftPad(pedidoEntity.getId().toString(), 9, "0"));

		PedidoDTO pedido = pedidoMapper.toDto(pedidoEntity);

		Long pedidoId = pedido.getId();
		pedidoDTO.getPedidoProveedores().forEach(pedProv -> {
			pedProv.setPedidoId(pedidoId);
			pedProv.setTotal(sumaProveedor.get(pedProv.getProveedorId()));
			pedProv.setTotalSinIva(sumaSinIvaProveedor.get(pedProv.getProveedorId()));

			String token = RandomUtil.generateToken(SIZE_TOKEN_PEDIDO);
			log.debug("Token: {}", token);
			pedProv.setToken(token);

			PedidoProveedor pedidoProveedor = pedidoProveedorMapper.toEntity(pedProv);
			pedidoProveedor = pedidoProveedorRepository.save(pedidoProveedor);
			pedidoProveedor.setFolio("PV" + StringUtils.leftPad(pedidoProveedor.getId().toString(), 10, "0"));

			PedidoProveedorDTO pedProvSaved = pedidoProveedorMapper.toDto(pedidoProveedor);
			pedido.getPedidoProveedores().add(pedProvSaved);

			pedProv.getPedidoDetalles().forEach(pedDet -> {
				pedDet.setPedidoProveedorId(pedProvSaved.getId());

				PedidoDetalle pedidoDetalle = pedidoDetalleMapper.toEntity(pedDet);
				pedidoDetalle = pedidoDetalleRepository.save(pedidoDetalle);
				pedidoDetalle.setFolio("PR" + StringUtils.leftPad(pedidoDetalle.getId().toString(), 10, "0"));

				PedidoDetalleDTO pedidoDetalleSaved = pedidoDetalleMapper.toDto(pedidoDetalle);
				pedProvSaved.getPedidoDetalles().add(pedidoDetalleSaved);
			});

		});

		return pedido;
	}

	/**
	 * Get all the pedidos by clienteId.
	 *
	 * @param clienteId
	 * @return the list of entities.
	 */
	@Override
	public List<PedidoDTO> findByClienteId(Long clienteId) {
		log.debug("Request to get all Pedidos by clienteId: {}", clienteId);
		return pedidoRepository.findByClienteId(clienteId).stream().map(pedidoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get all the pedidos by proveedorId.
	 *
	 * @param proveedorId
	 * @return the list of entities.
	 */
	@Override
	public List<PedidoDTO> findByProveedorId(Long proveedorId) {
		log.debug("Request to get all Pedidos by proveedorId: {}", proveedorId);
		return pedidoRepository.findByProveedorId(proveedorId).stream().map(pedidoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<PedidoDTO> findByTransportistaId(Long transportistaId) {
		log.debug("Request to get all Pedidos by transportistaId: {}", transportistaId);
		return pedidoRepository.findByTransportistaId(transportistaId).stream().map(pedidoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public PedidoDTO cambiaEstatusPedidoAndPedidoProveedores(Long pedidoId, Long estatus, Long usuarioEstatus) {
		LocalDateTime now = LocalDateTime.now();
		Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
		if (pedido != null) {
			pedido.setEstatusId(estatus);
			pedidoProveedorRepository.findByPedidoId(pedidoId).forEach(pp -> {
				pp.setEstatusId(estatus);
				pp.setUsuarioModificacionId(usuarioEstatus);
				pp.setFechaModificacion(now);

				pedidoDetalleRepository.findByPedidoProveedorId(pp.getId()).forEach(pd -> {
					pd.setEstatusId(estatus);
				});
			});
		}

		return pedidoMapper.toDto(pedido);
	}

	@Override
	public PedidoDTO registraPagoPedido(Long pedidoId, Charge charge, Long usuarioEstatus) {
		PedidoDTO pedidoDTO = null;
		Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
		if (pedido != null) {
			pedido.setStatusPago(charge.getStatus());
			pedido.setBalanceTransaction(charge.getBalanceTransaction());
			pedido.setChargeId(charge.getId());
			pedido.setReceiptUrl(charge.getReceiptUrl());

			pedidoDTO = cambiaEstatusPedidoAndPedidoProveedores(pedidoId, Estatus.PEDIDO_PAGADO, usuarioEstatus);
		}
		return pedidoDTO;
	}

}
