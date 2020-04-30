package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.TipoDireccion;
import mx.com.sharkit.repository.TipoDireccionRepository;
import mx.com.sharkit.service.TipoDireccionService;
import mx.com.sharkit.service.dto.TipoDireccionDTO;
import mx.com.sharkit.service.mapper.TipoDireccionMapper;

/**
 * Service Implementation for managing {@link TipoDireccion}.
 */
@Service
@Transactional
public class TipoDireccionServiceImpl extends BaseServiceImpl<TipoDireccion, Long>
		implements TipoDireccionService {

	private final Logger log = LoggerFactory.getLogger(TipoDireccionServiceImpl.class);

	private final TipoDireccionRepository tipoDireccionRepository;

	private final TipoDireccionMapper tipoDireccionMapper;

	public TipoDireccionServiceImpl(TipoDireccionRepository tipoDireccionRepository,
			TipoDireccionMapper tipoDireccionMapper) {
		this.tipoDireccionRepository = tipoDireccionRepository;
		this.tipoDireccionMapper = tipoDireccionMapper;
	}

	/**
	 * Save a tipoDireccion.
	 *
	 * @param tipoDireccionDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public TipoDireccionDTO save(TipoDireccionDTO tipoDireccionDTO) {
		log.debug("Request to save TipoDireccion : {}", tipoDireccionDTO);
		TipoDireccion tipoDireccion = tipoDireccionMapper.toEntity(tipoDireccionDTO);
		tipoDireccion = tipoDireccionRepository.save(tipoDireccion);
		return tipoDireccionMapper.toDto(tipoDireccion);
	}

	/**
	 * Get all the tipoDireccions.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TipoDireccionDTO> findAllDTO() {
		log.debug("Request to get all TipoDireccions");
		return tipoDireccionRepository.findAll().stream().map(tipoDireccionMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one tipoDireccion by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TipoDireccionDTO> findOne(Long id) {
		log.debug("Request to get TipoDireccion : {}", id);
		return tipoDireccionRepository.findById(id).map(tipoDireccionMapper::toDto);
	}

	/**
	 * Delete the tipoDireccion by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete TipoDireccion : {}", id);
		tipoDireccionRepository.deleteById(id);
	}


}
