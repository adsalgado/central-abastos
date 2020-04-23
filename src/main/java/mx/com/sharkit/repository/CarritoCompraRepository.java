package mx.com.sharkit.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.CarritoCompra;

/**
 * Spring Data repository for the CarritoCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Long> {

	@Query(value = "SELECT cc.* \n" + "FROM carrito_compra cc\n" + "INNER JOIN producto pr\n"
			+ "	ON (cc.producto_id = pr.id)\n" + "WHERE cliente_id = ?1\n" + "ORDER BY nombre ASC", nativeQuery = true)
	List<CarritoCompra> findByClienteIdOrderByNombre(Long clienteId);

	@Modifying
	@Transactional
	@Query("delete from CarritoCompra cc where cc.clienteId = ?1 and cc.productoId = ?2")
	void deleteByClienteIdAndProductoId(Long clienteId, Long productoId);

	@Modifying
	@Transactional
	@Query("delete from CarritoCompra cc where cc.clienteId = ?1")
	void deleteByClienteId(Long clienteId);
	
}
