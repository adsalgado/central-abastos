package mx.com.sharkit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.criterion.Order;

import mx.com.sharkit.domain.ProductoProveedor;
import mx.com.sharkit.excel.objectbinding.domain.ProductoCargaDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProveedorDTO;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
public interface ProductoProveedorService extends BaseService<ProductoProveedor, Long> {

    /**
     * Save a productoProveedor.
     *
     * @param productoProveedorDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoProveedorDTO save(ProductoProveedorDTO productoProveedorDTO);

    /**
     * Get all the productoProveedors.
     *
     * @return the list of entities.
     */
    List<ProductoProveedorDTO> findAllDTO();


    /**
     * Get the "id" productoProveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" productoProveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<ProductoProveedorDTO> searchProductos(Map<String, Object> params, Integer page, Integer pagesize, Order order);
    
    void cargaMasivaProductosProveedor(List<ProductoCargaDTO> productosCarga, ProveedorDTO proveedor) throws Exception;
    
    List<ProductoProveedorDTO> findByProveedorId(Long proveedorId);

	ProductoProveedorDTO saveProductoProveedor(@Valid ProductoProveedorDTO productoProveedorDTO);

	ProductoProveedorDTO updateProductoProveedor(@Valid ProductoProveedorDTO productoProveedorDTO);
    
}
