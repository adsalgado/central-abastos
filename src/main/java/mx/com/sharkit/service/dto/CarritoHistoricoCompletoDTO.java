package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CarritoHistoricoCompletoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	private Long clienteId;
	private BigDecimal total;
	private BigDecimal totalSinComisionStripe;
	private BigDecimal totalProductos;
	private BigDecimal totalComisionTransporte;
	private BigDecimal comisionStripe;
	private List<CarritoHistoricoProveedorDTO> listHistoricoProveedores;

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

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

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

	public List<CarritoHistoricoProveedorDTO> getListHistoricoProveedores() {
		return listHistoricoProveedores;
	}

	public void setListHistoricoProveedores(List<CarritoHistoricoProveedorDTO> listHistoricoProveedores) {
		this.listHistoricoProveedores = listHistoricoProveedores;
	}

	@Override
	public String toString() {
		return "CarritoHistoricoCompletoDTO [id=" + id + ", nombre=" + nombre + ", clienteId=" + clienteId + ", total="
				+ total + ", totalSinComisionStripe=" + totalSinComisionStripe + ", totalProductos=" + totalProductos
				+ ", totalComisionTransporte=" + totalComisionTransporte + ", comisionStripe=" + comisionStripe
				+ ", listHistoricoProveedores=" + listHistoricoProveedores + "]";
	}

}
