package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

public class PedidoAltaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<PedidoDetalleAltaDTO> productos;
	
	private Long usuarioId;

	public List<PedidoDetalleAltaDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<PedidoDetalleAltaDTO> productos) {
		this.productos = productos;
	}
	

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString() {
		return "PedidoAltaDTO [productos=" + productos + ", usuarioId=" + usuarioId + "]";
	}
	
}
