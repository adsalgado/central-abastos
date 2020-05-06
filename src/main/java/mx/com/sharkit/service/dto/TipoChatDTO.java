package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link mx.com.sharkit.domain.TipoChat} entity.
 */
public class TipoChatDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 45)
    private String nombre;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoChatDTO tipoOfertaDTO = (TipoChatDTO) o;
        if (tipoOfertaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoOfertaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDireccionDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
