package mx.com.sharkit.service.dto;

import java.io.Serializable;

public class CalificacionPedidoProveedorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long pedidoProveedorId;
	
	private Integer calificacionServicio;

	private String comentarios;

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public Integer getCalificacionServicio() {
		return calificacionServicio;
	}

	public void setCalificacionServicio(Integer calificacionServicio) {
		this.calificacionServicio = calificacionServicio;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "CalificacionPedidoProveedorDTO [pedidoProveedorId=" + pedidoProveedorId + ", calificacionServicio="
				+ calificacionServicio + ", comentarios=" + comentarios + "]";
	}

}
