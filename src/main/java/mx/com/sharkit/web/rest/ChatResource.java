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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
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
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.pushnotif.service.EnumPantallas;
import mx.com.sharkit.pushnotif.service.PushNotificationsService;
import mx.com.sharkit.repository.UserRepository;
import mx.com.sharkit.service.ChatDetalleService;
import mx.com.sharkit.service.ChatService;
import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.dto.ChatDTO;
import mx.com.sharkit.service.dto.ChatDetalleDTO;
import mx.com.sharkit.service.dto.NotificacionDTO;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.web.websocket.dto.MessageChatDTO;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Chat}.
 */
@RestController
@RequestMapping("/api")
public class ChatResource {

	private final Logger log = LoggerFactory.getLogger(ChatResource.class);

	private static final String ENTITY_NAME = "chat";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ChatService chatService;

	private final ChatDetalleService chatDetalleService;

	private final UserRepository userRepository;

	private final PushNotificationsService pushNotificationsService;

	@Autowired
	private PedidoProveedorService pedidoProveedorService;
	
	@Autowired
	private NotificacionAsyncService notificacionAsyncService;
	
	public ChatResource(ChatService chatService, ChatDetalleService chatDetalleService, UserRepository userRepository,
			PushNotificationsService pushNotificationsService) {
		this.chatService = chatService;
		this.chatDetalleService = chatDetalleService;
		this.userRepository = userRepository;
		this.pushNotificationsService = pushNotificationsService;
	}

	/**
	 * {@code POST  /chats} : Create a new chat.
	 *
	 * @param chatDTO the chatDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new chatDTO, or with status {@code 400 (Bad Request)} if the
	 *         chat has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/chats")
	public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody ChatDTO chatDTO) throws URISyntaxException {
		log.debug("REST request to save Chat : {}", chatDTO);
		if (chatDTO.getId() != null) {
			throw new BadRequestAlertException("A new chat cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ChatDTO result = chatService.save(chatDTO);
		return ResponseEntity
				.created(new URI("/api/chats/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /chats} : Updates an existing chat.
	 *
	 * @param chatDTO the chatDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated chatDTO, or with status {@code 400 (Bad Request)} if the
	 *         chatDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the chatDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/chats")
	public ResponseEntity<ChatDTO> updateChat(@Valid @RequestBody ChatDTO chatDTO) throws URISyntaxException {
		log.debug("REST request to update Chat : {}", chatDTO);
		if (chatDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ChatDTO result = chatService.save(chatDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /chats} : get all the chats.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of chats in body.
	 */
	@GetMapping("/chats")
	public List<ChatDTO> getAllChats() {
		log.debug("REST request to get all Chats");
		return chatService.findAll();
	}

	/**
	 * {@code GET  /chats/:id} : get the "id" chat.
	 *
	 * @param id the id of the chatDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the chatDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/chats/{id}")
	public ResponseEntity<ChatDTO> getChat(@PathVariable Long id) {
		log.debug("REST request to get Chat : {}", id);
		Optional<ChatDTO> chatDTO = chatService.findOne(id);
		if (chatDTO.isPresent()) {
			ChatDTO chat = chatDTO.get();
			chat.setChatDetalles(chatDetalleService.findByChatIdOrderById(chat.getId()));
		}
		return ResponseUtil.wrapOrNotFound(chatDTO);
	}

	/**
	 * {@code DELETE  /chats/:id} : delete the "id" chat.
	 *
	 * @param id the id of the chatDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/chats/{id}")
	public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
		log.debug("REST request to delete Chat : {}", id);
		chatService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /chats} : get all the chats.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of chats in body.
	 */
	@GetMapping("/proveedor/chats/pedido-proveedor/{pedidoProveedorId}/tipoChat/{tipoChatId}")
	public ResponseEntity<ChatDTO> getAllChatsByPedidoProveedorAndTipoChat(@PathVariable Long pedidoProveedorId,
			@PathVariable Long tipoChatId) {
		log.debug("REST request to get all Chats");
		ChatDTO chat;
		try {
			chat = chatService.getOrCreateChatPedidoProveedor(pedidoProveedorId, tipoChatId);
		} catch (Exception e) {
			throw new BadRequestAlertException("Invalid pedido proveedor id", ENTITY_NAME, "idnull");
		}
		return ResponseEntity.ok().body(chat);
	}

	/**
	 * {@code POST  /chats/messages} : Create a new chat.
	 *
	 * @param chatDTO the chatDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new chatDTO, or with status {@code 400 (Bad Request)} if the
	 *         chat has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/chats/messages")
	public ResponseEntity<ChatDetalleDTO> saveMessageChat(@Valid @RequestBody MessageChatDTO msgChatDTO)
			throws URISyntaxException {
		log.debug("REST request to save Chat : {}", msgChatDTO);
		if (msgChatDTO.getChatId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ChatDetalleDTO chatDetalleDTO = chatService.saveMensajeChat(msgChatDTO);
		sendPushNotification(msgChatDTO);

		return ResponseEntity.ok().body(chatDetalleDTO);
	}

	private void sendPushNotification(MessageChatDTO msgChatDTO) {
		User userTo = userRepository.findOneByLogin(msgChatDTO.getTo()).orElse(null);
		if (userTo != null) {
			String emisor = "usuario";
			User userFrom = userRepository.findOneByLogin(msgChatDTO.getFrom()).orElse(null);
			if (userFrom != null) {
				if (userFrom.getTipoUsuarioId().equals(TipoUsuario.CLIENTE)) {
					emisor = "cliente";
				} else if (userFrom.getTipoUsuarioId().equals(TipoUsuario.PROVEEDOR)) {
					emisor = "proveedor";
				} else if (userFrom.getTipoUsuarioId().equals(TipoUsuario.TRANSPORTISTA)) {
					emisor = "transportista";
				}
			}

			try {
				String title = String.format("El %s %s %s te envió un mensaje", emisor, userFrom.getFirstName(),
						userFrom.getLastName());
				
				Map<String, Object> mapData = new HashMap<>();
				mapData.put("chatId", msgChatDTO.getChatId());
				
				ChatDTO chat = chatService.findOne(msgChatDTO.getChatId()).orElse(null);
				if (chat != null) {
					mapData.put("pedidoProveedorId", chat.getPedidoProveedorId());
					PedidoProveedorDTO pprov = pedidoProveedorService.findOne(chat.getPedidoProveedorId()).orElse(null);
					if (pprov != null) {
						mapData.put("pedidoId", pprov.getPedidoId());	
					}
				}

				HttpEntity<String> request = pushNotificationsService.createRequestNotification(userTo.getToken(),
						title, msgChatDTO.getText(), title, EnumPantallas.CHAT.getView(), mapData);
				
				// Notificación Web
				NotificacionDTO notificacionDTO = new NotificacionDTO();
				notificacionDTO.setTitulo(title);
				notificacionDTO.setDescripcion(msgChatDTO.getText());
				notificacionDTO.setEstatus(0);
				notificacionDTO.setFechaNotificacion(LocalDateTime.now());
				notificacionDTO.setUsuarioId(userTo.getId());
				notificacionDTO.setViewId(EnumPantallas.CHAT.getView());
				notificacionDTO.setParametros(new ObjectMapper().writeValueAsString(mapData));
				notificacionAsyncService.save(notificacionDTO);

				log.debug("request: {}", request);
				CompletableFuture<String> pushNotification = pushNotificationsService.send(request, userTo.getTipoUsuarioId());

				CompletableFuture.allOf(pushNotification).join();

				try {
					String firebaseResponse = pushNotification.get();
					log.debug("firebaseResponse: {}", firebaseResponse);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
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
