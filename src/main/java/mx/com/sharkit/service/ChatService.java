package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.ChatDTO;

import java.util.List;
import java.util.Optional;

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
}
