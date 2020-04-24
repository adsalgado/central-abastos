package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.UsuarioImagen} entity.
 */
public class UsuarioDireccionDTO implements Serializable {

    private Long id;

    private Instant fechaAlta;
    
    private Long usuarioId;

    private DireccionDTO direccion;
    
    private Long direccionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }


    public DireccionDTO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDTO direccion) {
		this.direccion = direccion;
	}

	public Long getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Long direccionId) {
		this.direccionId = direccionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioDireccionDTO usuarioImagenDTO = (UsuarioDireccionDTO) o;
        if (usuarioImagenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuarioImagenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuarioImagenDTO{" +
            "id=" + getId() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", usuario=" + getUsuarioId() +
            ", direccion=" + getDireccionId() +
            "}";
    }
}
