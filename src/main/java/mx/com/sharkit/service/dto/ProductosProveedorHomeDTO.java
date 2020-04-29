package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Lista de productos por categoria
 * 
 * @author macpro
 *
 */
public class ProductosProveedorHomeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProductoProveedorCategoriaDTO> productosCategoria;

	public List<ProductoProveedorCategoriaDTO> getProductosCategoria() {
		return productosCategoria;
	}

	public void setProductosCategoria(List<ProductoProveedorCategoriaDTO> productosCategoria) {
		this.productosCategoria = productosCategoria;
	}

	@Override
	public String toString() {
		return "ProductosHomeDTO [productosCategoria=" + productosCategoria + "]";
	}
	
}
