package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Pedido;


/**
 * Spring Data  repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByClienteId(Long clienteId);
	
	@Query(value="SELECT  pe.*\n" + 
			"FROM		pedido pe\n" + 
			"INNER JOIN pedido_proveedor pp\n" + 
			"	ON (pp.pedido_id = pe.id)\n" + 
			"WHERE   pp.proveedor_id = ?1", nativeQuery=true)
	List<Pedido> findByProveedorId(Long proveedorId);

	@Query(value="SELECT  pe.*\n" + 
			"FROM		pedido pe\n" + 
			"INNER JOIN pedido_proveedor pp\n" + 
			"	ON (pp.pedido_id = pe.id)\n" + 
			"WHERE   pp.transportista_id = ?1", nativeQuery=true)
	List<Pedido> findByTransportistaId(Long transportistaId);

}
