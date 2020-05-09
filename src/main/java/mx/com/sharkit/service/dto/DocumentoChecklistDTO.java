package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.com.sharkit.domain.Documento;
import mx.com.sharkit.domain.TipoUsuario;

/**
 * A DTO for the {@link mx.com.sharkit.domain.DocumentoDTO} entity.
 */
public class DocumentoChecklistDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private TipoUsuarioDTO tipoUsuario;

	private Long tipoUsuarioId;

	private DocumentoDTO documento;

	private Long documentoId;

	private String requerido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUsuarioDTO getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
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

	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DocumentoChecklistDTO tipoOfertaDTO = (DocumentoChecklistDTO) o;
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
		return "DocumentoChecklistDTO [id=" + id + ", tipoUsuario=" + tipoUsuario + ", tipoUsuarioId=" + tipoUsuarioId
				+ ", documento=" + documento + ", documentoId=" + documentoId + ", requerido=" + requerido + "]";
	}

}
