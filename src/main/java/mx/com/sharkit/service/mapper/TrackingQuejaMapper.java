package mx.com.sharkit.service.mapper;


import mx.com.sharkit.domain.*;
import mx.com.sharkit.service.dto.TrackingQuejaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrackingQueja} and its DTO {@link TrackingQuejaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, QuejaMapper.class})
public interface TrackingQuejaMapper extends EntityMapper<TrackingQuejaDTO, TrackingQueja> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "queja.id", target = "quejaId")
    TrackingQuejaDTO toDto(TrackingQueja trackingQueja);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "quejaId", target = "queja")
    TrackingQueja toEntity(TrackingQuejaDTO trackingQuejaDTO);

    default TrackingQueja fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrackingQueja trackingQueja = new TrackingQueja();
        trackingQueja.setId(id);
        return trackingQueja;
    }
}
