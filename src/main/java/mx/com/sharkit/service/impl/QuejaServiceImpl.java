package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.QuejaService;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Queja;
import mx.com.sharkit.domain.enumeration.TipoEstatus;
import mx.com.sharkit.repository.EstatusRepository;
import mx.com.sharkit.repository.QuejaRepository;
import mx.com.sharkit.service.dto.QuejaDTO;
import mx.com.sharkit.service.dto.TrackingQuejaDTO;
import mx.com.sharkit.service.mapper.QuejaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Queja}.
 */
@Service
@Transactional
public class QuejaServiceImpl extends BaseServiceImpl<Queja, Long> implements QuejaService {

    private final Logger log = LoggerFactory.getLogger(QuejaServiceImpl.class);

    private final QuejaRepository quejaRepository;

    private final QuejaMapper quejaMapper;
    
    private final EstatusRepository statusRepository;
    
    

    public QuejaServiceImpl(QuejaRepository quejaRepository, QuejaMapper quejaMapper, 
    						EstatusRepository estatusRepository) {
        this.quejaRepository = quejaRepository;
        this.quejaMapper = quejaMapper;
        
        this.statusRepository = estatusRepository;
    }

    /**
     * Save a queja.
     *
     * @param quejaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuejaDTO save(QuejaDTO quejaDTO) {
        log.debug("Request to save Queja : {}", quejaDTO);
        Queja queja = quejaMapper.toEntity(quejaDTO);
        queja = quejaRepository.save(queja);
        return quejaMapper.toDto(queja);
    }

    /**
     * Get all the quejas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuejaDTO> findAllDTO() {
        log.debug("Request to get all Quejas");
        return quejaRepository.findAll().stream()
            .map(quejaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one queja by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuejaDTO> findOne(Long id) {
        log.debug("Request to get Queja : {}", id);
        return quejaRepository.findById(id)
            .map(quejaMapper::toDto);
    }

    /**
     * Delete the queja by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Queja : {}", id);
        quejaRepository.deleteById(id);
    }
    
    
    @Override
    public QuejaDTO changeStatus(Long quejaId, String newStatus){
    	 Optional<Queja> queja = quejaRepository.findById(quejaId);
    	 Optional<Estatus> estatus = statusRepository.findStatusByTipoEstatusAndNombre("ESTATUS_QUEJA", newStatus);
    	 if(queja.isPresent()) {
    		 Queja quejaObject =queja.get();
    		 quejaObject.setEstatus(estatus.get());
    		 quejaObject.setEstatusId(estatus.get().getId());
    		 quejaObject = quejaRepository.save(quejaObject);
    		 return quejaMapper.toDto(quejaObject);
    	 }
    	 return null;
    }
}
