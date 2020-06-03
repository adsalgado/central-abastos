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
    private int viewId;
    
	@Size(max = 128)
    @Column(name = "titulo", length = 128, nullable = false)
    private String titulo;

	@Size(max = 256)
    @Column(name = "descripcion", length = 256, nullable = false)
    private String descripcion;
    
    @Column(name = "fecha_notificacion")
    private LocalDateTime fechaNotificacion;
    
    @Column(name = "estatus")
    private int estatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
				+ ", titulo=" + titulo + ", descripcion=" + descripcion + ", fechaNotificacion=" + fechaNotificacion
				+ ", estatus=" + estatus + "]";
	}

}
