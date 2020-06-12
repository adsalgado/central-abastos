package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Queja} entity.
 */
public class QuejaDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tipoUsuarioId;
    
	private TipoUsuarioDTO tipoUsuario;

	@NotNull
    private Long usuarioId;
    
 	private UserDTO usuario;

 	@NotNull
    private Long pedidoProveedorId;
    
	private PedidoProveedorDTO pedidoProveedor;

    private Long estatusId;
    
	private EstatusDTO estatus;

    private String tokenWeb;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", locale = "es_MX")
	private LocalDateTime fechaAlta;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	public TipoUsuarioDTO getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
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

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public PedidoProveedorDTO getPedidoProveedor() {
		return pedidoProveedor;
	}

	public void setPedidoProveedor(PedidoProveedorDTO pedidoProveedor) {
		this.pedidoProveedor = pedidoProveedor;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getTokenWeb() {
		return tokenWeb;
	}

	public void setTokenWeb(String tokenWeb) {
		this.tokenWeb = tokenWeb;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuejaDTO quejaDTO = (QuejaDTO) o;
        if (quejaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quejaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "QuejaDTO [id=" + id + ", tipoUsuarioId=" + tipoUsuarioId + ", tipoUsuario=" + tipoUsuario
				+ ", usuarioId=" + usuarioId + ", usuario=" + usuario + ", pedidoProveedorId=" + pedidoProveedorId
				+ ", pedidoProveedor=" + pedidoProveedor + ", estatusId=" + estatusId + ", estatus=" + estatus
				+ ", fechaAlta=" + fechaAlta + "]";
	}

}
