package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Notificacion} entity.
 */
public class NotificacionDTO implements Serializable {

	private Long id;

	private Long usuarioId;

	private UserDTO usuario;

	private Integer viewId;

	private String titulo;

	private String descripcion;

	@JsonIgnore
	private String parametros;

	Map<String, Object> data;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime fechaNotificacion;

	private Integer estatus;

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

	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
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

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public LocalDateTime getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(LocalDateTime fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
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
				+ ", titulo=" + titulo + ", descripcion=" + descripcion + ", parametros=" + parametros
				+ ", fechaNotificacion=" + fechaNotificacion + ", estatus=" + estatus + "]";
	}

}
