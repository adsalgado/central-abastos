package mx.com.sharkit.repository;

import mx.com.sharkit.domain.RecolectorTarifa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecolectorTarifa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecolectorTarifaRepository extends JpaRepository<RecolectorTarifa, Long> {

}
