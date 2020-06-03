package mx.com.sharkit.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
	
	Optional<Proveedor> findOneByUsuarioId(Long usuarioId);

	@Query(value = "SELECT pr.*\n" + 
			"FROM		jhi_user us\n" + 
			"INNER JOIN proveedor pr\n" + 
			"	ON (pr.usuario_id = us.id)\n" + 
			"WHERE us.login = ?1", nativeQuery = true)
	Optional<Proveedor> findOneByUserName(String userName);
	
}
