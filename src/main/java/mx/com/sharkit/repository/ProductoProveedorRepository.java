package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.domain.ProductoProveedor;

/**
 * Spring Data repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoProveedorRepository  extends IBaseRepositorio<ProductoProveedor, Long>, JpaSpecificationExecutor<ProductoProveedor> {

}

