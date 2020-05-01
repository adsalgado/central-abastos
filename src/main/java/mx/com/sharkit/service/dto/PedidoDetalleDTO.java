package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.PedidoDetalle} entity.
 */
public class PedidoDetalleDTO implements Serializable {

    private Long id;

    private Long pedidoProveedorId;

    private PedidoProveedorDTO pedidoProveedor;

    private Long productoProveedorId;

    private ProductoProveedorDTO productoProveedor;
    
    private EstatusDTO estatus;

    private Long estatusId;

    private BigDecimal cantidad;

    private BigDecimal totalSinIva;

    private BigDecimal total;



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

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getTotalSinIva() {
		return totalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		this.totalSinIva = totalSinIva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDetalleDTO pedidoDetalleDTO = (PedidoDetalleDTO) o;
        if (pedidoDetalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDetalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "PedidoDetalleDTO [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", productoProveedorId="
				+ productoProveedorId + ", estatusId=" + estatusId + ", cantidad=" + cantidad + ", totalSinIva="
				+ totalSinIva + ", total=" + total + "]";
	}

}
