package mx.com.sharkit.repository;

import mx.com.sharkit.domain.CarritoHistorico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarritoHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoHistoricoRepository extends JpaRepository<CarritoHistorico, Long> {

}
