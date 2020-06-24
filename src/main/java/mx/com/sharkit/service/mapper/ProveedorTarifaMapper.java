package mx.com.sharkit.service.mapper;

import org.mapstruct.Mapper;

import mx.com.sharkit.domain.ProveedorTarifa;
import mx.com.sharkit.service.dto.ProveedorTarifaDTO;

/**
 * Mapper for the entity {@link ProveedorTarifa} and its DTO
 * {@link ProveedorTarifaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedorTarifaMapper extends EntityMapper<ProveedorTarifaDTO, ProveedorTarifa> {

	ProveedorTarifaDTO toDto(ProveedorTarifa proveedorTarifa);

	ProveedorTarifa toEntity(ProveedorTarifaDTO proveedorTarifaDTO);

	default ProveedorTarifa fromId(Long id) {
		if (id == null) {
			return null;
		}
		ProveedorTarifa proveedorTarifa = new ProveedorTarifa();
		proveedorTarifa.setId(id);
		return proveedorTarifa;
	}
}
