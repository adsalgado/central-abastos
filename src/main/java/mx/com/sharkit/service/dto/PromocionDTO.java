package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Categoria} entity.
 */
public class PromocionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String titulo;

    @Size(max = 512)
    private String descripcion;

    @Size(max = 256)
    private String link;

    private Long usuarioAltaId;

    private Long usuarioModificacionId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaModificacion;
    
    private Long adjuntoId;

    private AdjuntoDTO adjunto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

        PromocionDTO categoriaDTO = (PromocionDTO) o;
        if (categoriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "PromocionDTO [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", link=" + link
				+ ", usuarioAltaId=" + usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId
				+ ", fechaAlta=" + fechaAlta + ", fechaModificacion=" + fechaModificacion + ", adjuntoId=" + adjuntoId
				+ "]";
	}


}
