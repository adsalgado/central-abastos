package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto que trae los productos por tipo de articulo
 * 
 * @author macpro
 *
 */
public class ProductoTipoArticuloDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoArticuloDTO tipoArticulo;
	
	private List<ProductoDTO> productos;


	public TipoArticuloDTO getTipoArticulo() {
		return tipoArticulo;
	}

	public void setTipoArticulo(TipoArticuloDTO tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	public List<ProductoDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "ProductoTipoArticuloDTO [tipoArticulo=" + tipoArticulo + ", productos=" + productos + "]";
	}

}
