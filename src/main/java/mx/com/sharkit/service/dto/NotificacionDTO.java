package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import mx.com.sharkit.domain.User;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Notificacion} entity.
 */
public class NotificacionDTO implements Serializable {

    private Long id;
    
    private Long usuarioId;
    
    private UserDTO usuario;
    
    private int viewId;
    
    private String titulo;

    private String descripcion;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaNotificacion;
    
    private int estatus;


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

	public UserDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserDTO usuario) {
		this.usuario = usuario;
	}

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(LocalDateTime fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificacionDTO notificacionDTO = (NotificacionDTO) o;
        if (notificacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "NotificacionDTO [id=" + id + ", usuarioId=" + usuarioId + ", usuario=" + usuario + ", viewId=" + viewId
				+ ", titulo=" + titulo + ", descripcion=" + descripcion + ", fechaNotificacion=" + fechaNotificacion
				+ ", estatus=" + estatus + "]";
	}

}
