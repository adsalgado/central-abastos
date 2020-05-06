package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.TipoChat;
import mx.com.sharkit.service.dto.TipoChatDTO;

/**
 * Mapper for the entity {@link TipoChat} and its DTO {@link TipoChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoChatMapper extends EntityMapper<TipoChatDTO, TipoChat> {

	TipoChatDTO toDto(TipoChat tipoChat);
	
    TipoChat toEntity(TipoChatDTO tipoChatDTO);

    default TipoChat fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoChat tipoChat = new TipoChat();
        tipoChat.setId(id);
        return tipoChat;
    }
}
