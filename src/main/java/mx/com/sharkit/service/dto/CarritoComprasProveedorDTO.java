package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarritoComprasProveedorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal total;
	private ProveedorDTO proveedor;
	private List<CarritoCompraDTO> listCarrito = new ArrayList<>();

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public List<CarritoCompraDTO> getListCarrito() {
		return listCarrito;
	}

	public void setListCarrito(List<CarritoCompraDTO> listCarrito) {
		this.listCarrito = listCarrito;
	}

	@Override
	public String toString() {
		return "CarritoComprasProveedorDTO [total=" + total + ", proveedor=" + proveedor + ", listCarrito="
				+ listCarrito + "]";
	}

}
