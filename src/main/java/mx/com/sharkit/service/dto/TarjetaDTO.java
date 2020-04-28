package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Tarjeta} entity.
 */
public class TarjetaDTO implements Serializable {

    private Long id;
    
    @Size(max = 45)
    private String alias;

    @NotNull
    @Size(max = 20)
    private String numeroTarjeta;

    @NotNull
    @Size(max = 10)
    private String fechaCaducidad;

    @NotNull
    @Size(max = 3)
    private String numeroSeguridad;
    
    private Long usuarioId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getNumeroSeguridad() {
		return numeroSeguridad;
	}

	public void setNumeroSeguridad(String numeroSeguridad) {
		this.numeroSeguridad = numeroSeguridad;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarjetaDTO tarjetaDTO = (TarjetaDTO) o;
        if (tarjetaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarjetaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "TarjetaDTO [id=" + id + ", alias=" + alias + ", numeroTarjeta=" + numeroTarjeta + ", fechaCaducidad="
				+ fechaCaducidad + ", numeroSeguridad=" + numeroSeguridad + ", usuarioId=" + usuarioId + ", fechaAlta="
				+ fechaAlta + "]";
	}

}
