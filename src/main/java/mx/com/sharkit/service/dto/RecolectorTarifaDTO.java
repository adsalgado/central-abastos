package mx.com.sharkit.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.RecolectorTarifa} entity.
 */
public class RecolectorTarifaDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal rangoMinimo;

    @NotNull
    private BigDecimal rangoMaximo;

    @NotNull
    private BigDecimal precio;


    private Long recolectorId;

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getRecolectorId() {
        return recolectorId;
    }

    public void setRecolectorId(Long recolectorId) {
        this.recolectorId = recolectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecolectorTarifaDTO recolectorTarifaDTO = (RecolectorTarifaDTO) o;
        if (recolectorTarifaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recolectorTarifaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecolectorTarifaDTO{" +
            "id=" + getId() +
            ", rangoMinimo=" + getRangoMinimo() +
            ", rangoMaximo=" + getRangoMaximo() +
            ", precio=" + getPrecio() +
            ", recolector=" + getRecolectorId() +
            "}";
    }
}
