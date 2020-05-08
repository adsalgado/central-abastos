package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.ChatDTO;
import mx.com.sharkit.service.dto.ChatDetalleDTO;
import mx.com.sharkit.web.websocket.dto.MessageChatDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Chat}.
 */
public interface ChatService {

	/**
	 * Save a chat.
	 *
	 * @param chatDTO the entity to save.
	 * @return the persisted entity.
	 */
	ChatDTO save(ChatDTO chatDTO);

	/**
	 * Get all the chats.
	 *
	 * @return the list of entities.
	 */
	List<ChatDTO> findAll();

	/**
	 * Get the "id" chat.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ChatDTO> findOne(Long id);

	/**
	 * Delete the "id" chat.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Save a chat.
	 *
	 * @param chatDTO the entity to save.
	 * @return the persisted entity.
	 */
	ChatDetalleDTO saveMensajeChat(MessageChatDTO chatDTO);

	/**
	 * Get the chat ByPedidoProveedorIdAndTipoChatId
	 *
	 * @param pedidoProveedorId the pedidoProveedorId of the entity.
	 * @param tipoChatId        the tipoChatId of the entity.
	 * @return the entity.
	 */
	Optional<ChatDTO> findOneByPedidoProveedorIdAndTipoChatId(Long pedidoProveedorId, Long tipoChatId);

	/**
	 * Get the chat ByPedidoProveedorIdAndTipoChatId
	 *
	 * @param pedidoProveedorId the pedidoProveedorId of the entity.
	 * @param tipoChatId        the tipoChatId of the entity.
	 * @return the entity.
	 */
	ChatDTO getOrCreateChatPedidoProveedor(Long pedidoProveedorId, Long tipoChatId) throws Exception;

}
