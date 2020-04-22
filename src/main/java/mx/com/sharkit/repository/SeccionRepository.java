package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Seccion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Seccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {

}
