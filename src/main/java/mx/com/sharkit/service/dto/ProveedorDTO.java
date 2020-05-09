package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Proveedor} entity.
 */
public class ProveedorDTO implements Serializable {

    private Long id;

	private UserDTO usuario;

    private Long usuarioId;
    
    @NotNull
    @Size(max = 256)
    private String nombre;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es")
    private LocalDateTime fechaAlta;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es")
    private LocalDateTime fechaModificacion;

    private EmpresaDTO empresa;

    private Long empresaId;

    private DireccionDTO direccion;

    private Long direccionId;

    private Long usuarioAltaId;

    private Long usuarioModificacionId;
    
    private TransportistaDTO transportista;

    private Long transportistaId;


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

    public Long getUsuarioAltaId() {
        return usuarioAltaId;
    }

    public void setUsuarioAltaId(Long userId) {
        this.usuarioAltaId = userId;
    }

    public Long getUsuarioModificacionId() {
        return usuarioModificacionId;
    }

    public void setUsuarioModificacionId(Long userId) {
        this.usuarioModificacionId = userId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
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

	public TransportistaDTO getTransportista() {
		return transportista;
	}

	public void setTransportista(TransportistaDTO transportista) {
		this.transportista = transportista;
	}

	public Long getTransportistaId() {
		return transportistaId;
	}

	public void setTransportistaId(Long transportistaId) {
		this.transportistaId = transportistaId;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if (proveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ProveedorDTO [id=" + id + ", usuario=" + usuario + ", usuarioId=" + usuarioId + ", nombre=" + nombre
				+ ", fechaAlta=" + fechaAlta + ", fechaModificacion=" + fechaModificacion + ", empresa=" + empresa
				+ ", empresaId=" + empresaId + ", direccion=" + direccion + ", direccionId=" + direccionId
				+ ", usuarioAltaId=" + usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId
				+ ", transportista=" + transportista + ", transportistaId=" + transportistaId + "]";
	}

}
