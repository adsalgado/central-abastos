package mx.com.sharkit.repository;

import mx.com.sharkit.domain.CarritoHistoricoDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarritoHistoricoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoHistoricoDetalleRepository extends JpaRepository<CarritoHistoricoDetalle, Long> {

}
