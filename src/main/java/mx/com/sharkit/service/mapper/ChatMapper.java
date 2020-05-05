package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Chat;
import mx.com.sharkit.service.dto.ChatDTO;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdjuntoMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {

    ChatDTO toDto(Chat chat);

    Chat toEntity(ChatDTO chatDTO);

    default Chat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }
}
