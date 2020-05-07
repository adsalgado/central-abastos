package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link mx.com.sharkit.domain.PedidoDetalle} entity.
 */
public class PedidoDetalleDTO implements Serializable {

    private Long id;
    
	private String folio;

    private Long pedidoProveedorId;

    private Long productoProveedorId;

    private ProductoProveedorDTO productoProveedor;
    
    private EstatusDTO estatus;

    private Long estatusId;

    private BigDecimal cantidad;

    private BigDecimal totalSinIva;

    private BigDecimal total;

    private BigDecimal precioSinIva;

    private BigDecimal precio;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public ProductoProveedorDTO getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedorDTO productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public BigDecimal getPrecioSinIva() {
		return precioSinIva;
	}

	public void setPrecioSinIva(BigDecimal precioSinIva) {
		this.precioSinIva = precioSinIva;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
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
				+ totalSinIva + ", folio=" + folio + ", total=" + total + ", precioSinIva=" + precioSinIva + ", precio=" + precio + "]";
	}


}
