package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.PedidoProveedor;

/**
 * Spring Data repository for the PedidoProveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoProveedorRepository  extends IBaseRepositorio<PedidoProveedor, Long>, JpaSpecificationExecutor<PedidoProveedor> {

	List<PedidoProveedor> findByPedidoId(Long pedidoId);
	
	List<PedidoProveedor> findByPedidoIdAndProveedorId(Long pedidoId, Long proveedorId);

	List<PedidoProveedor> findByPedidoIdAndTransportistaId(Long pedidoId, Long transportistaId);

}

