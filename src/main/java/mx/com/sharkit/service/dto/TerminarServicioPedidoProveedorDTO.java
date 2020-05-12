package mx.com.sharkit.service.dto;

import java.io.Serializable;

public class TerminarServicioPedidoProveedorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long pedidoProveedorId;
	
	private String token;

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TerminarServicioPedidoProveedorDTO [pedidoProveedorId=" + pedidoProveedorId + ", token=" + token + "]";
	}

}
