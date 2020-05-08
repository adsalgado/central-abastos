package mx.com.sharkit.service.dto;

import java.io.Serializable;

public class ChangeEstatusPedidoProveedorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long pedidoProveedorId;
	
	private Long estatusId;

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

	@Override
	public String toString() {
		return "ChangeEstatusPedidoProveedorDTO [pedidoProveedorId=" + pedidoProveedorId + ", estatusId=" + estatusId
				+ "]";
	}

}
