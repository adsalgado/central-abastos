package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.PedidoProveedorHistorico;

/**
 * Spring Data repository for the PedidoProveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoProveedorHistoricoRepository
		extends IBaseRepositorio<PedidoProveedorHistorico, Long>, JpaSpecificationExecutor<PedidoProveedorHistorico> {

	List<PedidoProveedorHistorico> findByPedidoProveedorId(Long pedidoProveedorId);

	List<PedidoProveedorHistorico> findByPedidoProveedorIdOrderByFecha(Long pedidoProveedorId);

}
