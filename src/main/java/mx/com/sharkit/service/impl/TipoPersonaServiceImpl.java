package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.TipoPersona;
import mx.com.sharkit.repository.TipoPersonaRepository;
import mx.com.sharkit.service.TipoPersonaService;
import mx.com.sharkit.service.dto.TipoPersonaDTO;
import mx.com.sharkit.service.mapper.TipoPersonaMapper;

/**
 * Service Implementation for managing {@link TipoPersona}.
 */
@Service
@Transactional
public class TipoPersonaServiceImpl extends BaseServiceImpl<TipoPersona, Long>
		implements TipoPersonaService {

	private final Logger log = LoggerFactory.getLogger(TipoPersonaServiceImpl.class);

	private final TipoPersonaRepository tipoPersonaRepository;

	private final TipoPersonaMapper tipoPersonaMapper;

	public TipoPersonaServiceImpl(TipoPersonaRepository tipoPersonaRepository,
			TipoPersonaMapper tipoPersonaMapper) {
		this.tipoPersonaRepository = tipoPersonaRepository;
		this.tipoPersonaMapper = tipoPersonaMapper;
	}

	/**
	 * Save a tipoPersona.
	 *
	 * @param tipoPersonaDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public TipoPersonaDTO save(TipoPersonaDTO tipoPersonaDTO) {
		log.debug("Request to save TipoPersona : {}", tipoPersonaDTO);
		TipoPersona tipoPersona = tipoPersonaMapper.toEntity(tipoPersonaDTO);
		tipoPersona = tipoPersonaRepository.save(tipoPersona);
		return tipoPersonaMapper.toDto(tipoPersona);
	}

	/**
	 * Get all the tipoPersonas.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TipoPersonaDTO> findAllDTO() {
		log.debug("Request to get all TipoPersonas");
		return tipoPersonaRepository.findAll().stream().map(tipoPersonaMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one tipoPersona by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TipoPersonaDTO> findOne(Long id) {
		log.debug("Request to get TipoPersona : {}", id);
		return tipoPersonaRepository.findById(id).map(tipoPersonaMapper::toDto);
	}

	/**
	 * Delete the tipoPersona by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete TipoPersona : {}", id);
		tipoPersonaRepository.deleteById(id);
	}


}
