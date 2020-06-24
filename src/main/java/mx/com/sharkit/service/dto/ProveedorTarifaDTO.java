package mx.com.sharkit.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.TransportistaTarifa} entity.
 */
public class ProveedorTarifaDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal rangoMinimo;

    @NotNull
    private BigDecimal rangoMaximo;

    @NotNull
    private BigDecimal porcentajeComision;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getRangoMinimo() {
		return rangoMinimo;
	}

	public void setRangoMinimo(BigDecimal rangoMinimo) {
		this.rangoMinimo = rangoMinimo;
	}

	public BigDecimal getRangoMaximo() {
		return rangoMaximo;
	}

	public void setRangoMaximo(BigDecimal rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

	public BigDecimal getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(BigDecimal porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorTarifaDTO transportistaTarifaDTO = (ProveedorTarifaDTO) o;
        if (transportistaTarifaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transportistaTarifaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ProveedorTarifaDTO [id=" + id + ", rangoMinimo=" + rangoMinimo + ", rangoMaximo=" + rangoMaximo
				+ ", porcentajeComision=" + porcentajeComision + "]";
	}

}
