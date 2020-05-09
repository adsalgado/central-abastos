package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A UsuarioDireccion.
 */
@Entity
@Table(name = "usuario_documento")
public class UsuarioDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, insertable = false, updatable = false)
    private User usuario;
    
    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "documento_id", insertable = false, updatable = false)
    private Documento documento;
    
    @Column(name = "documento_id")
    private Long documentoId;

    @Column(name = "adjunto_id")
    private Long adjuntoId;
    
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuario_modificacion_id")
    private Long usuarioModificacionId;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDocumento)) {
            return false;
        }
        return id != null && id.equals(((UsuarioDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "UsuarioDocumento [id=" + id + ", usuario=" + usuario + ", usuarioId=" + usuarioId + ", documento="
				+ documento + ", documentoId=" + documentoId + ", adjuntoId=" + adjuntoId + ", fechaAlta=" + fechaAlta
				+ ", usuarioAltaId=" + usuarioAltaId + ", fechaModificacion=" + fechaModificacion
				+ ", usuarioModificacionId=" + usuarioModificacionId + "]";
	}

}
