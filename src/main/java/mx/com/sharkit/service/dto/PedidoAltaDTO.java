package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

public class PedidoAltaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean picking;

	private String nombreContacto;

    private String telefonoContacto;

    private String correoContacto;
    
    private DireccionDTO direccionContacto;
    	
	private Long usuarioId;

	private List<PedidoDetalleAltaDTO> productos;
	
	public List<PedidoDetalleAltaDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<PedidoDetalleAltaDTO> productos) {
		this.productos = productos;
	}
	
	public boolean isPicking() {
		return picking;
	}

	public void setPicking(boolean picking) {
		this.picking = picking;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getCorreoContacto() {
		return correoContacto;
	}

	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}

	public DireccionDTO getDireccionContacto() {
		return direccionContacto;
	}

	public void setDireccionContacto(DireccionDTO direccionContacto) {
		this.direccionContacto = direccionContacto;
	}

	@Override
	public String toString() {
		return "PedidoAltaDTO [nombreContacto=" + nombreContacto + ", telefonoContacto=" + telefonoContacto
				+ ", correoContacto=" + correoContacto + ", direccionContacto=" + direccionContacto + ", usuarioId="
				+ usuarioId + ", productos=" + productos + "]";
	}
	
}
