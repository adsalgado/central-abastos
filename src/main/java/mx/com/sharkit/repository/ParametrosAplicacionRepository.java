package mx.com.sharkit.repository;

import mx.com.sharkit.domain.ParametrosAplicacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParametrosAplicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrosAplicacionRepository extends JpaRepository<ParametrosAplicacion, Long> {

}
