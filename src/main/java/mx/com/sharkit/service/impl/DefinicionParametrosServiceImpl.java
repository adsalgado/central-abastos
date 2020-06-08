package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.DefinicionParametros;
import mx.com.sharkit.repository.DefinicionParametrosRepository;
import mx.com.sharkit.service.DefinicionParametrosService;
import mx.com.sharkit.service.dto.DefinicionParametrosDTO;
import mx.com.sharkit.service.mapper.DefinicionParametrosMapper;

/**
 * Service Implementation for managing {@link DefinicionParametros}.
 */
@Service
@Transactional
public class DefinicionParametrosServiceImpl extends BaseServiceImpl<DefinicionParametros, Integer>
		implements DefinicionParametrosService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(DefinicionParametrosServiceImpl.class);

	private final DefinicionParametrosRepository definicionParametrosRepository;

	private final DefinicionParametrosMapper definicionParametrosMapper;

	public DefinicionParametrosServiceImpl(DefinicionParametrosRepository definicionParametrosRepository,
			DefinicionParametrosMapper definicionParametrosMapper) {
		this.definicionParametrosRepository = definicionParametrosRepository;
		this.definicionParametrosMapper = definicionParametrosMapper;
	}

	/**
	 * Save a definicionParametros.
	 *
	 * @param definicionParametrosDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public DefinicionParametrosDTO save(DefinicionParametrosDTO definicionParametrosDTO) {
		log.debug("Request to save DefinicionParametros : {}", definicionParametrosDTO);
		DefinicionParametros definicionParametros = definicionParametrosMapper.toEntity(definicionParametrosDTO);
		definicionParametros = definicionParametrosRepository.save(definicionParametros);
		return definicionParametrosMapper.toDto(definicionParametros);
	}

	/**
	 * Get all the definicionParametross.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DefinicionParametrosDTO> findAllDTO() {
		log.debug("Request to get all DefinicionParametross");
		return definicionParametrosRepository.findAll().stream().map(definicionParametrosMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one definicionParametros by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DefinicionParametrosDTO> findOne(Integer id) {
		log.debug("Request to get DefinicionParametros : {}", id);
		return definicionParametrosRepository.findById(id).map(definicionParametrosMapper::toDto);
	}

	/**
	 * Delete the definicionParametros by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Integer id) {
		log.debug("Request to delete DefinicionParametros : {}", id);
		definicionParametrosRepository.deleteById(id);
	}

	@Override
	public List<Map<String, Object>> findAllByQueryNativeToMap(String sSql) {
		return this.findAllByQueryNativeToMap(sSql);
	}

	@Override
	public <TEntity> List<TEntity> findAllByQueryNativeToMap(Class<TEntity> eClazz, String sSql) {
		return this.findAllByQueryNativeToMap(eClazz, sSql);
	}

}
