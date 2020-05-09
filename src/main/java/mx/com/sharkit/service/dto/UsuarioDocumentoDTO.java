package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.domain.User;

/**
 * A DTO for the {@link mx.com.sharkit.domain.UsuarioDocumento} entity.
 */
public class UsuarioDocumentoDTO implements Serializable {

	private Long id;

	private User usuario;

	private Long usuarioId;

	private DocumentoDTO documento;

	private Long documentoId;

	private Long adjuntoId;

	private LocalDateTime fechaAlta;

	private Long usuarioAltaId;

	private LocalDateTime fechaModificacion;

	private Long usuarioModificacionId;
	
	private AdjuntoDTO adjunto;

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

	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
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

	public AdjuntoDTO getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(AdjuntoDTO adjunto) {
		this.adjunto = adjunto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UsuarioDocumentoDTO usuarioImagenDTO = (UsuarioDocumentoDTO) o;
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
		return "UsuarioDocumentoDTO [id=" + id + ", usuario=" + usuario + ", usuarioId=" + usuarioId + ", documento="
				+ documento + ", documentoId=" + documentoId + ", adjuntoId=" + adjuntoId + ", fechaAlta=" + fechaAlta
				+ ", usuarioAltaId=" + usuarioAltaId + ", fechaModificacion=" + fechaModificacion
				+ ", usuarioModificacionId=" + usuarioModificacionId + "]";
	}

}
