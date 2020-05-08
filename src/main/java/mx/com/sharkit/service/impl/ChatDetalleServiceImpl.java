package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.ChatDetalle;
import mx.com.sharkit.repository.ChatDetalleRepository;
import mx.com.sharkit.service.ChatDetalleService;
import mx.com.sharkit.service.dto.ChatDetalleDTO;
import mx.com.sharkit.service.mapper.ChatDetalleMapper;

/**
 * Service Implementation for managing {@link ChatDetalle}.
 */
@Service
@Transactional
public class ChatDetalleServiceImpl implements ChatDetalleService {

	private final Logger log = LoggerFactory.getLogger(ChatDetalleServiceImpl.class);

	private final ChatDetalleRepository chatDetalleRepository;

	private final ChatDetalleMapper chatDetalleMapper;
	
	public ChatDetalleServiceImpl(ChatDetalleRepository chatDetalleRepository, ChatDetalleMapper chatDetalleMapper) {
		this.chatDetalleRepository = chatDetalleRepository;
		this.chatDetalleMapper = chatDetalleMapper;
	}

	/**
	 * Save a chat.
	 *
	 * @param chatDetalleDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ChatDetalleDTO save(ChatDetalleDTO chatDetalleDTO) {
		log.debug("Request to save Chat : {}", chatDetalleDTO);
		ChatDetalle chat = chatDetalleMapper.toEntity(chatDetalleDTO);
		chat = chatDetalleRepository.save(chat);
		return chatDetalleMapper.toDto(chat);
	}

	/**
	 * Get all the chats.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ChatDetalleDTO> findAll() {
		log.debug("Request to get all Chats");
		return chatDetalleRepository.findAll().stream().map(chatDetalleMapper::toDto)
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
	public Optional<ChatDetalleDTO> findOne(Long id) {
		log.debug("Request to get Chat : {}", id);
		return chatDetalleRepository.findById(id).map(chatDetalleMapper::toDto);
	}

	/**
	 * Delete the chat by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Chat : {}", id);
		chatDetalleRepository.deleteById(id);
	}

	@Override
	public List<ChatDetalleDTO> findByChatIdOrderById(Long chatId) {
		log.debug("Request to get all ChatDetalles");
		return chatDetalleRepository.findByChatIdOrderById(chatId).stream().map(chatDetalleMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

}
