package mx.com.sharkit.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.repository.ProductoRepository;
import mx.com.sharkit.service.ProductoService;
import mx.com.sharkit.service.dto.ProductoDTO;
import mx.com.sharkit.service.mapper.ProductoMapper;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

	private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

	private final ProductoRepository productoRepository;

	private final ProductoMapper productoMapper;

	public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
		this.productoRepository = productoRepository;
		this.productoMapper = productoMapper;
	}

	/**
	 * Save a producto.
	 *
	 * @param productoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProductoDTO save(ProductoDTO productoDTO) {
		log.debug("Request to save Producto : {}", productoDTO);
		Producto producto = productoMapper.toEntity(productoDTO);
		producto = productoRepository.save(producto);
		return productoMapper.toDto(producto);
	}

	/**
	 * Get all the productos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ProductoDTO> findAll() {
		log.debug("Request to get all Productos");
		return productoRepository.findAll().stream().map(productoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one producto by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ProductoDTO> findOne(Long id) {
		log.debug("Request to get Producto : {}", id);
		return productoRepository.findById(id).map(productoMapper::toDto);
	}

	/**
	 * Delete the producto by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Producto : {}", id);
		productoRepository.deleteById(id);
	}

	@Override
	public List<ProductoDTO> searchProductos(Map<String, Object> params, Pageable pageable) {
		
		productoRepository.findAll();
		Page<Producto> pageProductos = productoRepository.findAll(new Specification<Producto>() {

			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				params.keySet().forEach(key -> {
					switch (key) {
					case "proveedorId":
						predicates.add(
								criteriaBuilder.and(criteriaBuilder.equal(root.get("proveedorId"), params.get(key))));
						break;
					case "categoriaId":
						predicates.add(
								criteriaBuilder.and(criteriaBuilder.equal(root.get("categoriaId"), params.get(key))));
						break;
					case "seccionId":
						predicates.add(
								criteriaBuilder.and(criteriaBuilder.equal(root.get("seccionId"), params.get(key))));
						break;
					case "tipoArticuloId":
						predicates.add(
								criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoArticuloId"), params.get(key))));
						break;
					case "nombre":
						predicates.add(criteriaBuilder
								.and(criteriaBuilder.like(root.get("nombre"), "%" + params.get(key) + "%")));
						break;
					default:
						break;
					}

				});

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);

		List<Producto> lstProductos = pageProductos.getContent();
		return lstProductos.stream().map(productoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}
}
