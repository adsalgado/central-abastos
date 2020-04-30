package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
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
import mx.com.sharkit.service.UsuarioDireccionService;
import mx.com.sharkit.service.dto.DireccionDTO;
import mx.com.sharkit.service.dto.UsuarioDireccionDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.UsuarioDireccion}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioDireccionResource {

	private final Logger log = LoggerFactory.getLogger(UsuarioDireccionResource.class);

	private static final String ENTITY_NAME = "usuarioDireccion";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UsuarioDireccionService usuarioDireccionService;

	private final UserService userService;

	private final DireccionService direccionService;

	public UsuarioDireccionResource(UsuarioDireccionService usuarioDireccionService, UserService userService,
			DireccionService direccionService) {
		this.usuarioDireccionService = usuarioDireccionService;
		this.userService = userService;
		this.direccionService = direccionService;
	}

	/**
	 * {@code POST  /usuario-direcciones} : Create a new usuarioDireccion.
	 *
	 * @param usuarioDireccionDTO the usuarioDireccionDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new usuarioDireccionDTO, or with status
	 *         {@code 400 (Bad Request)} if the usuarioDireccion has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/usuario-direcciones")
	public ResponseEntity<UsuarioDireccionDTO> createUsuarioDireccion(
			@RequestBody UsuarioDireccionDTO usuarioDireccionDTO) throws URISyntaxException {
		log.debug("REST request to save UsuarioDireccion : {}", usuarioDireccionDTO);
		if (usuarioDireccionDTO.getId() != null) {
			throw new BadRequestAlertException("A new usuarioDireccion cannot already have an ID", ENTITY_NAME,
					"idexists");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		LocalDateTime now = LocalDateTime.now();

		usuarioDireccionDTO.getDireccion().setUsuarioAltaId(usuarioId);
		usuarioDireccionDTO.getDireccion().setFechaAlta(now);
		DireccionDTO direccion = direccionService.save(usuarioDireccionDTO.getDireccion());

		if (direccion == null || direccion.getId() == null) {
			throw new BadRequestAlertException("La dirección es requerida", ENTITY_NAME, "idnull");
		}

		usuarioDireccionDTO.setUsuarioId(usuarioId);
		usuarioDireccionDTO.setUsuarioAltaId(usuarioId);
		usuarioDireccionDTO.setFechaAlta(now);
		usuarioDireccionDTO.setDireccionId(direccion.getId());

		UsuarioDireccionDTO result = usuarioDireccionService.save(usuarioDireccionDTO);
		return ResponseEntity
				.created(new URI("/api/usuario-direcciones/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /usuario-direcciones} : Updates an existing usuarioDireccion.
	 *
	 * @param usuarioDireccionDTO the usuarioDireccionDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated usuarioDireccionDTO, or with status
	 *         {@code 400 (Bad Request)} if the usuarioDireccionDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         usuarioDireccionDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/usuario-direcciones")
	public ResponseEntity<UsuarioDireccionDTO> updateUsuarioDireccion(
			@RequestBody UsuarioDireccionDTO usuarioDireccionDTO) throws URISyntaxException {
		log.debug("REST request to update UsuarioDireccion : {}", usuarioDireccionDTO);
		if (usuarioDireccionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		Optional<UsuarioDireccionDTO> optUserDireccion = usuarioDireccionService.findOne(usuarioDireccionDTO.getId());
		if (optUserDireccion.isPresent()) {
			UsuarioDireccionDTO userDireccion = optUserDireccion.get();
			userDireccion.getDireccion().setCodigoPostal(usuarioDireccionDTO.getDireccion().getCodigoPostal());
			userDireccion.getDireccion().setDireccion(usuarioDireccionDTO.getDireccion().getDireccion());
			userDireccion.getDireccion().setColonia(usuarioDireccionDTO.getDireccion().getColonia());
			userDireccion.getDireccion().setLatitud(usuarioDireccionDTO.getDireccion().getLatitud());
			userDireccion.getDireccion().setLongitud(usuarioDireccionDTO.getDireccion().getLongitud());

			DireccionDTO direccion = direccionService.save(userDireccion.getDireccion());

			userDireccion.setAlias(usuarioDireccionDTO.getAlias());
			userDireccion.setTipoDireccionId(usuarioDireccionDTO.getTipoDireccionId());
			userDireccion.setDireccion(direccion);

			usuarioDireccionDTO = usuarioDireccionService.save(userDireccion);
		} else {
			throw new BadRequestAlertException("No se encontro la dirección con el id proporcionado", ENTITY_NAME, "idnull");
		}

		UsuarioDireccionDTO result = usuarioDireccionDTO;

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				usuarioDireccionDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /usuario-direcciones} : get all the usuarioDireccions.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of usuarioDireccions in body.
	 */
	@GetMapping("/usuario-direcciones")
	public List<UsuarioDireccionDTO> getAllUsuarioDireccions() {
		log.debug("REST request to get all UsuarioDireccions");

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idexists");
		}

		return usuarioDireccionService.findByUsuarioId(usuarioId);
	}

	/**
	 * {@code GET  /usuario-direcciones/:id} : get the "id" usuarioDireccion.
	 *
	 * @param id the id of the usuarioDireccionDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the usuarioDireccionDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/usuario-direcciones/{id}")
	public ResponseEntity<UsuarioDireccionDTO> getUsuarioDireccion(@PathVariable Long id) {
		log.debug("REST request to get UsuarioDireccion : {}", id);
		Optional<UsuarioDireccionDTO> usuarioDireccionDTO = usuarioDireccionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(usuarioDireccionDTO);
	}

	/**
	 * {@code DELETE  /usuario-direcciones/:id} : delete the "id" usuarioDireccion.
	 *
	 * @param id the id of the usuarioDireccionDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/usuario-direcciones/{id}")
	public ResponseEntity<Void> deleteUsuarioDireccion(@PathVariable Long id) {
		log.debug("REST request to delete UsuarioDireccion : {}", id);
		usuarioDireccionService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
