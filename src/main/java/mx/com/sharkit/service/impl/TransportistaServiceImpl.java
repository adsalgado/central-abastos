package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.TransportistaService;
import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.repository.TransportistaRepository;
import mx.com.sharkit.service.dto.TransportistaDTO;
import mx.com.sharkit.service.mapper.TransportistaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Transportista}.
 */
@Service
@Transactional
public class TransportistaServiceImpl implements TransportistaService {

    private final Logger log = LoggerFactory.getLogger(TransportistaServiceImpl.class);

    private final TransportistaRepository transportistaRepository;

    private final TransportistaMapper transportistaMapper;

    public TransportistaServiceImpl(TransportistaRepository transportistaRepository, TransportistaMapper transportistaMapper) {
        this.transportistaRepository = transportistaRepository;
        this.transportistaMapper = transportistaMapper;
    }

    /**
     * Save a transportista.
     *
     * @param transportistaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransportistaDTO save(TransportistaDTO transportistaDTO) {
        log.debug("Request to save Transportista : {}", transportistaDTO);
        Transportista transportista = transportistaMapper.toEntity(transportistaDTO);
        transportista = transportistaRepository.save(transportista);
        return transportistaMapper.toDto(transportista);
    }

    /**
     * Get all the transportistas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransportistaDTO> findAll() {
        log.debug("Request to get all Transportistas");
        return transportistaRepository.findAll().stream()
            .map(transportistaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transportista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransportistaDTO> findOne(Long id) {
        log.debug("Request to get Transportista : {}", id);
        return transportistaRepository.findById(id)
            .map(transportistaMapper::toDto);
    }

    /**
     * Delete the transportista by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transportista : {}", id);
        transportistaRepository.deleteById(id);
    }

	@Override
	public Optional<TransportistaDTO> findOneByusuarioId(Long usuarioId) {
		log.debug("Request to get Transportista by usuarioId : {}", usuarioId);
        return transportistaRepository.findOneByusuarioId(usuarioId)
            .map(transportistaMapper::toDto);
	}
}
