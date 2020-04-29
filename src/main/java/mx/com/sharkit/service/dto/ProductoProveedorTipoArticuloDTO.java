package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto que trae los productos por tipo de articulo
 * 
 * @author macpro
 *
 */
public class ProductoProveedorTipoArticuloDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoArticuloDTO tipoArticulo;
	
	private List<ProductoProveedorDTO> productos;


	public TipoArticuloDTO getTipoArticulo() {
		return tipoArticulo;
	}

	public void setTipoArticulo(TipoArticuloDTO tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	public List<ProductoProveedorDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoProveedorDTO> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "ProductoTipoArticuloDTO [tipoArticulo=" + tipoArticulo + ", productos=" + productos + "]";
	}

}
