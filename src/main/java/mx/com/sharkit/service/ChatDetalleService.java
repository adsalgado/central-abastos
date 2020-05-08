package mx.com.sharkit.service;

import java.util.List;
import java.util.Optional;

import mx.com.sharkit.service.dto.ChatDetalleDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.ChatDetalle}.
 */
public interface ChatDetalleService {

    /**
     * Save a chat.
     *
     * @param chatDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    ChatDetalleDTO save(ChatDetalleDTO chatDetalleDTO);

    /**
     * Get all the chats.
     *
     * @return the list of entities.
     */
    List<ChatDetalleDTO> findAll();


    /**
     * Get the "id" chat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" chat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<ChatDetalleDTO> findByChatIdOrderById(Long chatId);
    
}
