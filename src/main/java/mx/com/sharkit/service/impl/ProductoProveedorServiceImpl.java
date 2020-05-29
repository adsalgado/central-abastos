package mx.com.sharkit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Inventario;
import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.excel.objectbinding.domain.ProductoCargaDTO;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.repository.InventarioRepository;
import mx.com.sharkit.repository.ProductoImagenRepository;
import mx.com.sharkit.repository.ProductoProveedorRepository;
import mx.com.sharkit.repository.ProductoRepository;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.dto.ProductoImagenDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.mapper.ProductoImagenMapper;
import mx.com.sharkit.service.mapper.ProductoProveedorMapper;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * Service Implementation for managing {@link ProductoProveedor}.
 */
@Service
@Transactional
public class ProductoProveedorServiceImpl extends BaseServiceImpl<ProductoProveedor, Long>
		implements ProductoProveedorService {

	private final Logger log = LoggerFactory.getLogger(ProductoProveedorServiceImpl.class);

	private final ProductoProveedorRepository productoProveedorRepository;

	private final ProductoProveedorMapper productoProveedorMapper;

	private final ProductoImagenMapper productoImagenMapper;

	private final ProductoImagenRepository productoImagenRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private InventarioRepository inventarioRepository;

	@Autowired
	private AdjuntoRepository adjuntoRepository;

	public ProductoProveedorServiceImpl(ProductoProveedorRepository productoProveedorRepository,
			ProductoProveedorMapper productoProveedorMapper, ProductoImagenRepository productoImagenRepository,
			ProductoImagenMapper productoImagenMapper) {
		this.productoProveedorRepository = productoProveedorRepository;
		this.productoProveedorMapper = productoProveedorMapper;
		this.productoImagenRepository = productoImagenRepository;
		this.productoImagenMapper = productoImagenMapper;
	}

	/**
	 * Save a productoProveedor.
	 *
	 * @param productoProveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProductoProveedorDTO save(ProductoProveedorDTO productoProveedorDTO) {
		log.debug("Request to save ProductoProveedor : {}", productoProveedorDTO);
		ProductoProveedor productoProveedor = productoProveedorMapper.toEntity(productoProveedorDTO);
		productoProveedor = productoProveedorRepository.save(productoProveedor);
		return productoProveedorMapper.toDto(productoProveedor);
	}

	/**
	 * Get all the productoProveedors.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ProductoProveedorDTO> findAllDTO() {
		log.debug("Request to get all ProductoProveedors");
		return productoProveedorRepository.findAll().stream().map(productoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one productoProveedor by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ProductoProveedorDTO> findOne(Long id) {
		log.debug("Request to get ProductoProveedor : {}", id);
		Optional<ProductoProveedorDTO> optDTO = productoProveedorRepository.findById(id)
				.map(productoProveedorMapper::toDto);
		ProductoProveedorDTO productoDTO = optDTO.isPresent() ? optDTO.get() : null;
		if (productoDTO != null) {
			List<AdjuntoDTO> adjuntos = productoImagenRepository
					.findByProductoProveedorIdOrderByIdAsc(productoDTO.getId()).stream()
					.map(productoImagenMapper::toDto).map(ProductoImagenDTO::getAdjunto)
					.collect(Collectors.toCollection(LinkedList::new));
			productoDTO.setImagenes(adjuntos);
		}
		return optDTO;
	}

	/**
	 * Delete the productoProveedor by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ProductoProveedor : {}", id);
		productoProveedorRepository.deleteById(id);
	}

	@Override
	public List<ProductoProveedorDTO> searchProductos(Map<String, Object> params, Integer page, Integer pagesize,
			Order order) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ProductoProveedor.class);
		criteria.createAlias("producto", "producto");
		criteria.createAlias("producto.tipoArticulo", "tipoArticulo");
		criteria.createAlias("tipoArticulo.categoria", "categoria");
		criteria.createAlias("categoria.seccion", "seccion");

		params.keySet().forEach(key -> {
			switch (key) {
			case "proveedorId":
				criteria.add(Restrictions.eq("proveedorId", Long.parseLong((String) params.get(key))));
				break;
			case "categoriaId":
				criteria.add(Restrictions.eq("categoria.id", Long.parseLong((String) params.get(key))));
				break;
			case "seccionId":
				criteria.add(Restrictions.eq("seccion.id", Long.parseLong((String) params.get(key))));
				break;
			case "tipoArticuloId":
				criteria.add(Restrictions.eq("producto.tipoArticuloId", Long.parseLong((String) params.get(key))));
				break;
			case "nombre":
				criteria.add(Restrictions.ilike("producto.nombre", (String) params.get(key), MatchMode.ANYWHERE));
				break;
			case "productoId":
				criteria.add(Restrictions.eq("productoId", Long.parseLong((String) params.get(key))));
				break;
			default:
				break;
			}

		});
		return this.findByCriteria(criteria).stream().map(productoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}

	@Override
	public void cargaMasivaProductosProveedor(List<ProductoCargaDTO> productosCarga, ProveedorDTO proveedor)
			throws Exception {

		LocalDateTime now = LocalDateTime.now();
		productosCarga.forEach(prodCarga -> {

			if (prodCarga.getErrors() != null && prodCarga.getErrors().hasErrors()) {
				return;
			}

			Producto producto = null;
			ProductoProveedor productoProveedor = null;
			Inventario inventario = null;

			if (!StringUtils.isAllBlank(prodCarga.getSku())) {
				producto = productoRepository.findOneBySku(prodCarga.getSku()).orElse(null);
			}

			if (producto != null && !StringUtils.isAllBlank(prodCarga.getNombre())) {
				producto = productoRepository.findOneBySku(prodCarga.getSku()).orElse(null);
			}

			if (producto == null) {
				producto = new Producto();
				producto.setSku(prodCarga.getSku());
				producto.setNombre(prodCarga.getNombre());
				producto.setDescripcion(prodCarga.getDescripcion());
				producto.setPrecio(prodCarga.getPrecio());
				producto.setPrecioSinIva(prodCarga.getPrecio());
				producto.setTipoArticuloId(prodCarga.getTipoArticuloId());
				producto.setUnidadMedidaId(prodCarga.getUnidadMedidaId());
				producto.setEstatusId(Estatus.ACTIVO);
				producto.setFechaAlta(now);
				producto.setUsuarioAltaId(proveedor.getUsuarioId());

				producto = productoRepository.save(producto);
			} else {
				productoProveedor = productoProveedorRepository
						.findOneByProveedorIdAndProductoId(proveedor.getId(), producto.getId()).orElse(null);
			}

			if (productoProveedor == null) {
				productoProveedor = new ProductoProveedor();
				productoProveedor.setProveedorId(proveedor.getId());
				productoProveedor.setProductoId(producto.getId());
				productoProveedor.setPrecio(producto.getPrecio());
				productoProveedor.setPrecioSinIva(producto.getPrecioSinIva());
				productoProveedor.setEstatusId(Estatus.ACTIVO);
				productoProveedor.setFechaAlta(now);
				productoProveedor.setUsuarioAltaId(proveedor.getId());

				productoProveedor = productoProveedorRepository.save(productoProveedor);
			}

			inventario = inventarioRepository.findOneByProductoProveedorId(productoProveedor.getId()).orElse(null);
			if (inventario == null) {
				inventario = new Inventario();
				inventario.setProductoProveedorId(productoProveedor.getId());
				inventario.setTotal(prodCarga.getInventario());
				inventario = inventarioRepository.save(inventario);
			} else {
				inventario.setTotal(prodCarga.getInventario());
			}

		});

	}

	@Override
	public List<ProductoProveedorDTO> findByProveedorId(Long proveedorId) {
		log.debug("Request to get all ProductoProveedors by proveedorId: {}", proveedorId);
		return productoProveedorRepository.findByProveedorId(proveedorId).stream().map(productoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public ProductoProveedorDTO saveProductoProveedor(@Valid ProductoProveedorDTO productoProveedorDTO) {
		log.debug("Request to save ProductoProveedor : {}", productoProveedorDTO);

		Producto producto = null;
		ProductoProveedor productoProveedor = null;
		Inventario inventario = null;

		LocalDateTime now = LocalDateTime.now();

		if (!StringUtils.isAllBlank(productoProveedorDTO.getProducto().getSku())) {
			producto = productoRepository.findOneBySku(productoProveedorDTO.getProducto().getSku()).orElse(null);
		}

		if (producto != null && !StringUtils.isAllBlank(productoProveedorDTO.getProducto().getNombre())) {
			producto = productoRepository.findOneBySku(productoProveedorDTO.getProducto().getSku()).orElse(null);
		}

		if (producto == null) {
			producto = new Producto();
			producto.setSku(productoProveedorDTO.getProducto().getSku());
			producto.setNombre(productoProveedorDTO.getProducto().getNombre());
			producto.setDescripcion(productoProveedorDTO.getProducto().getDescripcion());
			producto.setPrecio(productoProveedorDTO.getPrecio());
			producto.setPrecioSinIva(productoProveedorDTO.getPrecio());
			producto.setTipoArticuloId(productoProveedorDTO.getProducto().getTipoArticuloId());
			producto.setUnidadMedidaId(productoProveedorDTO.getProducto().getUnidadMedidaId());
			producto.setEstatusId(Estatus.ACTIVO);
			producto.setFechaAlta(now);
			producto.setUsuarioAltaId(productoProveedorDTO.getProveedorId());

			producto = productoRepository.save(producto);
		} else {
			productoProveedor = productoProveedorRepository
					.findOneByProveedorIdAndProductoId(productoProveedorDTO.getProveedorId(), producto.getId())
					.orElse(null);
		}
		
		if (productoProveedorDTO.getProducto().getAdjunto() != null) {
			Adjunto adjunto = new Adjunto();
			adjunto.setFileName(productoProveedorDTO.getProducto().getAdjunto().getFileName());
			adjunto.setFile(productoProveedorDTO.getProducto().getAdjunto().getFile());
			adjunto.setContentType(productoProveedorDTO.getProducto().getAdjunto().getContentType());
			adjunto.setFileContentType(productoProveedorDTO.getProducto().getAdjunto().getFileContentType());
			adjunto.setSize(productoProveedorDTO.getProducto().getAdjunto().getSize());
			adjuntoRepository.save(adjunto);
			producto.setAdjuntoId(adjunto.getId());
		}

		if (productoProveedor == null) {
			productoProveedor = new ProductoProveedor();
			productoProveedor.setProveedorId(productoProveedorDTO.getProveedorId());
			productoProveedor.setProductoId(producto.getId());
			productoProveedor.setPrecio(producto.getPrecio());
			productoProveedor.setPrecioSinIva(producto.getPrecioSinIva());
			productoProveedor.setEstatusId(Estatus.ACTIVO);
			productoProveedor.setFechaAlta(now);
			productoProveedor.setUsuarioAltaId(productoProveedorDTO.getProveedorId());

			productoProveedor = productoProveedorRepository.save(productoProveedor);
		}

		inventario = inventarioRepository.findOneByProductoProveedorId(productoProveedor.getId()).orElse(null);
		if (inventario == null) {
			inventario = new Inventario();
			inventario.setProductoProveedorId(productoProveedor.getId());
			inventario.setTotal(BigDecimal.ZERO);
			inventario = inventarioRepository.save(inventario);
		}

		return productoProveedorMapper.toDto(productoProveedor);
	}

	@Override
	public ProductoProveedorDTO updateProductoProveedor(@Valid ProductoProveedorDTO productoProveedorDTO) {
		log.debug("Request to update ProductoProveedor : {}", productoProveedorDTO);

		ProductoProveedor productoProveedor = productoProveedorRepository.findById(productoProveedorDTO.getId())
				.orElse(null);
		if (productoProveedor != null) {
			Producto producto = productoProveedor.getProducto();
			producto.setSku(productoProveedorDTO.getProducto().getSku());
			producto.setNombre(productoProveedorDTO.getProducto().getNombre());
			producto.setDescripcion(productoProveedorDTO.getProducto().getDescripcion());
			producto.setCaracteristicas(productoProveedorDTO.getProducto().getCaracteristicas());
			producto.setUnidadMedidaId(productoProveedorDTO.getProducto().getUnidadMedidaId());
			producto.setTipoArticuloId(productoProveedorDTO.getProducto().getTipoArticuloId());
			
			productoProveedor.setPrecio(productoProveedorDTO.getPrecio());
			productoProveedor.setPrecioSinIva(productoProveedorDTO.getPrecio());
			productoProveedor.setEstatusId(productoProveedorDTO.getEstatusId());
			
			if (productoProveedorDTO.getProducto().getAdjunto() != null) {
				Adjunto adjunto = null;
				if (productoProveedorDTO.getProducto().getAdjunto().getId() != null) {
					adjunto = adjuntoRepository.findById(productoProveedorDTO.getProducto().getAdjunto().getId()).orElse(null);
					if (adjunto != null) {
						adjunto.setFileName(productoProveedorDTO.getProducto().getAdjunto().getFileName());
						adjunto.setFile(productoProveedorDTO.getProducto().getAdjunto().getFile());
						adjunto.setContentType(productoProveedorDTO.getProducto().getAdjunto().getContentType());
						adjunto.setFileContentType(productoProveedorDTO.getProducto().getAdjunto().getFileContentType());
						adjunto.setSize(productoProveedorDTO.getProducto().getAdjunto().getSize());
					}
				}

				if (adjunto == null) {
					adjunto = new Adjunto();
					adjunto.setFileName(productoProveedorDTO.getProducto().getAdjunto().getFileName());
					adjunto.setFile(productoProveedorDTO.getProducto().getAdjunto().getFile());
					adjunto.setContentType(productoProveedorDTO.getProducto().getAdjunto().getContentType());
					adjunto.setFileContentType(productoProveedorDTO.getProducto().getAdjunto().getFileContentType());
					adjunto.setSize(productoProveedorDTO.getProducto().getAdjunto().getSize());
					adjuntoRepository.save(adjunto);
				}

				producto.setAdjuntoId(adjunto.getId());
			}
			
		}

		return productoProveedorMapper.toDto(productoProveedor);
	}
}
