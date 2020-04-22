package mx.com.sharkit.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import mx.com.sharkit.domain.enumeration.TipoEstatus;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Estatus} entity.
 */
public class EstatusDTO implements Serializable {

    private Long id;

    private TipoEstatus tipoEstatus;

    @NotNull
    @Size(max = 128)
    private String nombre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEstatus getTipoEstatus() {
        return tipoEstatus;
    }

    public void setTipoEstatus(TipoEstatus tipoEstatus) {
        this.tipoEstatus = tipoEstatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstatusDTO estatusDTO = (EstatusDTO) o;
        if (estatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstatusDTO{" +
            "id=" + getId() +
            ", tipoEstatus='" + getTipoEstatus() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
