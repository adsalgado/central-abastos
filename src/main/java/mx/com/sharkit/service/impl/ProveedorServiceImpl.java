package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.ProveedorService;
import mx.com.sharkit.domain.Proveedor;
import mx.com.sharkit.repository.ProveedorRepository;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.mapper.ProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link Proveedor}.
 */
@Service
@Transactional
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor, Long> implements ProveedorService {

	private final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

	private final ProveedorRepository proveedorRepository;

	private final ProveedorMapper proveedorMapper;

	public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
		this.proveedorRepository = proveedorRepository;
		this.proveedorMapper = proveedorMapper;
	}

	/**
	 * Save a proveedor.
	 *
	 * @param proveedorDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProveedorDTO save(ProveedorDTO proveedorDTO) {
		log.debug("Request to save Proveedor : {}", proveedorDTO);
		Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
		proveedor = proveedorRepository.save(proveedor);
		return proveedorMapper.toDto(proveedor);
	}

	/**
	 * Get all the proveedors.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorDTO> findAllDTO() {
		log.debug("Request to get all Proveedors");
		return proveedorRepository.findAll().stream().map(proveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one proveedor by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ProveedorDTO> findOne(Long id) {
		log.debug("Request to get Proveedor : {}", id);
		return proveedorRepository.findById(id).map(proveedorMapper::toDto);
	}

	/**
	 * Delete the proveedor by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Proveedor : {}", id);
		proveedorRepository.deleteById(id);
	}

	@Override
	public List<ProveedorDTO> findAllByProductoId(Long productoId) {
		log.debug("Request to get all Proveedors by productoId: {}", productoId);
		return proveedorRepository.findByProductoId(productoId).stream().map(proveedorMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
     * Get the "id" proveedor by usuarioId.
     *
     * @param usuarioId the usuarioId of the entity.
     * @return the entity.
     */
	@Override
	public Optional<ProveedorDTO> findOneByusuarioId(Long usuarioId) {
		log.debug("Request to get Proveedor : {}", usuarioId);
		return proveedorRepository.findOneByUsuarioId(usuarioId).map(proveedorMapper::toDto);
	}

    /**
     * Get the proveedor by userName.
     *
     * @param userName the userName of the user.
     * @return the entity.
     */
	@Override
	public Optional<ProveedorDTO> findOneByUserName(String userName) {
		log.debug("Request to get Proveedor by userName : {}", userName);
		return proveedorRepository.findOneByUserName(userName).map(proveedorMapper::toDto);
	}

	/**
     * Update the transportista of a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */    
	@Override
	public ProveedorDTO updateTransportistaProveedor(@Valid ProveedorDTO proveedorDTO) {
		Proveedor proveedor = proveedorRepository.findById(proveedorDTO.getId()).orElse(null);
		if (proveedor != null) {
			proveedor.setNombre(proveedorDTO.getNombre());
			proveedor.setTransportistaId(proveedorDTO.getTransportistaId());
		}
		return proveedorMapper.toDto(proveedor);
	}
}
