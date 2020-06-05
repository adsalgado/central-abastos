package mx.com.sharkit.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.NotificacionService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Notificacion}.
 */
@RestController
@RequestMapping("/api")
public class NotificacionResource {

	private final Logger log = LoggerFactory.getLogger(NotificacionResource.class);

	private static final String ENTITY_NAME = "notificacion";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private UserService userService;

	private final NotificacionService notificacionService;

	public NotificacionResource(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	/**
	 * {@code POST  /notificaciones} : Create a new notificacion.
	 *
	 * @param notificacionDTO the notificacionDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new notificacionDTO, or with status
	 *         {@code 400 (Bad Request)} if the notificacion has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/notificaciones")
	public ResponseEntity<NotificacionDTO> createNotificacion(@RequestBody NotificacionDTO notificacionDTO)
			throws URISyntaxException {
		log.debug("REST request to save Notificacion : {}", notificacionDTO);
		if (notificacionDTO.getId() != null) {
			throw new BadRequestAlertException("A new notificacion cannot already have an ID", ENTITY_NAME, "idexists");
		}
		NotificacionDTO result = notificacionService.save(notificacionDTO);
		return ResponseEntity
				.created(new URI("/api/notificaciones/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /notificaciones} : Updates an existing notificacion.
	 *
	 * @param notificacionDTO the notificacionDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated notificacionDTO, or with status {@code 400 (Bad Request)}
	 *         if the notificacionDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the notificacionDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/notificaciones")
	public ResponseEntity<NotificacionDTO> updateNotificacion(@RequestBody NotificacionDTO notificacionDTO)
			throws URISyntaxException {
		log.debug("REST request to update Notificacion : {}", notificacionDTO);
		if (notificacionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		NotificacionDTO result = notificacionService.save(notificacionDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				notificacionDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /notificaciones} : get all the notificacions.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of notificacions in body.
	 */
	@GetMapping("/notificaciones")
	public List<NotificacionDTO> getAllNotificacions() {
		log.debug("REST request to get all Notificacions");

		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		List<NotificacionDTO> notificaciones = notificacionService.findByUsuarioId(usuarioId).stream()
				.filter(notif -> (notif.getEstatus() == null || notif.getEstatus().equals(0)))
				.collect(Collectors.toCollection(LinkedList::new));

		for (NotificacionDTO notificacionDTO : notificaciones) {
			if (notificacionDTO.getParametros() != null && !StringUtils.isAllBlank(notificacionDTO.getParametros())) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					Map<String, Object> data = mapper.readValue(notificacionDTO.getParametros(),
							new TypeReference<Map<String, Object>>() {
							});
					notificacionDTO.setData(data);
				} catch (IOException e) {
					log.error("Error en conversion JSON: {}", e);
				}
			}
		}
		return notificaciones;
	}

	/**
	 * {@code GET  /notificaciones/:id} : get the "id" notificacion.
	 *
	 * @param id the id of the notificacionDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the notificacionDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/notificaciones/{id}")
	public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable Long id) {
		log.debug("REST request to get Notificacion : {}", id);
		Optional<NotificacionDTO> notificacionDTO = notificacionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(notificacionDTO);
	}

	/**
	 * {@code DELETE  /notificaciones/:id} : delete the "id" notificacion.
	 *
	 * @param id the id of the notificacionDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/notificaciones/{id}")
	public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
		log.debug("REST request to delete Notificacion : {}", id);
		notificacionService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
