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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

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
import mx.com.sharkit.service.CarritoCompraService;
import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.StripeService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.ChargeRequestDTO;
import mx.com.sharkit.service.dto.ChargeRequestDTO.Currency;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.service.dto.PedidoAltaDTO;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.dto.PedidoPagoDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Pedido}.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

	private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

	private static final String ENTITY_NAME = "pedido";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PedidoService pedidoService;

	private final PedidoProveedorService pedidoProveedorService;

	private final UserService userService;

	private final StripeService stripeService;

	private final ProveedorRepository proveedorRepository;

	private final TransportistaRepository transportistaRepository;

	private final PushNotificationsService pushNotificationsService;
	
	@Autowired
	private NotificacionAsyncService notificacionAsyncService;

	@Autowired
	private CarritoCompraService carritoCompraService;

	public PedidoResource(PedidoService pedidoService, UserService userService,
			PedidoProveedorService pedidoProveedorService, StripeService stripeService,
			ProveedorRepository proveedorRepository, TransportistaRepository transportistaRepository,
			PushNotificationsService pushNotificationsService) {
		this.pedidoService = pedidoService;
		this.userService = userService;
		this.pedidoProveedorService = pedidoProveedorService;
		this.stripeService = stripeService;
		this.proveedorRepository = proveedorRepository;
		this.transportistaRepository = transportistaRepository;
		this.pushNotificationsService = pushNotificationsService;
	}

	/**
	 * {@code POST  /pedidos} : Create a new pedido.
	 *
	 * @param pedidoDTO the pedidoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedido has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
//    @PostMapping("/pedidos")
//    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
//        log.debug("REST request to save Pedido : {}", pedidoDTO);
//        if (pedidoDTO.getId() != null) {
//            throw new BadRequestAlertException("A new pedido cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        PedidoDTO result = pedidoService.save(pedidoDTO);
//        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

	/**
	 * {@code POST  /pedidos} : Create a new pedido.
	 *
	 * @param pedidoDTO the pedidoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedido has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/pedidos")
	public ResponseEntity<PedidoDTO> createPedidoNuevo(@RequestBody PedidoAltaDTO pedidoAltaDTO)
			throws URISyntaxException, Exception {
		log.debug("REST request to save pedidoAltaDTO : {}", pedidoAltaDTO);
		if (pedidoAltaDTO.getProductos() == null || pedidoAltaDTO.getProductos().isEmpty()) {
			throw new BadRequestAlertException("Lista de productos vacía", ENTITY_NAME, "arraynull");
		}

		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		pedidoAltaDTO.setUsuarioId(clienteId);
		PedidoDTO resultSave = pedidoService.generaNuevoPedido(pedidoAltaDTO);

		PedidoDTO result = getPedidoCompleto(resultSave.getId()).orElse(new PedidoDTO());

		return ResponseEntity
				.created(new URI("/api/pedidos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /pedidos} : Updates an existing pedido.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pedidos")
	public ResponseEntity<PedidoDTO> updatePedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
		log.debug("REST request to update Pedido : {}", pedidoDTO);
		if (pedidoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PedidoDTO result = pedidoService.save(pedidoDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidoDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /pedidos/pago} : Pago de pedido with Stripe.
	 *
	 * @param pedidoDTO the pedidoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoDTO, or with status {@code 400 (Bad Request)} if
	 *         the pedidoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pedidos/pago")
	public ResponseEntity<PedidoDTO> pagoPedido(@RequestBody PedidoPagoDTO pedidopagoDTO) throws URISyntaxException {

		log.debug("REST request to update Pedido : {}", pedidopagoDTO);

		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		if (pedidopagoDTO.getPedidoId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		Optional<PedidoDTO> optPedido = pedidoService.findOne(pedidopagoDTO.getPedidoId());
		if (!optPedido.isPresent()) {
			throw new BadRequestAlertException("No se encontró el pedido en la base.", ENTITY_NAME, "idnull");
		}

		PedidoDTO pedido = optPedido.get();
		// Checar el moto
		ChargeRequestDTO chargeRequest = new ChargeRequestDTO();
		chargeRequest.setAmount(pedido.getTotal());
		chargeRequest.setCurrency(Currency.MXN);
		chargeRequest.setDescription("Pago de pedido: " + pedido.getFolio());
		chargeRequest.setStripeToken(pedidopagoDTO.getToken());

		Charge charge = null;
		try {
			charge = stripeService.charge(chargeRequest);
//			pedido = pedidoService.registraPagoPedido(pedidopagoDTO.getPedidoId(), charge, clienteId);
//			//Envío de notificación push con Firebase
//			sendPushNotificationPedidoPagado(pedido);
			
		} catch (StripeException e) {
			log.error("Error Stripe: {}", e);
//			throw new BadRequestAlertException("Error al procesar el pago.", ENTITY_NAME, "errorStripe");
		}

		if (charge == null) {
			charge = new Charge();
		}
		pedido = pedidoService.registraPagoPedido(pedidopagoDTO.getPedidoId(), charge, clienteId);
		if (pedido.getEstatusId() == Estatus.PEDIDO_PAGADO) {
			//Envío de notificación push con Firebase
			sendPushNotificationPedidoPagado(pedido);
			carritoCompraService.deleteByClienteId(clienteId);
		}

		return ResponseEntity.ok().body(pedido);
	}


	/**
	 * {@code GET  /pedidos} : get all the pedidos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidos in body.
	 */
	@GetMapping("/pedidos")
	public List<PedidoDTO> getAllPedidos() {
		log.debug("REST request to get all Pedidos");
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		List<PedidoDTO> lstPedidos = pedidoService.findByClienteId(clienteId);
		for (PedidoDTO pedidoDTO : lstPedidos) {
//			pedidoDTO.setPedidoProveedores(pedidoProveedorService.findByPedidoId(pedidoDTO.getId()));
		}

		return lstPedidos;
	}

	/**
	 * {@code GET  /pedidos/:id} : get the "id" pedido.
	 *
	 * @param id the id of the pedidoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pedidoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/pedidos/{id}")
	public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
		log.debug("REST request to get Pedido : {}", id);
		return ResponseUtil.wrapOrNotFound(getPedidoCompleto(id));
	}

	private Optional<PedidoDTO> getPedidoCompleto(Long pedidoId) {
		Optional<PedidoDTO> pedidoDTO = pedidoService.findOne(pedidoId);
		if (pedidoDTO.isPresent()) {
			pedidoDTO.get().setPedidoProveedores(pedidoProveedorService.findByPedidoId(pedidoId));
		}
		return pedidoDTO;
	}

	/**
	 * {@code DELETE  /pedidos/:id} : delete the "id" pedido.
	 *
	 * @param id the id of the pedidoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/pedidos/{id}")
	public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
		log.debug("REST request to delete Pedido : {}", id);
		pedidoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /proveedor/pedidos} : get all the pedidos of proveedor.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidos in body.
	 */
	@GetMapping("proveedor/pedidos")
	public List<PedidoDTO> getAllPedidosProveedor() {
		log.debug("REST request to get all Pedidos");
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

		List<PedidoDTO> lstPedidos = pedidoService.findByProveedorId(proveedorId);
		for (PedidoDTO pedidoDTO : lstPedidos) {
			pedidoDTO.setPedidoProveedores(
					pedidoProveedorService.findByPedidoIdAndProveedorId(pedidoDTO.getId(), proveedorId));
		}

		return lstPedidos;
	}

	/**
	 * {@code GET /proveedor/pedidos/{pedidoId}} : get the pedidos of proveedor by
	 * pedidoId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidos in body.
	 */
	@GetMapping("proveedor/pedidos/{pedidoId}")
	public ResponseEntity<PedidoDTO> getPedidoProveedor(@PathVariable Long pedidoId) {
		log.debug("REST request to get Pedido by pedidoId");
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

		PedidoDTO pedidoDTO = pedidoService.findOne(pedidoId).orElse(null);
		if (pedidoDTO != null) {
			pedidoDTO.setPedidoProveedores(
					pedidoProveedorService.findByPedidoIdAndProveedorId(pedidoDTO.getId(), proveedorId));

		}

		return ResponseEntity.ok().body(pedidoDTO);
	}

	/**
	 * {@code GET  /transportista/pedidos} : get all the pedidos of Transportista.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidos in body.
	 */
	@GetMapping("transportista/pedidos")
	public List<PedidoDTO> getAllPedidosTransportista() {
		log.debug("REST request to get all Pedidos of Transportista");
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
		log.debug("Transportista: {}", transportistaId);
		List<PedidoDTO> lstPedidos = pedidoService.findByTransportistaId(transportistaId);
		for (PedidoDTO pedidoDTO : lstPedidos) {
			pedidoDTO.setPedidoProveedores(
					pedidoProveedorService.findByPedidoIdAndTransportistaId(pedidoDTO.getId(), transportistaId));
		}

		return lstPedidos;
	}
	
	/**
	 * {@code GET /transportista/pedidos/{pedidoId}} : get the pedidos of proveedor by
	 * pedidoId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidos in body.
	 */
	@GetMapping("transportista/pedidos/{pedidoId}")
	public ResponseEntity<PedidoDTO> getPedidoTransportista(@PathVariable Long pedidoId) {
		log.debug("REST request to get Pedido by transportistaId");
		
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

		PedidoDTO pedidoDTO = pedidoService.findOne(pedidoId).orElse(null);
		if (pedidoDTO != null) {
			pedidoDTO.setPedidoProveedores(
					pedidoProveedorService.findByPedidoIdAndTransportistaId(pedidoDTO.getId(), transportistaId));

		}

		return ResponseEntity.ok().body(pedidoDTO);
	}

	
	@Async
	private void sendPushNotificationPedidoPagado(PedidoDTO pedido) {
		
		List<PedidoProveedorDTO> lstPprov = pedidoProveedorService.findByPedidoId(pedido.getId());
		for (PedidoProveedorDTO pprov : lstPprov) {
			try {
				
				Map<String, Object> mapData = new HashMap<>();
				mapData.put("pedidoId", pedido.getId());
				mapData.put("pedidoProveedorId", pprov.getId());
				
				// Notificación Push
				HttpEntity<String> request = pushNotificationsService.createRequestNotification(
						pprov.getProveedor().getUsuario().getToken(),
						String.format("El pedido es %s", pedido.getFolio()),
						String.format("El cliente %s %s ha solicitado un pedido", pedido.getCliente().getFirstName(),
								pedido.getCliente().getLastName()),
						String.format("El cliente %s %s ha solicitado un pedido %s", pedido.getCliente().getFirstName(),
								pedido.getCliente().getLastName(), pedido.getFolio()),
						EnumPantallas.SOLICITUD_PEDIDO.getView(), mapData);
				
				// Notificación Web
				NotificacionDTO notificacionDTO = new NotificacionDTO();
				notificacionDTO.setTitulo(String.format("Nuevo pedido: %s", pedido.getFolio()));
				notificacionDTO.setDescripcion(String.format("El cliente %s %s ha solicitado un pedido", pedido.getCliente().getFirstName(),
						pedido.getCliente().getLastName()));
				notificacionDTO.setEstatus(0);
				notificacionDTO.setFechaNotificacion(LocalDateTime.now());
				notificacionDTO.setUsuarioId(pprov.getProveedor().getUsuario().getId());
				notificacionDTO.setViewId(EnumPantallas.SOLICITUD_PEDIDO.getView());
				notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
				notificacionAsyncService.save(notificacionDTO);


				log.debug("request: {}", request);
				CompletableFuture<String> pushNotification = pushNotificationsService.send(request, TipoUsuario.PROVEEDOR);

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

			} catch (JSONException e) {
				log.debug("JSONException e: {}", e);
			} catch (JsonProcessingException e1) {
				log.debug("JsonProcessingException e: {}", e1);
			} 

		}

	}


}
