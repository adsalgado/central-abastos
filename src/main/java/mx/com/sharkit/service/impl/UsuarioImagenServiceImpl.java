package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.UsuarioImagenService;
import mx.com.sharkit.domain.UsuarioImagen;
import mx.com.sharkit.repository.UsuarioImagenRepository;
import mx.com.sharkit.service.dto.UsuarioImagenDTO;
import mx.com.sharkit.service.mapper.UsuarioImagenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UsuarioImagen}.
 */
@Service
@Transactional
public class UsuarioImagenServiceImpl implements UsuarioImagenService {

    private final Logger log = LoggerFactory.getLogger(UsuarioImagenServiceImpl.class);

    private final UsuarioImagenRepository usuarioImagenRepository;

    private final UsuarioImagenMapper usuarioImagenMapper;

    public UsuarioImagenServiceImpl(UsuarioImagenRepository usuarioImagenRepository, UsuarioImagenMapper usuarioImagenMapper) {
        this.usuarioImagenRepository = usuarioImagenRepository;
        this.usuarioImagenMapper = usuarioImagenMapper;
    }

    /**
     * Save a usuarioImagen.
     *
     * @param usuarioImagenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsuarioImagenDTO save(UsuarioImagenDTO usuarioImagenDTO) {
        log.debug("Request to save UsuarioImagen : {}", usuarioImagenDTO);
        UsuarioImagen usuarioImagen = usuarioImagenMapper.toEntity(usuarioImagenDTO);
        usuarioImagen = usuarioImagenRepository.save(usuarioImagen);
        return usuarioImagenMapper.toDto(usuarioImagen);
    }

    /**
     * Get all the usuarioImagens.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioImagenDTO> findAll() {
        log.debug("Request to get all UsuarioImagens");
        return usuarioImagenRepository.findAll().stream()
            .map(usuarioImagenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one usuarioImagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioImagenDTO> findOne(Long id) {
        log.debug("Request to get UsuarioImagen : {}", id);
        return usuarioImagenRepository.findById(id)
            .map(usuarioImagenMapper::toDto);
    }

    /**
     * Delete the usuarioImagen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsuarioImagen : {}", id);
        usuarioImagenRepository.deleteById(id);
    }
}
