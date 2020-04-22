package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.TarjetaService;
import mx.com.sharkit.domain.Tarjeta;
import mx.com.sharkit.repository.TarjetaRepository;
import mx.com.sharkit.service.dto.TarjetaDTO;
import mx.com.sharkit.service.mapper.TarjetaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Tarjeta}.
 */
@Service
@Transactional
public class TarjetaServiceImpl implements TarjetaService {

    private final Logger log = LoggerFactory.getLogger(TarjetaServiceImpl.class);

    private final TarjetaRepository tarjetaRepository;

    private final TarjetaMapper tarjetaMapper;

    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository, TarjetaMapper tarjetaMapper) {
        this.tarjetaRepository = tarjetaRepository;
        this.tarjetaMapper = tarjetaMapper;
    }

    /**
     * Save a tarjeta.
     *
     * @param tarjetaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TarjetaDTO save(TarjetaDTO tarjetaDTO) {
        log.debug("Request to save Tarjeta : {}", tarjetaDTO);
        Tarjeta tarjeta = tarjetaMapper.toEntity(tarjetaDTO);
        tarjeta = tarjetaRepository.save(tarjeta);
        return tarjetaMapper.toDto(tarjeta);
    }

    /**
     * Get all the tarjetas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TarjetaDTO> findAll() {
        log.debug("Request to get all Tarjetas");
        return tarjetaRepository.findAll().stream()
            .map(tarjetaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tarjeta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TarjetaDTO> findOne(Long id) {
        log.debug("Request to get Tarjeta : {}", id);
        return tarjetaRepository.findById(id)
            .map(tarjetaMapper::toDto);
    }

    /**
     * Delete the tarjeta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarjeta : {}", id);
        tarjetaRepository.deleteById(id);
    }
}
