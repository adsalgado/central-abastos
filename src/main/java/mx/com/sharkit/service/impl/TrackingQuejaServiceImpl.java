package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.TrackingQuejaService;
import mx.com.sharkit.domain.TrackingQueja;
import mx.com.sharkit.repository.TrackingQuejaRepository;
import mx.com.sharkit.repository.UserRepository;
import mx.com.sharkit.service.dto.TrackingQuejaDTO;
import mx.com.sharkit.service.mapper.TrackingQuejaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TrackingQueja}.
 */
@Service
@Transactional
public class TrackingQuejaServiceImpl implements TrackingQuejaService {

    private final Logger log = LoggerFactory.getLogger(TrackingQuejaServiceImpl.class);

    private final TrackingQuejaRepository trackingQuejaRepository;

    private final TrackingQuejaMapper trackingQuejaMapper;

    private final UserRepository userRepository;

    public TrackingQuejaServiceImpl(TrackingQuejaRepository trackingQuejaRepository, TrackingQuejaMapper trackingQuejaMapper, UserRepository userRepository) {
        this.trackingQuejaRepository = trackingQuejaRepository;
        this.trackingQuejaMapper = trackingQuejaMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a trackingQueja.
     *
     * @param trackingQuejaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrackingQuejaDTO save(TrackingQuejaDTO trackingQuejaDTO) {
        TrackingQueja trackingQueja = trackingQuejaMapper.toEntity(trackingQuejaDTO);
        trackingQueja = trackingQuejaRepository.save(trackingQueja);
        return trackingQuejaMapper.toDto(trackingQueja);
    }

    /**
     * Get all the trackingQuejas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrackingQuejaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrackingQuejas");
        return trackingQuejaRepository.findAll(pageable)
            .map(trackingQuejaMapper::toDto);
    }


    /**
     * Get one trackingQueja by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrackingQuejaDTO> findOne(Long id) {
        log.debug("Request to get TrackingQueja : {}", id);
        return trackingQuejaRepository.findById(id)
            .map(trackingQuejaMapper::toDto);
    }

    /**
     * Delete the trackingQueja by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrackingQueja : {}", id);
        trackingQuejaRepository.deleteById(id);
    }
}
