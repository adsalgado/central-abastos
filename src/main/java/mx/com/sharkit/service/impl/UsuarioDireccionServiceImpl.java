package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.UsuarioDireccion;
import mx.com.sharkit.repository.UsuarioDireccionRepository;
import mx.com.sharkit.service.UsuarioDireccionService;
import mx.com.sharkit.service.dto.UsuarioDireccionDTO;
import mx.com.sharkit.service.mapper.UsuarioDireccionMapper;

/**
 * Service Implementation for managing {@link UsuarioDireccion}.
 */
@Service
@Transactional
public class UsuarioDireccionServiceImpl implements UsuarioDireccionService {

    private final Logger log = LoggerFactory.getLogger(UsuarioDireccionServiceImpl.class);

    private final UsuarioDireccionRepository usuarioDireccionRepository;

    private final UsuarioDireccionMapper usuarioDireccionMapper;

    public UsuarioDireccionServiceImpl(UsuarioDireccionRepository usuarioDireccionRepository, UsuarioDireccionMapper usuarioDireccionMapper) {
        this.usuarioDireccionRepository = usuarioDireccionRepository;
        this.usuarioDireccionMapper = usuarioDireccionMapper;
    }

    /**
     * Save a usuarioDireccion.
     *
     * @param usuarioDireccionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsuarioDireccionDTO save(UsuarioDireccionDTO usuarioDireccionDTO) {
        log.debug("Request to save UsuarioDireccion : {}", usuarioDireccionDTO);
        UsuarioDireccion usuarioDireccion = usuarioDireccionMapper.toEntity(usuarioDireccionDTO);
        usuarioDireccion = usuarioDireccionRepository.save(usuarioDireccion);
        return usuarioDireccionMapper.toDto(usuarioDireccion);
    }

    /**
     * Get all the usuarioDireccions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDireccionDTO> findAll() {
        log.debug("Request to get all UsuarioDireccions");
        return usuarioDireccionRepository.findAll().stream()
            .map(usuarioDireccionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one usuarioDireccion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDireccionDTO> findOne(Long id) {
        log.debug("Request to get UsuarioDireccion : {}", id);
        return usuarioDireccionRepository.findById(id)
            .map(usuarioDireccionMapper::toDto);
    }

    /**
     * Delete the usuarioDireccion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsuarioDireccion : {}", id);
        usuarioDireccionRepository.deleteById(id);
    }

	@Override
	public List<UsuarioDireccionDTO> findByUsuarioId(Long usuarioId) {
        log.debug("Request to get all UsuarioDireccions by usuarioId: {}", usuarioId);
        return usuarioDireccionRepository.findByUsuarioId(usuarioId).stream()
            .map(usuarioDireccionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
 
	}
}
