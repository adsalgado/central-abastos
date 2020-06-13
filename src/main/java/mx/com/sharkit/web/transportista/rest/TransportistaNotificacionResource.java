package mx.com.sharkit.web.transportista.rest;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.HeaderUtil;
import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.pushnotif.service.EnumPantallas;
import mx.com.sharkit.pushnotif.service.PushNotificationsService;
import mx.com.sharkit.repository.TransportistaRepository;
import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.TerminarServicioPedidoProveedorDTO;
import mx.com.sharkit.web.rest.ProductoProveedorResource;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class TransportistaNotificacionResource {

	private final Logger log = LoggerFactory.getLogger(ProductoProveedorResource.class);

	private static final String ENTITY_NAME = "productoProveedor";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private UserService userService;

	@Autowired
	private PedidoProveedorService pedidoProveedorService;

	@Autowired
	private TransportistaRepository transportistaRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PushNotificationsService pushNotificationsService;

	@Autowired
	private NotificacionAsyncService notificacionAsyncService;

	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * {@code PUT  /transportista/pedido-proveedores/notificacion-llegada} : Updates
	 * an existing pedido proveedor.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/transportista/pedido-proveedores/notificacion-llegada")
	public ResponseEntity<PedidoProveedorDTO> terminarServicio(
			@RequestBody TerminarServicioPedidoProveedorDTO terminarDTO) throws URISyntaxException {

		log.debug("REST request to terminarServicio Pedido : {}", terminarDTO);
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		Optional<Transportista> transportista = transportistaRepository.findOneByusuarioId(usuarioId);
		Long transportistaId = transportista.isPresent() ? transportista.get().getId() : 0L;
		if (transportistaId == 0) {
			throw new BadRequestAlertException("El usuario no es transportistaId", ENTITY_NAME, "idnull");
		}

		PedidoProveedorDTO result = pedidoProveedorService.findOne(terminarDTO.getPedidoProveedorId()).orElse(null);
		if (result == null) {
			throw new BadRequestAlertException("pedidoProveedorId no existe", ENTITY_NAME, "idnull");
		}

		try {
			sendPushNotificationLlegadaTransportista(result);

		} catch (Exception e) {
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "idnull");
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				terminarDTO.getPedidoProveedorId().toString())).body(result);

	}

	private void sendPushNotificationLlegadaTransportista(PedidoProveedorDTO pprovDTO) {

		taskExecutor.execute(() -> {

			if (pprovDTO != null) {

				PedidoDTO pedido = pedidoService.findOne(pprovDTO.getPedidoId()).orElse(null);
				if (pedido != null) {
					try {

						String notificationTitle = String.format("LLegada de transportista.");
						String notificationBody = String.format(
								"El transportista ha llegado a tu domicilio, a entregar el pedido: %s",
								pedido.getFolio());

						String toCliente = pedido.getCliente().getToken();

						Map<String, Object> mapData = new HashMap<>();
						mapData.put("pedidoId", pedido.getId());
						mapData.put("pedidoProveedorId", pprovDTO.getId());

						HttpEntity<String> requestCliente = pushNotificationsService.createRequestNotification(
								toCliente, notificationTitle, notificationBody, notificationBody,
								EnumPantallas.LLEGADA_TRANSPORTISTA.getView(), mapData);

						// Notificación Web
						NotificacionDTO notificacionDTO = new NotificacionDTO();
						notificacionDTO.setTitulo(notificationTitle);
						notificacionDTO.setDescripcion(notificationBody);
						notificacionDTO.setEstatus(0);
						notificacionDTO.setFechaNotificacion(LocalDateTime.now());
						notificacionDTO.setUsuarioId(pedido.getCliente().getId());
						notificacionDTO.setViewId(EnumPantallas.LLEGADA_TRANSPORTISTA.getView());
						notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
						notificacionAsyncService.save(notificacionDTO);

						log.debug("requestCliente: {}", requestCliente);
						CompletableFuture<String> pushNotificationCliente = pushNotificationsService
								.send(requestCliente, TipoUsuario.CLIENTE);
						CompletableFuture.allOf(pushNotificationCliente).join();

						try {
							String firebaseResponseCliente = pushNotificationCliente.get();
							log.debug("firebaseResponseCliente: {}", firebaseResponseCliente);

						} catch (InterruptedException e) {
							log.debug("InterruptedException: {}", e);
						} catch (ExecutionException e) {
							log.debug("ExecutionException: {}", e);
						} catch (Exception e) {
							log.debug("Exception: {}", e);
						}
					} catch (JSONException e) {
						log.debug("JSONException e: {}", e);
					} catch (JsonProcessingException e1) {
						log.debug("JsonProcessingException e: {}", e1);
					}

				}

			}

		});

	}

}
