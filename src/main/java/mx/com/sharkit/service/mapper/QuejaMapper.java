package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.Queja;
import mx.com.sharkit.service.dto.QuejaDTO;

/**
 * Mapper for the entity {@link Queja} and its DTO {@link QuejaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoUsuarioMapper.class, UserMapper.class, PedidoProveedorMapper.class, EstatusMapper.class})
public interface QuejaMapper extends EntityMapper<QuejaDTO, Queja> {

	QuejaDTO toDto(Queja queja);

	Queja toEntity(QuejaDTO quejaDTO);

    default Queja fromId(Long id) {
        if (id == null) {
            return null;
        }
        Queja queja = new Queja();
        queja.setId(id);
        return queja;
    }
}
