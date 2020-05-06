package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Pedido} entity.
 */
public class PedidoProveedorDTO implements Serializable {

    private Long id;
    
    private Long pedidoId;

    private ProveedorDTO proveedor;
    
    private Long proveedorId;
    
    private EstatusDTO estatus;
    
    private Long estatusId;

    private Long transportistaId;

    private Long recolectorId;
    
    private BigDecimal total;

    private BigDecimal totalSinIva;

    private BigDecimal comisionTransportista;

    private BigDecimal comisionPreparador;
    
    private Long usuarioAltaId;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDateTime fechaAlta;

    private Long usuarioModificacionId;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDateTime fechaModificacion;

    private List<PedidoDetalleDTO> pedidoDetalles = new ArrayList<>();
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public Long getTransportistaId() {
		return transportistaId;
	}

	public void setTransportistaId(Long transportistaId) {
		this.transportistaId = transportistaId;
	}

	public Long getRecolectorId() {
		return recolectorId;
	}

	public void setRecolectorId(Long recolectorId) {
		this.recolectorId = recolectorId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalSinIva() {
		return totalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		this.totalSinIva = totalSinIva;
	}

	public BigDecimal getComisionTransportista() {
		return comisionTransportista;
	}

	public void setComisionTransportista(BigDecimal comisionTransportista) {
		this.comisionTransportista = comisionTransportista;
	}

	public BigDecimal getComisionPreparador() {
		return comisionPreparador;
	}

	public void setComisionPreparador(BigDecimal comisionPreparador) {
		this.comisionPreparador = comisionPreparador;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public List<PedidoDetalleDTO> getPedidoDetalles() {
		return pedidoDetalles;
	}

	public void setPedidoDetalles(List<PedidoDetalleDTO> pedidoDetalles) {
		this.pedidoDetalles = pedidoDetalles;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoProveedorDTO pedidoDTO = (PedidoProveedorDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "PedidoProveedorDTO [id=" + id + ", pedidoId=" + pedidoId + ", proveedorId=" + proveedorId
				+ ", estatusId=" + estatusId + ", transportistaId=" + transportistaId + ", recolectorId=" + recolectorId
				+ ", total=" + total + ", totalSinIva=" + totalSinIva + ", comisionTransportista="
				+ comisionTransportista + ", comisionPreparador=" + comisionPreparador + ", usuarioAltaId="
				+ usuarioAltaId + ", fechaAlta=" + fechaAlta + ", usuarioModificacionId=" + usuarioModificacionId
				+ ", fechaModificacion=" + fechaModificacion + "]";
	}

}
