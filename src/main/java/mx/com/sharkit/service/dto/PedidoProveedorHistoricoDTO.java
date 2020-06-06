package mx.com.sharkit.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PedidoProveedorHistoricoDTO {

    private Long id;
    
    private Long pedidoProveedorId;

    private Long estatusId;

    private EstatusDTO estatus;

    private Long usuarioId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fecha;
    
    private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoProveedorHistoricoDTO other = (PedidoProveedorHistoricoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedidoProveedorHistoricoDTO [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", estatusId="
				+ estatusId + ", estatus=" + estatus + ", usuarioId=" + usuarioId + ", fecha=" + fecha
				+ ", descripcion=" + descripcion + "]";
	}

}
