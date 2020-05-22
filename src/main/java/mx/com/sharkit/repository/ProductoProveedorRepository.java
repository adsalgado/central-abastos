package mx.com.sharkit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.ProductoProveedor;

/**
 * Spring Data repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoProveedorRepository  extends IBaseRepositorio<ProductoProveedor, Long>, JpaSpecificationExecutor<ProductoProveedor> {

	Optional<ProductoProveedor> findOneByProveedorIdAndProductoId(Long proveedorId, Long productoId);

	List<ProductoProveedor> findByProveedorId(Long proveedorId);

}

