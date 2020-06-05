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

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Proveedor;
import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.pushnotif.service.EnumPantallas;
import mx.com.sharkit.pushnotif.service.PushNotificationsService;
import mx.com.sharkit.repository.ProveedorRepository;
import mx.com.sharkit.repository.TransportistaRepository;
import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.CalificacionPedidoProveedorDTO;
import mx.com.sharkit.service.dto.ChangeEstatusPedidoProveedorDTO;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.dto.TerminarServicioPedidoProveedorDTO;
import mx.com.sharkit.service.mapper.PedidoProveedorMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.PedidoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class PedidoProveedorResource {

	private final Logger log = LoggerFactory.getLogger(PedidoProveedorResource.class);

	private static final String ENTITY_NAME = "pedidoProveedor";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PedidoProveedorService pedidoProveedorService;

	private final UserService userService;

	private final ProveedorRepository proveedorRepository;

	private final TransportistaRepository transportistaRepository;

	@Autowired
	private PedidoProveedorMapper pedidoProveedorMapper;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PushNotificationsService pushNotificationsService;
	
	@Autowired
	private NotificacionAsyncService notificacionAsyncService;

	public PedidoProveedorResource(PedidoProveedorService pedidoProveedorService, UserService userService,
			ProveedorRepository proveedorRepository, TransportistaRepository transportistaRepository) {
		this.pedidoProveedorService = pedidoProveedorService;
		this.userService = userService;
		this.proveedorRepository = proveedorRepository;
		this.transportistaRepository = transportistaRepository;
	}

	/**
	 * {@code POST  /pedido-proveedores} : Create a new pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the pedidoProveedorDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pedidoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedor has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/pedido-proveedores")
	public ResponseEntity<PedidoProveedorDTO> createPedidoProveedor(
			@Valid @RequestBody PedidoProveedorDTO pedidoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to save PedidoProveedor : {}", pedidoProveedorDTO);
		if (pedidoProveedorDTO.getId() != null) {
			throw new BadRequestAlertException("A new pedidoProveedor cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		PedidoProveedorDTO result = pedidoProveedorService.save(pedidoProveedorDTO);
		return ResponseEntity
				.created(new URI("/api/pedido-proveedores/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /pedido-proveedores} : Updates an existing pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the pedidoProveedorDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedorDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         pedidoProveedorDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pedido-proveedores")
	public ResponseEntity<PedidoProveedorDTO> updatePedidoProveedor(
			@Valid @RequestBody PedidoProveedorDTO pedidoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to update PedidoProveedor : {}", pedidoProveedorDTO);
		if (pedidoProveedorDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PedidoProveedorDTO result = pedidoProveedorService.save(pedidoProveedorDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				pedidoProveedorDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /pedido-proveedores} : get all the pedidoProveedors.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidoProveedors in body.
	 */
	@GetMapping("/pedido-proveedores")
	public List<PedidoProveedorDTO> getAllPedidoProveedors() {
		log.debug("REST request to get all PedidoProveedors");
		return pedidoProveedorService.findAllDTO();
	}

	/**
	 * {@code GET  /pedido-proveedores/:id} : get the "id" pedidoProveedor.
	 *
	 * @param id the id of the pedidoProveedorDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pedidoProveedorDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/pedido-proveedores/{id}")
	public ResponseEntity<PedidoProveedorDTO> getPedidoProveedor(@PathVariable Long id) {
		log.debug("REST request to get PedidoProveedor : {}", id);
		Optional<PedidoProveedorDTO> pedidoProveedorDTO = pedidoProveedorService.findOne(id);
		return ResponseUtil.wrapOrNotFound(pedidoProveedorDTO);
	}

	/**
	 * {@code DELETE  /pedido-proveedores/:id} : delete the "id" pedidoProveedor.
	 *
	 * @param id the id of the pedidoProveedorDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/pedido-proveedores/{id}")
	public ResponseEntity<Void> deletePedidoProveedor(@PathVariable Long id) {
		log.debug("REST request to delete PedidoProveedor : {}", id);
		pedidoProveedorService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code PUT  /pedidos} : Updates an existing pedido proveedor.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/proveedor/pedido-proveedores")
	public ResponseEntity<PedidoProveedorDTO> updatePedido(
			@RequestBody ChangeEstatusPedidoProveedorDTO changeEstatusDTO) throws URISyntaxException {
		log.debug("REST request to update Pedido : {}", changeEstatusDTO);
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		Optional<Proveedor> proveedor = proveedorRepository.findOneByUsuarioId(usuarioId);
		Long proveedorId = proveedor.isPresent() ? proveedor.get().getId() : 0L;
		if (proveedorId == 0) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}

		PedidoProveedorDTO result = pedidoProveedorService.cambiaEstatusPedidoProveedorAndDetalles(
				changeEstatusDTO.getPedidoProveedorId(), changeEstatusDTO.getEstatusId(), usuarioId);

		if (result != null && result.getEstatusId().equals(Estatus.PEDIDO_PREPARADO)) {
			sendPushNotificationPedidoConfirmado(result);
		}

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proveedorId.toString()))
				.body(result);

	}

	/**
	 * {@code PUT  /pedido-proveedores/calificacion-servicio} : Updates an existing
	 * pedido proveedor.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pedido-proveedores/calificacion-servicio")
	public ResponseEntity<PedidoProveedorDTO> calificacionServicio(
			@RequestBody CalificacionPedidoProveedorDTO calificacionDTO) throws URISyntaxException {

		log.debug("REST request to update Pedido : {}", calificacionDTO);
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		PedidoProveedorDTO result = pedidoProveedorService.actualizaCalificacionServicio(calificacionDTO, usuarioId);
		if (result != null && result.getCalificacionServicio() != null) {
			sendPushNotificationCalificacionServicio(result);
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				calificacionDTO.getPedidoProveedorId().toString())).body(result);

	}

	/**
	 * {@code PUT  /transportista/pedido-proveedores/terminar-servicio} : Updates an
	 * existing pedido proveedor.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/transportista/pedido-proveedores/terminar-servicio")
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

		PedidoProveedorDTO result;
		try {
			result = pedidoProveedorService.terminarServicio(terminarDTO, usuarioId);
			if (result != null && result.getEstatusId().equals(Estatus.PEDIDO_ENTREGADO)) {
				sendPushNotificationPedidoEntregado(result);
			}
		} catch (Exception e) {
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "idnull");
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				terminarDTO.getPedidoProveedorId().toString())).body(result);

	}

	@Async
	private void sendPushNotificationPedidoEntregado(PedidoProveedorDTO pedidoProveedorDTO) {

		PedidoProveedorDTO pprovDTO = pedidoProveedorService.findOne(pedidoProveedorDTO.getId()).orElse(null);
		if (pprovDTO != null) {

			PedidoDTO pedido = pedidoService.findOne(pprovDTO.getPedidoId()).orElse(null);
			if (pedido != null) {
				try {

					String notificationTitle = String.format("Se entregó tu pedido %s", pedido.getFolio());
					String notificationBody = "Hemos confirmado que tu pedido ha sido entregado, califica al transportista";
					String messageTitle = "Hemos confirmado que tu pedido ha sido entregado, califica al transportista";

					String toCliente = pedido.getCliente().getToken();

					Map<String, Object> mapData = new HashMap<>();
					mapData.put("pedidoId", pedido.getId());
					mapData.put("pedidoProveedorId", pprovDTO.getId());

					HttpEntity<String> requestCliente = pushNotificationsService.createRequestNotification(toCliente,
							notificationTitle, notificationBody, messageTitle, EnumPantallas.PEDIDO_ENTREGADO.getView(),
							mapData);
					
					// Notificación Web
					NotificacionDTO notificacionDTO = new NotificacionDTO();
					notificacionDTO.setTitulo(notificationTitle);
					notificacionDTO.setDescripcion(notificationBody);
					notificacionDTO.setEstatus(0);
					notificacionDTO.setFechaNotificacion(LocalDateTime.now());
					notificacionDTO.setUsuarioId(pedido.getCliente().getId());
					notificacionDTO.setViewId(EnumPantallas.PEDIDO_ENTREGADO.getView());
					notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
					notificacionAsyncService.save(notificacionDTO);

					log.debug("requestCliente: {}", requestCliente);
					CompletableFuture<String> pushNotificationCliente = pushNotificationsService.send(requestCliente,
							TipoUsuario.CLIENTE);
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

	}

	@Async
	private void sendPushNotificationPedidoConfirmado(PedidoProveedorDTO pedidoProveedorDTO) {

		PedidoProveedorDTO pprovDTO = pedidoProveedorService.findOne(pedidoProveedorDTO.getId()).orElse(null);
		if (pprovDTO != null) {

			PedidoDTO pedido = pedidoService.findOne(pprovDTO.getPedidoId()).orElse(null);
			if (pedido != null) {
				try {

					String notificationTitle = String.format("El proveedor %s ha confirmado el pedido %s",
							pprovDTO.getProveedor().getNombre(),
							"P" + StringUtils.leftPad(pprovDTO.getPedidoId().toString(), 9, "0"));
					String notificationBody = "Pedido Confirmado";
					String messageTitle = String.format("El proveedor %s ha confirmado el pedido %s",
							pprovDTO.getProveedor().getNombre(),
							"P" + StringUtils.leftPad(pprovDTO.getPedidoId().toString(), 9, "0"));

					String toCliente = pedido.getCliente().getToken();
					String toTransportista = pprovDTO.getProveedor().getTransportista().getUsuario().getToken();

					Map<String, Object> mapData = new HashMap<>();
					mapData.put("pedidoId", pedido.getId());
					mapData.put("pedidoProveedorId", pprovDTO.getId());

					HttpEntity<String> requestCliente = pushNotificationsService.createRequestNotification(toCliente,
							notificationTitle, notificationBody, messageTitle,
							EnumPantallas.PEDIDO_CONFIRMADO_CLIENTE.getView(), mapData);
					
					// Notificación Web
					NotificacionDTO notificacionDTO = new NotificacionDTO();
					notificacionDTO.setTitulo(notificationBody);
					notificacionDTO.setDescripcion(notificationTitle);
					notificacionDTO.setEstatus(0);
					notificacionDTO.setFechaNotificacion(LocalDateTime.now());
					notificacionDTO.setUsuarioId(pedido.getCliente().getId());
					notificacionDTO.setViewId(EnumPantallas.PEDIDO_CONFIRMADO_CLIENTE.getView());
					notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
					notificacionAsyncService.save(notificacionDTO);

					log.debug("requestCliente: {}", requestCliente);
					CompletableFuture<String> pushNotificationCliente = pushNotificationsService.send(requestCliente,
							TipoUsuario.CLIENTE);
					CompletableFuture.allOf(pushNotificationCliente).join();

					HttpEntity<String> requestTransportista = pushNotificationsService.createRequestNotification(
							toTransportista, notificationTitle, notificationBody, messageTitle,
							EnumPantallas.PEDIDO_CONFIRMADO_TRANSPORTISTA.getView(), mapData);

					// Notificación Web
					notificacionDTO = new NotificacionDTO();
					notificacionDTO.setTitulo(notificationBody);
					notificacionDTO.setDescripcion(notificationTitle);
					notificacionDTO.setEstatus(0);
					notificacionDTO.setFechaNotificacion(LocalDateTime.now());
					notificacionDTO.setUsuarioId(pprovDTO.getProveedor().getTransportista().getUsuario().getId());
					notificacionDTO.setViewId(EnumPantallas.PEDIDO_CONFIRMADO_TRANSPORTISTA.getView());
					notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
					notificacionAsyncService.save(notificacionDTO);

					log.debug("request: {}", requestTransportista);
					CompletableFuture<String> pushNotificationTransportista = pushNotificationsService
							.send(requestTransportista, TipoUsuario.TRANSPORTISTA);
					CompletableFuture.allOf(pushNotificationTransportista).join();

					try {
						String firebaseResponseCliente = pushNotificationCliente.get();
						log.debug("firebaseResponseCliente: {}", firebaseResponseCliente);

						String firebaseResponseTransportista = pushNotificationCliente.get();
						log.debug("firebaseResponseTransportista: {}", firebaseResponseTransportista);

					} catch (InterruptedException e) {
						log.debug("InterruptedException: {}", e);
					} catch (ExecutionException e) {
						log.debug("ExecutionException: {}", e);
					} catch (Exception e) {
						log.debug("Exception: {}", e);
					}

				} catch (JSONException e) {
					log.debug("JSONException e: {}", e);
				} catch (HttpClientErrorException e) {
					log.debug("HttpClientErrorException e: {}", e);
				} catch (JsonProcessingException e1) {
					log.debug("JsonProcessingException e: {}", e1);
				}

			}

		}

	}

	@Async
	private void sendPushNotificationCalificacionServicio(PedidoProveedorDTO pedidoProveedorDTO) {

		PedidoProveedorDTO pprovDTO = pedidoProveedorService.findOne(pedidoProveedorDTO.getId()).orElse(null);
		if (pprovDTO != null) {

			PedidoDTO pedido = pedidoService.findOne(pprovDTO.getPedidoId()).orElse(null);
			if (pedido != null) {
				try {

					String calificacionServicio = "";
					switch (pprovDTO.getCalificacionServicio()) {
					case 1:
						calificacionServicio = "PESIMO";
						break;
					case 2:
						calificacionServicio = "MALO";
						break;
					case 3:
						calificacionServicio = "REGULAR";
						break;
					case 4:
						calificacionServicio = "BUENO";
						break;
					case 5:
						calificacionServicio = "EXCELENTE";
						break;
					}

					String notificationTitle = "Pedido entregado";
					String notificationBody = String.format("El cliente ha calificado el servicio como: %s",
							calificacionServicio);
					String messageTitle = String.format("El cliente ha calificado el servicio como: %s",
							calificacionServicio);

					String toProveedor = pprovDTO.getProveedor().getUsuario().getToken();
					String toTransportista = pprovDTO.getProveedor().getTransportista().getUsuario().getToken();

					Map<String, Object> mapData = new HashMap<>();
					mapData.put("pedidoId", pedido.getId());
					mapData.put("pedidoProveedorId", pprovDTO.getId());

					HttpEntity<String> requestProveedor = pushNotificationsService.createRequestNotification(
							toProveedor, notificationTitle, notificationBody, messageTitle,
							EnumPantallas.PEDIDO_CALIFICADO.getView(), mapData);
					
					// Notificación Web
					NotificacionDTO notificacionDTO = new NotificacionDTO();
					notificacionDTO.setTitulo(notificationTitle);
					notificacionDTO.setDescripcion(notificationBody);
					notificacionDTO.setEstatus(0);
					notificacionDTO.setFechaNotificacion(LocalDateTime.now());
					notificacionDTO.setUsuarioId(pprovDTO.getProveedor().getUsuario().getId());
					notificacionDTO.setViewId(EnumPantallas.PEDIDO_CALIFICADO.getView());
					notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
					notificacionAsyncService.save(notificacionDTO);

					log.debug("requestProveedor: {}", requestProveedor);
					CompletableFuture<String> pushNotificationProveedor = pushNotificationsService
							.send(requestProveedor, TipoUsuario.PROVEEDOR);
					CompletableFuture.allOf(pushNotificationProveedor).join();

					HttpEntity<String> requestTransportista = pushNotificationsService.createRequestNotification(
							toTransportista, notificationTitle, notificationBody, messageTitle,
							EnumPantallas.PEDIDO_CALIFICADO.getView(), mapData);
					
					// Notificación Web
					notificacionDTO = new NotificacionDTO();
					notificacionDTO.setTitulo(notificationTitle);
					notificacionDTO.setDescripcion(notificationBody);
					notificacionDTO.setEstatus(0);
					notificacionDTO.setFechaNotificacion(LocalDateTime.now());
					notificacionDTO.setUsuarioId(pprovDTO.getProveedor().getTransportista().getUsuario().getId());
					notificacionDTO.setViewId(EnumPantallas.PEDIDO_CALIFICADO.getView());
					notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
					notificacionAsyncService.save(notificacionDTO);

					log.debug("request: {}", requestTransportista);
					CompletableFuture<String> pushNotificationTransportista = pushNotificationsService
							.send(requestTransportista, TipoUsuario.TRANSPORTISTA);
					CompletableFuture.allOf(pushNotificationTransportista).join();

					try {
						String firebaseResponseProveedor = pushNotificationProveedor.get();
						log.debug("firebaseResponseProveedor: {}", firebaseResponseProveedor);

						String firebaseResponseTransportista = pushNotificationTransportista.get();
						log.debug("firebaseResponseTransportista: {}", firebaseResponseTransportista);

					} catch (InterruptedException e) {
						log.debug("InterruptedException: {}", e);
					} catch (ExecutionException e) {
						log.debug("ExecutionException: {}", e);
					} catch (Exception e) {
						log.debug("Exception: {}", e);
					}

				} catch (JSONException e) {
					log.debug("JSONException e: {}", e);
				} catch (HttpClientErrorException e) {
					log.debug("HttpClientErrorException e: {}", e);
				} catch (JsonProcessingException e1) {
					log.debug("JsonProcessingException e: {}", e1);
				}

			}

		}

	}

}
