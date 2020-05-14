package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Transportista} entity.
 */
public class TransportistaDTO implements Serializable {

    private Long id;

    private UserDTO usuario;
    
	private Long usuarioId;

    private String nombre;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaAlta;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaModificacion;

	private Long usuarioAltaId;

	private Long usuarioModificacionId;

	private Long empresaId;

    private DireccionDTO direccion;

    private Long direccionId;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombre() {
		return nombre;
	}

	public UserDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserDTO usuario) {
		this.usuario = usuario;
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

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
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

        TransportistaDTO transportistaDTO = (TransportistaDTO) o;
        if (transportistaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transportistaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "TransportistaDTO [id=" + id + ", usuarioId=" + usuarioId + ", nombre=" + nombre + ", fechaAlta="
				+ fechaAlta + ", fechaModificacion=" + fechaModificacion + ", usuarioAltaId=" + usuarioAltaId
				+ ", usuarioModificacionId=" + usuarioModificacionId + ", empresaId=" + empresaId + ", direccion="
				+ direccion + ", direccionId=" + direccionId + "]";
	}

}
