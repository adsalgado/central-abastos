package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link mx.com.sharkit.domain.CarritoHistorico} entity.
 */
public class CarritoHistoricoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String nombre;

    private LocalDateTime fechaAlta;

    private Long clienteId;
    
    private List<CarritoHistoricoDetalleDTO> carritoHistoricoDetalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<CarritoHistoricoDetalleDTO> getCarritoHistoricoDetalles() {
		return carritoHistoricoDetalles;
	}

	public void setCarritoHistoricoDetalles(List<CarritoHistoricoDetalleDTO> carritoHistoricoDetalles) {
		this.carritoHistoricoDetalles = carritoHistoricoDetalles;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoHistoricoDTO carritoHistoricoDTO = (CarritoHistoricoDTO) o;
        if (carritoHistoricoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoHistoricoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarritoHistoricoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", cliente=" + getClienteId() +
            "}";
    }
}
