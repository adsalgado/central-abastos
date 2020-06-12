package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.pushnotif.service.EnumPantallas;
import mx.com.sharkit.pushnotif.service.PushNotificationsService;
import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.QuejaService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.service.dto.QuejaDTO;
import mx.com.sharkit.service.dto.UserDTO;
import mx.com.sharkit.service.mapper.UserMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Queja}.
 */
@RestController
@RequestMapping("/api")
public class QuejaResource {

	private final Logger log = LoggerFactory.getLogger(QuejaResource.class);

	private static final String ENTITY_NAME = "queja";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private UserService userService;

	@Autowired
	private PushNotificationsService pushNotificationsService;

	@Autowired
	private NotificacionAsyncService notificacionAsyncService;

	@Autowired
	private TaskExecutor taskExecutor;

	private final QuejaService quejaService;

	public QuejaResource(QuejaService quejaService) {
		this.quejaService = quejaService;
	}

	/**
	 * {@code POST  /quejas} : Create a new queja.
	 *
	 * @param quejaDTO the quejaDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new quejaDTO, or with status {@code 400 (Bad Request)} if
	 *         the queja has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/quejas")
	public ResponseEntity<QuejaDTO> createQueja(@RequestBody QuejaDTO quejaDTO) throws URISyntaxException {
		log.debug("REST request to save Queja : {}", quejaDTO);
		if (quejaDTO.getId() != null) {
			throw new BadRequestAlertException("A new queja cannot already have an ID", ENTITY_NAME, "idexists");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		User usuario = user.isPresent() ? user.get() : null;
		if (usuario == null) {
			throw new BadRequestAlertException("El usuario es requerido", ENTITY_NAME, "idnull");
		}

		quejaDTO.setEstatusId(Estatus.QUEJA_ABIERTA);
		quejaDTO.setUsuarioId(usuario.getId());
		quejaDTO.setTipoUsuarioId(usuario.getTipoUsuarioId());
		quejaDTO.setFechaAlta(LocalDateTime.now());
		QuejaDTO result = quejaService.save(quejaDTO);

		if (result != null) {
			sendPushNotificationToAllContactUsers(usuario, result);
		}

		return ResponseEntity
				.created(new URI("/api/quejas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /quejas} : Updates an existing queja.
	 *
	 * @param quejaDTO the quejaDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated quejaDTO, or with status {@code 400 (Bad Request)} if the
	 *         quejaDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the quejaDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/quejas")
	public ResponseEntity<QuejaDTO> updateQueja(@RequestBody QuejaDTO quejaDTO) throws URISyntaxException {
		log.debug("REST request to update Queja : {}", quejaDTO);
		if (quejaDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		QuejaDTO result = quejaService.save(quejaDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quejaDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /quejas} : get all the quejas.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of quejas in body.
	 */
	@GetMapping("/quejas")
	public List<QuejaDTO> getAllQuejas() {
		log.debug("REST request to get all Quejas");
		return quejaService.findAllDTO();
	}

	/**
	 * {@code GET  /quejas/:id} : get the "id" queja.
	 *
	 * @param id the id of the quejaDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the quejaDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/quejas/{id}")
	public ResponseEntity<QuejaDTO> getQueja(@PathVariable Long id) {
		log.debug("REST request to get Queja : {}", id);
		Optional<QuejaDTO> quejaDTO = quejaService.findOne(id);
		return ResponseUtil.wrapOrNotFound(quejaDTO);
	}

	/**
	 * {@code DELETE  /quejas/:id} : delete the "id" queja.
	 *
	 * @param id the id of the quejaDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/quejas/{id}")
	public ResponseEntity<Void> deleteQueja(@PathVariable Long id) {
		log.debug("REST request to delete Queja : {}", id);
		quejaService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	private void sendPushNotificationToAllContactUsers(User userQueja, QuejaDTO result) {
		log.debug("SEND PUSH NOTIFICATION ...");
		taskExecutor.execute(() -> {
			List<UserDTO> users = userService.findAllByTipoUsuarioId(TipoUsuario.CONTACT_CENTER);
			users.forEach(usCc -> 
				sendNotificationToContactUser(userQueja, result, usCc)
			);
		});
	}

	private void sendNotificationToContactUser(User userQueja, QuejaDTO result, UserDTO us) {
		log.debug("SEND PUSH NOTIFICATION TO CC: {} {} {} ...", userQueja, result, us);
		
		try {
			String notificationTitle = "Queja nueva";
			String notificationBody = String.format("El usuario %s %s ha generado una queja", userQueja.getFirstName(),
					userQueja.getLastName());

			Map<String, Object> mapData = new HashMap<>();
			mapData.put("pedidoProveedorId", result.getPedidoProveedorId());

			// Notificación Push
			HttpEntity<String> request = pushNotificationsService.createRequestNotification(us.getTokenWeb(),
					notificationTitle, notificationBody, notificationBody, EnumPantallas.SOLICITUD_PEDIDO.getView(),
					mapData);

			// Notificación Web
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			notificacionDTO.setTitulo(notificationTitle);
			notificacionDTO.setDescripcion(notificationBody);
			notificacionDTO.setEstatus(0);
			notificacionDTO.setFechaNotificacion(LocalDateTime.now());
			notificacionDTO.setUsuarioId(us.getId());
			notificacionDTO.setViewId(EnumPantallas.CONTACT_CENTER.getView());
			notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
			notificacionAsyncService.save(notificacionDTO);

			if (us.getTokenWeb() != null) {
				log.debug("request: {}", request);
				CompletableFuture<String> pushNotification = pushNotificationsService.send(request,
						TipoUsuario.PROVEEDOR);

				CompletableFuture.allOf(pushNotification).join();

				try {
					String firebaseResponse = pushNotification.get();
					log.debug("firebaseResponse: {}", firebaseResponse);
				} catch (InterruptedException e) {
					log.debug("InterruptedException: {}", e);
				} catch (ExecutionException e) {
					log.debug("ExecutionException: {}", e);
				} catch (Exception e) {
					log.debug("Exception: {}", e);
				}
			}

		} catch (JSONException e) {
			log.debug("JSONException e: {}", e);
		} catch (JsonProcessingException e1) {
			log.debug("JsonProcessingException e: {}", e1);
		}

	}

}
