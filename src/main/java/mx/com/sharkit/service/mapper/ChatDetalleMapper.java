package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Chat;
import mx.com.sharkit.domain.ChatDetalle;
import mx.com.sharkit.service.dto.ChatDetalleDTO;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdjuntoMapper.class, ChatMapper.class})
public interface ChatDetalleMapper extends EntityMapper<ChatDetalleDTO, ChatDetalle> {

    ChatDetalleDTO toDto(ChatDetalle chat);

    ChatDetalle toEntity(ChatDetalleDTO chatDetalleDTO);

    default ChatDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChatDetalle chat = new ChatDetalle();
        chat.setId(id);
        return chat;
    }
    
}
