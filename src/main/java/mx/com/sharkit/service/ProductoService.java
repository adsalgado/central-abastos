package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import mx.com.sharkit.service.dto.ProductoDTO;
import mx.com.sharkit.service.dto.ProductosHomeDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.Producto}.
 */
public interface ProductoService {

	/**
	 * Save a producto.
	 *
	 * @param productoDTO the entity to save.
	 * @return the persisted entity.
	 */
	ProductoDTO save(ProductoDTO productoDTO);

	/**
	 * Get all the productos.
	 *
	 * @return the list of entities.
	 */
	List<ProductoDTO> findAll();

	/**
	 * Get the "id" producto.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ProductoDTO> findOne(Long id);

	/**
	 * Delete the "id" producto.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the productos.
	 *
	 * @return the list of entities.
	 */
	List<ProductoDTO> searchProductos(Map<String, Object> params, Pageable pageable);

	/**
	 * Recupera las imágenes del producto
	 * 
	 * @param productoId Id del producto
	 * @return Lista de imágenes
	 */
	List<ProductoDTO> getImagenesProducto(Long productoId);

	/**
	 * Recupera los productos por categoria
	 * 
	 * @param seccionId id de la seccion
	 * @param queryString parametros de búsqueda
	 * @return Lista de productos
	 */
	ProductosHomeDTO getProductosCategoria(Long seccionId, String queryString);

}
