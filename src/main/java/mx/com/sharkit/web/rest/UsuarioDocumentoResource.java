package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.DireccionService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.UsuarioDocumentoService;
import mx.com.sharkit.service.dto.UsuarioDocumentoDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.UsuarioDocumento}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioDocumentoResource {

	private final Logger log = LoggerFactory.getLogger(UsuarioDocumentoResource.class);

	private static final String ENTITY_NAME = "usuarioDocumento";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UsuarioDocumentoService usuarioDocumentoService;

	private final UserService userService;

	private final DireccionService direccionService;

	public UsuarioDocumentoResource(UsuarioDocumentoService usuarioDocumentoService, UserService userService,
			DireccionService direccionService) {
		this.usuarioDocumentoService = usuarioDocumentoService;
		this.userService = userService;
		this.direccionService = direccionService;
	}

	/**
	 * {@code POST  /usuario-documentos} : Create a new usuarioDocumento.
	 *
	 * @param usuarioDocumentoDTO the usuarioDocumentoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new usuarioDocumentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the usuarioDocumento has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/usuario-documentos")
	public ResponseEntity<UsuarioDocumentoDTO> createUsuarioDocumento(
			@RequestBody UsuarioDocumentoDTO usuarioDocumentoDTO) throws URISyntaxException {
		log.debug("REST request to save UsuarioDocumento : {}", usuarioDocumentoDTO);
		if (usuarioDocumentoDTO.getId() != null) {
			throw new BadRequestAlertException("A new usuario Documento cannot already have an ID", ENTITY_NAME,
					"idexists");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		
		if (usuarioDocumentoDTO.getAdjunto() == null) {
			throw new BadRequestAlertException("El archivo adjunto es requerido", ENTITY_NAME, "idnull");
		}
		
		usuarioDocumentoDTO.setUsuarioId(usuarioId);
		UsuarioDocumentoDTO result = usuarioDocumentoService.save(usuarioDocumentoDTO);
		
		return ResponseEntity
				.created(new URI("/api/usuario-documentos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /usuario-documentos} : Updates an existing usuarioDocumento.
	 *
	 * @param usuarioDocumentoDTO the usuarioDocumentoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated usuarioDocumentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the usuarioDocumentoDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         usuarioDocumentoDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/usuario-documentos")
	public ResponseEntity<UsuarioDocumentoDTO> updateUsuarioDocumento(
			@RequestBody UsuarioDocumentoDTO usuarioDocumentoDTO) throws URISyntaxException {
		log.debug("REST request to update UsuarioDocumento : {}", usuarioDocumentoDTO);
		if (usuarioDocumentoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

//		Optional<UsuarioDocumentoDTO> optUserDireccion = usuarioDocumentoService.findOne(usuarioDocumentoDTO.getId());
//		if (optUserDireccion.isPresent()) {
//			UsuarioDocumentoDTO userDireccion = optUserDireccion.get();
//			userDireccion.getDireccion().setCodigoPostal(usuarioDocumentoDTO.getDireccion().getCodigoPostal());
//			userDireccion.getDireccion().setDireccion(usuarioDocumentoDTO.getDireccion().getDireccion());
//			userDireccion.getDireccion().setColonia(usuarioDocumentoDTO.getDireccion().getColonia());
//			userDireccion.getDireccion().setLatitud(usuarioDocumentoDTO.getDireccion().getLatitud());
//			userDireccion.getDireccion().setLongitud(usuarioDocumentoDTO.getDireccion().getLongitud());
//
//			DireccionDTO direccion = direccionService.save(userDireccion.getDireccion());
//
//			userDireccion.setAlias(usuarioDocumentoDTO.getAlias());
//			userDireccion.setTipoDireccionId(usuarioDocumentoDTO.getTipoDireccionId());
//			userDireccion.setDireccion(direccion);
//
//			usuarioDocumentoDTO = usuarioDocumentoService.save(userDireccion);
//		} else {
//			throw new BadRequestAlertException("No se encontro la dirección con el id proporcionado", ENTITY_NAME, "idnull");
//		}

		UsuarioDocumentoDTO result = usuarioDocumentoDTO;

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				usuarioDocumentoDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /usuario-documentos} : get all the usuarioDocumentos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of usuarioDocumentos in body.
	 */
//	@GetMapping("/usuario-documentos")
//	public List<UsuarioDocumentoDTO> getAllUsuarioDocumentos() {
//		log.debug("REST request to get all UsuarioDocumentos");
//
//		Optional<User> user = userService.getUserWithAuthorities();
//		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
//		if (usuarioId == 0) {
//			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idexists");
//		}
//		
//
//		return usuarioDocumentoService.findByUsuarioId(usuarioId);
//	}

	/**
	 * {@code GET  /usuario-documentos : get all the usuarioDocumentos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of usuarioDocumentos in body.
	 */
	@GetMapping("/usuario-documentos")
	public List<Map<String, Object>> getAllDocumentosUsuarioByTipoUsuario() {
		log.debug("REST request to get all UsuarioDocumentos");

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idexists");
		}
		

		return usuarioDocumentoService.findDocumentosUsuarioByTipo(usuarioId, user.get().getTipoUsuarioId());
	}

	/**
	 * {@code GET  /usuario-documentos/:id} : get the "id" usuarioDocumento.
	 *
	 * @param id the id of the usuarioDocumentoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the usuarioDocumentoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/usuario-documentos/{id}")
	public ResponseEntity<UsuarioDocumentoDTO> getUsuarioDocumento(@PathVariable Long id) {
		log.debug("REST request to get UsuarioDocumento : {}", id);
		Optional<UsuarioDocumentoDTO> usuarioDocumentoDTO = usuarioDocumentoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(usuarioDocumentoDTO);
	}

	/**
	 * {@code DELETE  /usuario-documentos/:id} : delete the "id" usuarioDocumento.
	 *
	 * @param id the id of the usuarioDocumentoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/usuario-documentos/{id}")
	public ResponseEntity<Void> deleteUsuarioDocumento(@PathVariable Long id) {
		log.debug("REST request to delete UsuarioDocumento : {}", id);
		usuarioDocumentoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
