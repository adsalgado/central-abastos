package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.domain.Promocion;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.repository.PromocionRepository;
import mx.com.sharkit.service.PromocionService;
import mx.com.sharkit.service.dto.PromocionDTO;
import mx.com.sharkit.service.mapper.PromocionMapper;

/**
 * Service Implementation for managing {@link Promocion}.
 */
@Service
@Transactional
public class PromocionServiceImpl extends BaseServiceImpl<Promocion, Long>
		implements PromocionService {

	private final Logger log = LoggerFactory.getLogger(PromocionServiceImpl.class);

	private final PromocionRepository promocionRepository;

	private final PromocionMapper promocionMapper;
	
	@Autowired
	private AdjuntoRepository adjuntoRepository;

	public PromocionServiceImpl(PromocionRepository promocionRepository,
			PromocionMapper promocionMapper) {
		this.promocionRepository = promocionRepository;
		this.promocionMapper = promocionMapper;
	}

	/**
	 * Save a promocion.
	 *
	 * @param promocionDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PromocionDTO save(PromocionDTO promocionDTO) {
		log.debug("Request to save Promocion : {}", promocionDTO);
		Promocion promocion = promocionMapper.toEntity(promocionDTO);
		promocion = promocionRepository.save(promocion);
		
		if (promocionDTO.getAdjunto() != null) {
			Adjunto adjunto = null;
			if (promocionDTO.getAdjunto().getId() != null) {
				adjunto = adjuntoRepository.findById(promocionDTO.getAdjunto().getId()).orElse(null);
				if (adjunto != null) {
					adjunto.setFileName(promocionDTO.getAdjunto().getFileName());
					adjunto.setFile(promocionDTO.getAdjunto().getFile());
					adjunto.setContentType(promocionDTO.getAdjunto().getContentType());
					adjunto.setFileContentType(promocionDTO.getAdjunto().getFileContentType());
					adjunto.setSize(promocionDTO.getAdjunto().getSize());
				}
			}

			if (adjunto == null) {
				adjunto = new Adjunto();
				adjunto.setFileName(promocionDTO.getAdjunto().getFileName());
				adjunto.setFile(promocionDTO.getAdjunto().getFile());
				adjunto.setContentType(promocionDTO.getAdjunto().getContentType());
				adjunto.setFileContentType(promocionDTO.getAdjunto().getFileContentType());
				adjunto.setSize(promocionDTO.getAdjunto().getSize());
				adjuntoRepository.save(adjunto);
			}

			promocion.setAdjuntoId(adjunto.getId());
		}
		
		return promocionMapper.toDto(promocion);
	}

	/**
	 * Get all the promocions.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PromocionDTO> findAllDTO() {
		log.debug("Request to get all Promocions");
		return promocionRepository.findAll().stream().map(promocionMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one promocion by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PromocionDTO> findOne(Long id) {
		log.debug("Request to get Promocion : {}", id);
		return promocionRepository.findById(id).map(promocionMapper::toDto);
	}

	/**
	 * Delete the promocion by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Promocion : {}", id);
		promocionRepository.deleteById(id);
	}


}
