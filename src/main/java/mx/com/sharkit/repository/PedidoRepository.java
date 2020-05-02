package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Pedido;


/**
 * Spring Data  repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByClienteId(Long clienteId);
	
}
