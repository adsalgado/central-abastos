package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.TipoArticulo} entity.
 */
public class TipoArticuloDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String nombre;

    @Size(max = 256)
    private String descripcion;

    private Long usuarioAltaId;

    private Long usuarioModificacionId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaModificacion;

    private CategoriaDTO categoria;
    
    private Long categoriaId;
    
    private Long adjuntoId;

    private AdjuntoDTO adjunto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
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

        TipoArticuloDTO tipoArticuloDTO = (TipoArticuloDTO) o;
        if (tipoArticuloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoArticuloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "TipoArticuloDTO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", usuarioAltaId="
				+ usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId + ", fechaAlta=" + fechaAlta
				+ ", fechaModificacion=" + fechaModificacion + ", categoriaId=" + categoriaId + ", adjuntoId="
				+ adjuntoId + "]";
	}

}
