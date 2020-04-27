package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.repository.ProductoProveedorRepository;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.mapper.ProductoProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public ProductoProveedorServiceImpl(ProductoProveedorRepository productoProveedorRepository,
			ProductoProveedorMapper productoProveedorMapper) {
		this.productoProveedorRepository = productoProveedorRepository;
		this.productoProveedorMapper = productoProveedorMapper;
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
		return productoProveedorRepository.findById(id).map(productoProveedorMapper::toDto);
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
}
