package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PedidoDetalleAltaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productoProveedorId;
	
	private BigDecimal cantidad;

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidad == null) ? 0 : cantidad.hashCode());
		result = prime * result + ((productoProveedorId == null) ? 0 : productoProveedorId.hashCode());
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
		PedidoDetalleAltaDTO other = (PedidoDetalleAltaDTO) obj;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (productoProveedorId == null) {
			if (other.productoProveedorId != null)
				return false;
		} else if (!productoProveedorId.equals(other.productoProveedorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedidoDetalleAltaDTO [productoProveedorId=" + productoProveedorId + ", cantidad=" + cantidad + "]";
	}

}
