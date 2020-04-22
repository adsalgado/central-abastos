package mx.com.sharkit.service.mapper;

import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.ChatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdjuntoMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {

    @Mapping(source = "usuarioFuente.id", target = "usuarioFuenteId")
    @Mapping(source = "usuarioDestino.id", target = "usuarioDestinoId")
    @Mapping(source = "adjunto.id", target = "adjuntoId")
    ChatDTO toDto(Chat chat);

    @Mapping(source = "usuarioFuenteId", target = "usuarioFuente")
    @Mapping(source = "usuarioDestinoId", target = "usuarioDestino")
    @Mapping(source = "adjuntoId", target = "adjunto")
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
