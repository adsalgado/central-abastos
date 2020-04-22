package mx.com.sharkit.repository;

import mx.com.sharkit.domain.PedidoDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PedidoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

}
