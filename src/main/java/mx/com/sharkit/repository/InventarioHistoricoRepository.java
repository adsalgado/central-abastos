package mx.com.sharkit.repository;

import mx.com.sharkit.domain.InventarioHistorico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InventarioHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventarioHistoricoRepository extends JpaRepository<InventarioHistorico, Long> {

}
