package mx.com.sharkit.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Chat;
import mx.com.sharkit.repository.ChatRepository;
import mx.com.sharkit.service.ChatDetalleService;
import mx.com.sharkit.service.ChatService;
import mx.com.sharkit.service.dto.ChatDTO;
import mx.com.sharkit.service.dto.ChatDetalleDTO;
import mx.com.sharkit.service.mapper.ChatMapper;
import mx.com.sharkit.web.websocket.dto.MessageChatDTO;

/**
 * Service Implementation for managing {@link Chat}.
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {

	private final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

	private final ChatRepository chatRepository;

	private final ChatMapper chatMapper;
	
	private final ChatDetalleService chatDetalleService;

	public ChatServiceImpl(ChatRepository chatRepository, ChatMapper chatMapper, ChatDetalleService chatDetalleService) {
		this.chatRepository = chatRepository;
		this.chatMapper = chatMapper;
		this.chatDetalleService = chatDetalleService;
	}

	/**
	 * Save a chat.
	 *
	 * @param chatDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ChatDTO save(ChatDTO chatDTO) {
		log.debug("Request to save Chat : {}", chatDTO);
		Chat chat = chatMapper.toEntity(chatDTO);
		chat = chatRepository.save(chat);
		return chatMapper.toDto(chat);
	}

	/**
	 * Get all the chats.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ChatDTO> findAll() {
		log.debug("Request to get all Chats");
		return chatRepository.findAll().stream().map(chatMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one chat by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ChatDTO> findOne(Long id) {
		log.debug("Request to get Chat : {}", id);
		return chatRepository.findById(id).map(chatMapper::toDto);
	}

	/**
	 * Delete the chat by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Chat : {}", id);
		chatRepository.deleteById(id);
	}

	/**
	 * Create a new chat.
	 *
	 * @param chatDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ChatDetalleDTO saveMensajeChat(MessageChatDTO msgChatDTO) {
		ChatDTO chat = chatRepository
				.findOneByPedidoProveedorIdAndTipoChatId(msgChatDTO.getPedidoProveedorId(), msgChatDTO.getTipoChatId())
				.map(chatMapper::toDto).orElse(null);
		
		if(chat == null) {
			chat = new ChatDTO();
			chat.setFecha(LocalDateTime.now());
			chat.setPedidoProveedorId(msgChatDTO.getPedidoProveedorId());
			chat.setTipoChatId(msgChatDTO.getTipoChatId());
			if (msgChatDTO.getTipoUsuarioId() == 2L) { //Cliente
				chat.setUsuarioReceptorLogin(msgChatDTO.getFrom());
				chat.setUsuarioEmisorLogin(msgChatDTO.getTo());
			} else {
				chat.setUsuarioEmisorLogin(msgChatDTO.getFrom());
				chat.setUsuarioReceptorLogin(msgChatDTO.getTo());
			}
			chat = this.save(chat);
		}
		
		ChatDetalleDTO detalleDTO = new ChatDetalleDTO();
		detalleDTO.setChatId(chat.getId());
		detalleDTO.setMensaje(msgChatDTO.getText());
		detalleDTO.setFecha(LocalDateTime.now());
		if (msgChatDTO.getTipoUsuarioId() == 2L) { //Cliente
			detalleDTO.setUsuarioReceptorLogin(msgChatDTO.getFrom());
			detalleDTO.setUsuarioEmisorLogin(msgChatDTO.getTo());
		} else {
			detalleDTO.setUsuarioEmisorLogin(msgChatDTO.getFrom());
			detalleDTO.setUsuarioReceptorLogin(msgChatDTO.getTo());
		}
		
		return chatDetalleService.save(detalleDTO);
	}
	
}
