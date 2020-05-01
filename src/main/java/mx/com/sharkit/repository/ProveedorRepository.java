package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.Proveedor;


/**
 * Spring Data  repository for the Proveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorRepository extends IBaseRepositorio<Proveedor, Long>, JpaSpecificationExecutor<Proveedor> {

	@Query(value = "SELECT prv.*\n" + 
			"FROM		proveedor prv\n" + 
			"INNER JOIN producto_proveedor pp ON (pp.proveedor_id = prv.id)\n" + 
			"INNER JOIN producto prd ON (prd.id = pp.producto_id)\n" + 
			"WHERE   producto_id = ?1 \n" + 
			"ORDER BY prv.nombre", nativeQuery = true)
	List<Proveedor> findByProductoId(Long productoId);
	
}
