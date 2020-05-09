package mx.com.sharkit.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.UsuarioDocumento;
import mx.com.sharkit.repository.UsuarioDocumentoRepository;
import mx.com.sharkit.service.AdjuntoService;
import mx.com.sharkit.service.UsuarioDocumentoService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.dto.UsuarioDocumentoDTO;
import mx.com.sharkit.service.mapper.UsuarioDocumentoMapper;

/**
 * Service Implementation for managing {@link UsuarioDocumento}.
 */
@Service
@Transactional
public class UsuarioDocumentoServiceImpl implements UsuarioDocumentoService {

	private final Logger log = LoggerFactory.getLogger(UsuarioDocumentoServiceImpl.class);

	private final UsuarioDocumentoRepository usuarioDocumentoRepository;

	private final UsuarioDocumentoMapper usuarioDocumentoMapper;

	private final AdjuntoService adjuntoService;

	public UsuarioDocumentoServiceImpl(UsuarioDocumentoRepository usuarioDocumentoRepository,
			UsuarioDocumentoMapper usuarioDocumentoMapper, AdjuntoService adjuntoService) {
		this.usuarioDocumentoRepository = usuarioDocumentoRepository;
		this.usuarioDocumentoMapper = usuarioDocumentoMapper;
		this.adjuntoService = adjuntoService;
	}

	/**
	 * Save a usuarioDocumento.
	 *
	 * @param usuarioDocumentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public UsuarioDocumentoDTO save(UsuarioDocumentoDTO usuarioDocumentoDTO) {
		log.debug("Request to save UsuarioDocumento : {}", usuarioDocumentoDTO);

		AdjuntoDTO adjunto = adjuntoService.save(usuarioDocumentoDTO.getAdjunto());

		UsuarioDocumento usuarioDocumento = usuarioDocumentoRepository.findOneByUsuarioIdAndDocumentoId(
				usuarioDocumentoDTO.getUsuarioId(), usuarioDocumentoDTO.getDocumentoId()).orElse(null);


		if (usuarioDocumento == null) {
			usuarioDocumentoDTO.setAdjuntoId(adjunto.getId());
			usuarioDocumentoDTO.setFechaAlta(LocalDateTime.now());
			usuarioDocumentoDTO.setUsuarioAltaId(usuarioDocumentoDTO.getUsuarioId());
			usuarioDocumento = usuarioDocumentoMapper.toEntity(usuarioDocumentoDTO);
			usuarioDocumento = usuarioDocumentoRepository.save(usuarioDocumento);
		} else {
			usuarioDocumento.setFechaModificacion(LocalDateTime.now());
			usuarioDocumento.setUsuarioModificacionId(usuarioDocumentoDTO.getUsuarioId());
			usuarioDocumento.setAdjuntoId(adjunto.getId());
		}

		return usuarioDocumentoMapper.toDto(usuarioDocumento);
	}

	/**
	 * Get all the usuarioDocumentos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioDocumentoDTO> findAllDTO() {
		log.debug("Request to get all UsuarioDocumentos");
		return usuarioDocumentoRepository.findAll().stream().map(usuarioDocumentoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one usuarioDocumento by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<UsuarioDocumentoDTO> findOne(Long id) {
		log.debug("Request to get UsuarioDocumento : {}", id);
		return usuarioDocumentoRepository.findById(id).map(usuarioDocumentoMapper::toDto);
	}

	/**
	 * Delete the usuarioDocumento by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete UsuarioDocumento : {}", id);
		usuarioDocumentoRepository.deleteById(id);
	}

	@Override
	public List<UsuarioDocumentoDTO> findByUsuarioId(Long usuarioId) {
		log.debug("Request to get all UsuarioDocumentos by usuarioId: {}", usuarioId);
		return usuarioDocumentoRepository.findByUsuarioId(usuarioId).stream().map(usuarioDocumentoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}

	public List<Map<String, Object>> findDocumentosUsuarioByTipo(Long usuarioId, Long tipoUsuarioId) {
		String sQuery = "SELECT 	chk.documento_id documentoId, doc.nombre nombre, usd.id usuarioDocumentoId, usd.adjunto_id adjuntoId \n"
				+ "FROM 	documento_checklist chk\n" + "INNER JOIN documento doc\n"
				+ "	ON (doc.id = chk.documento_id)\n" + "LEFT OUTER JOIN usuario_documento usd\n"
				+ "	ON (usd.documento_id = doc.id AND usd.usuario_id = " + usuarioId + ")\n"
				+ "WHERE chk.tipo_usuario_id = " + tipoUsuarioId + "";

		return usuarioDocumentoRepository.findAllByQueryNativeToMap(sQuery, new Object[] {});
	}

	@Override
	public Optional<UsuarioDocumentoDTO> findOneByUsuarioIdAndDocumentoId(Long usuarioId, Long documentoId) {
		return usuarioDocumentoRepository.findOneByUsuarioIdAndDocumentoId(usuarioId, documentoId)
				.map(usuarioDocumentoMapper::toDto);
	}
}
