package mx.com.sharkit.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Notificacion.
 */
@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private User usuario;
    
    @Column(name = "view_id")
    private Integer viewId;
    
	@Size(max = 128)
    @Column(name = "titulo", length = 128)
    private String titulo;

	@Size(max = 256)
    @Column(name = "descripcion", length = 256)
    private String descripcion;

	@Size(max = 256)
    @Column(name = "parametros", length = 256)
    private String parametros;

    @Column(name = "fecha_notificacion")
    private LocalDateTime fechaNotificacion;
    
    @Column(name = "estatus")
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

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return id != null && id.equals(((Notificacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", usuarioId=" + usuarioId + ", usuario=" + usuario + ", viewId=" + viewId
				+ ", titulo=" + titulo + ", descripcion=" + descripcion + ", parametros=" + parametros
				+ ", fechaNotificacion=" + fechaNotificacion + ", estatus=" + estatus + "]";
	}

}
