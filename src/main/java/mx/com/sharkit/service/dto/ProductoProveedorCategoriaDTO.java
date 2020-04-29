package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto que trae los productos por categoria
 * 
 * @author macpro
 *
 */
public class ProductoProveedorCategoriaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2744424467474420452L;

	private CategoriaDTO categoria;
	
	private List<ProductoProveedorDTO> productos;

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public List<ProductoProveedorDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoProveedorDTO> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "ProductoCategoriaDTO [categoria=" + categoria + ", productos=" + productos + "]";
	}

}
