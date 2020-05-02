package mx.com.sharkit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Pedido;
import mx.com.sharkit.repository.PedidoRepository;
import mx.com.sharkit.service.PedidoDetalleService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.dto.PedidoAltaDTO;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.dto.PedidoDetalleAltaDTO;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.dto.UserDTO;
import mx.com.sharkit.service.mapper.PedidoMapper;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

	private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	private final PedidoRepository pedidoRepository;

	private final PedidoMapper pedidoMapper;

	private final ProductoProveedorService productoProveedorService;

	private final PedidoProveedorService pedidoProveedorService;

	private final PedidoDetalleService pedidoDetalleService;

	public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			ProductoProveedorService productoProveedorService, PedidoProveedorService pedidoProveedorService,
			PedidoDetalleService pedidoDetalleService) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.productoProveedorService = productoProveedorService;
		this.pedidoProveedorService = pedidoProveedorService;
		this.pedidoDetalleService = pedidoDetalleService;
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
		pedidoDTO.setEstatusId(Estatus.ESTATUS_PEDIDO_SOLICITADO);
		pedidoDTO.setClienteId(pedidoAltaDTO.getUsuarioId());
		pedidoDTO.setFechaAlta(fechaAlta);
		pedidoDTO.setUsuarioAltaId(pedidoAltaDTO.getUsuarioId());
		pedidoDTO.setTotal(BigDecimal.ZERO);
		pedidoDTO.setTotalSinIva(BigDecimal.ZERO);
		pedidoDTO.setComisionTransportista(BigDecimal.ZERO);
		pedidoDTO.setComisionPreparador(BigDecimal.ZERO);


		for (PedidoDetalleAltaDTO prod : pedidoAltaDTO.getProductos()) {
			
			Optional<ProductoProveedorDTO> prodProvDTO = productoProveedorService
					.findOne(prod.getProductoProveedorId());
			
			if (prodProvDTO.isPresent()) {
				ProductoProveedorDTO prodProdDTO = prodProvDTO.get();
				ProveedorDTO proveedorDTO = prodProdDTO.getProveedor();
				
				if (!mapProveedores.containsKey(proveedorDTO.getId())) { // Ya se dio de alta el proveedor
					PedidoProveedorDTO pedidoProveedorDTO = new PedidoProveedorDTO();
					pedidoProveedorDTO.setEstatusId(Estatus.ESTATUS_PEDIDO_SOLICITADO);
					pedidoProveedorDTO.setTotal(BigDecimal.ZERO);
					pedidoProveedorDTO.setTotalSinIva(BigDecimal.ZERO);
					pedidoProveedorDTO.setComisionTransportista(BigDecimal.ZERO);
					pedidoProveedorDTO.setComisionPreparador(BigDecimal.ZERO);
					pedidoProveedorDTO.setFechaAlta(fechaAlta);
					pedidoProveedorDTO.setUsuarioAltaId(pedidoAltaDTO.getUsuarioId());
					pedidoProveedorDTO.setProveedorId(proveedorDTO.getId());
					
					pedidoDTO.getPedidoProveedores().add(pedidoProveedorDTO);
					mapProveedores.put(proveedorDTO.getId(), pedidoProveedorDTO);
					sumaProveedor.put(proveedorDTO.getId(), BigDecimal.ZERO);
					sumaSinIvaProveedor.put(proveedorDTO.getId(), BigDecimal.ZERO);

				}
					
				PedidoProveedorDTO pedidoProveedor = mapProveedores.get(proveedorDTO.getId());
				
				PedidoDetalleDTO pedidoDetalleDTO = new PedidoDetalleDTO();
				pedidoDetalleDTO.setCantidad(prod.getCantidad());
				pedidoDetalleDTO.setEstatusId(Estatus.ESTATUS_PEDIDO_SOLICITADO);
				pedidoDetalleDTO.setPrecio(prodProdDTO.getPrecio());
				pedidoDetalleDTO.setPrecioSinIva(prodProdDTO.getPrecioSinIva());
				pedidoDetalleDTO.setProductoProveedorId(prod.getProductoProveedorId());
				pedidoDetalleDTO.setTotal(prodProdDTO.getPrecio().multiply(prod.getCantidad()));
				pedidoDetalleDTO.setTotalSinIva(prodProdDTO.getPrecioSinIva().multiply(prod.getCantidad()));
				
				BigDecimal totalProveedor = sumaProveedor.get(proveedorDTO.getId()).add(pedidoDetalleDTO.getTotal());
				BigDecimal totalSinIvaProveedor = sumaSinIvaProveedor.get(proveedorDTO.getId()).add(pedidoDetalleDTO.getTotalSinIva());
				
				sumaProveedor.put(proveedorDTO.getId(), totalProveedor);
				sumaSinIvaProveedor.put(proveedorDTO.getId(), totalSinIvaProveedor);
				
				total = total.add(totalProveedor);
				totalSinIva = totalSinIva.add(totalSinIvaProveedor);
				
				pedidoProveedor.getPedidoDetalles().add(pedidoDetalleDTO);
				
			} else {
				throw new Exception("No se encontró el producto en la base.");
			}

		}
		
		UserDTO user = new UserDTO();
		user.setId(pedidoDTO.getClienteId());
		pedidoDTO.setCliente(user);
		pedidoDTO.setTotal(total);
		pedidoDTO.setTotalSinIva(totalSinIva);
		PedidoDTO pedido = this.save(pedidoDTO);
		
		Long pedidoId = pedido.getId();
		pedidoDTO.getPedidoProveedores().forEach(pedProv -> {
			pedProv.setPedidoId(pedidoId);
			pedProv.setTotal(sumaProveedor.get(pedProv.getProveedorId()));
			pedProv.setTotalSinIva(sumaSinIvaProveedor.get(pedProv.getProveedorId()));
			
			PedidoProveedorDTO pedProvSaved = pedidoProveedorService.save(pedProv);
			pedido.getPedidoProveedores().add(pedProvSaved);
			
			pedProv.getPedidoDetalles().forEach(pedDet -> {
				pedDet.setPedidoProveedorId(pedProvSaved.getId());
				PedidoDetalleDTO pedidoDetalle = pedidoDetalleService.save(pedDet);		
				pedProvSaved.getPedidoDetalles().add(pedidoDetalle);
			});

		});
		
//		pedido.setFolio("P" + StringUtils.leftPad(pedido.getId().toString(), 9, "0"));
//		pedido = this.save(pedido);
//		
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
    
}
