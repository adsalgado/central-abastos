package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.UsuarioImagen} entity.
 */
public class UsuarioDireccionDTO implements Serializable {

    private Long id;

    private String alias;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;
    
    private Long usuarioId;

    private DireccionDTO direccion;
    
    private Long direccionId;

    private Long usuarioAltaId;
    
    private TipoDireccionDTO tipoDireccion;
    
    private Long tipoDireccionId;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
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

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public TipoDireccionDTO getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(TipoDireccionDTO tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public Long getTipoDireccionId() {
		return tipoDireccionId;
	}

	public void setTipoDireccionId(Long tipoDireccionId) {
		this.tipoDireccionId = tipoDireccionId;
	}

	public String getFavorita() {
		return favorita;
	}

	public void setFavorita(String favorita) {
		this.favorita = favorita;
	}

	@Size(max = 1)
    private String favorita;

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioDireccionDTO usuarioImagenDTO = (UsuarioDireccionDTO) o;
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
		return "UsuarioDireccionDTO [id=" + id + ", fechaAlta=" + fechaAlta + ", usuarioId=" + usuarioId
				+ ", direccionId=" + direccionId + ", usuarioAltaId=" + usuarioAltaId + ", tipoDireccionId="
				+ tipoDireccionId + ", favorita=" + favorita + "]";
	}

}
