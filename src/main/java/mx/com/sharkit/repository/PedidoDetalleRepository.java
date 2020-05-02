package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.PedidoDetalle;


/**
 * Spring Data  repository for the PedidoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

	List<PedidoDetalle> findByPedidoProveedorId(Long pedidoProveedorId);
	
}
