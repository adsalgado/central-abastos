package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Lista de productos por categoria
 * 
 * @author macpro
 *
 */
public class ProductosHomeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProductoCategoriaDTO> productosCategoria;

	public List<ProductoCategoriaDTO> getProductosCategoria() {
		return productosCategoria;
	}

	public void setProductosCategoria(List<ProductoCategoriaDTO> productosCategoria) {
		this.productosCategoria = productosCategoria;
	}

	@Override
	public String toString() {
		return "ProductosHomeDTO [productosCategoria=" + productosCategoria + "]";
	}
	
}
