package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CarritoComprasCompletoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal total;
	private BigDecimal totalSinComisionStripe;
	private BigDecimal totalProductos;
	private BigDecimal totalComisionTransporte;
	private BigDecimal comisionStripe;
	private List<CarritoComprasProveedorDTO> listCarritoProveedores;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalSinComisionStripe() {
		return totalSinComisionStripe;
	}

	public void setTotalSinComisionStripe(BigDecimal totalSinComisionStripe) {
		this.totalSinComisionStripe = totalSinComisionStripe;
	}

	public BigDecimal getTotalProductos() {
		return totalProductos;
	}

	public void setTotalProductos(BigDecimal totalProductos) {
		this.totalProductos = totalProductos;
	}

	public BigDecimal getTotalComisionTransporte() {
		return totalComisionTransporte;
	}

	public void setTotalComisionTransporte(BigDecimal totalComisionTransporte) {
		this.totalComisionTransporte = totalComisionTransporte;
	}

	public BigDecimal getComisionStripe() {
		return comisionStripe;
	}

	public void setComisionStripe(BigDecimal comisionStripe) {
		this.comisionStripe = comisionStripe;
	}

	public List<CarritoComprasProveedorDTO> getListCarritoProveedores() {
		return listCarritoProveedores;
	}

	public void setListCarritoProveedores(List<CarritoComprasProveedorDTO> listCarritoProveedores) {
		this.listCarritoProveedores = listCarritoProveedores;
	}

}
