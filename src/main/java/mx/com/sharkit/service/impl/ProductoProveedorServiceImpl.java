package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.repository.ProductoImagenRepository;
import mx.com.sharkit.repository.ProductoProveedorRepository;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.dto.ProductoImagenDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.mapper.ProductoImagenMapper;
import mx.com.sharkit.service.mapper.ProductoProveedorMapper;

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


	public ProductoProveedorServiceImpl(ProductoProveedorRepository productoProveedorRepository,
			ProductoProveedorMapper productoProveedorMapper,
			ProductoImagenRepository productoImagenRepository, ProductoImagenMapper productoImagenMapper) {
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
		Optional<ProductoProveedorDTO> optDTO = productoProveedorRepository.findById(id).map(productoProveedorMapper::toDto);
		ProductoProveedorDTO productoDTO = optDTO.isPresent() ? optDTO.get() : null;
		if (productoDTO != null) {
			List<AdjuntoDTO> adjuntos = productoImagenRepository.findByProductoProveedorIdOrderByIdAsc(productoDTO.getId())
					.stream().map(productoImagenMapper::toDto).map(ProductoImagenDTO::getAdjunto)
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
				criteria.add(Restrictions.eq("proveedorId", Long.parseLong((String)params.get(key))));
				break;
			case "categoriaId":
				criteria.add(Restrictions.eq("categoria.id", Long.parseLong((String)params.get(key))));
				break;
			case "seccionId":
				criteria.add(Restrictions.eq("seccion.id", Long.parseLong((String)params.get(key))));
				break;
			case "tipoArticuloId":
				criteria.add(Restrictions.eq("producto.tipoArticuloId", Long.parseLong((String)params.get(key))));
				break;
			case "nombre":
				criteria.add(Restrictions.ilike("producto.nombre", (String)params.get(key), MatchMode.ANYWHERE));
				break;
			default:
				break;
			}

		});
		return this.findByCriteria(criteria).stream().map(productoProveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}
}
