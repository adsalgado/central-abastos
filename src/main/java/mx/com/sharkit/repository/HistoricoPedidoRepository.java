package mx.com.sharkit.repository;

import mx.com.sharkit.domain.HistoricoPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HistoricoPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricoPedidoRepository extends JpaRepository<HistoricoPedido, Long> {

}
