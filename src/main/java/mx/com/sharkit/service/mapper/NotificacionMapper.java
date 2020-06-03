package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Notificacion;
import mx.com.sharkit.service.dto.NotificacionDTO;

/**
 * Mapper for the entity {@link Notificacion} and its DTO
 * {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {

	NotificacionDTO toDto(Notificacion notificacion);

	Notificacion toEntity(NotificacionDTO notificacionDTO);

	default Notificacion fromId(Long id) {
		if (id == null) {
			return null;
		}
		Notificacion notificacion = new Notificacion();
		notificacion.setId(id);
		return notificacion;
	}
}
