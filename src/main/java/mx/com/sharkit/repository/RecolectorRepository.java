package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Recolector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recolector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecolectorRepository extends JpaRepository<Recolector, Long> {

}
