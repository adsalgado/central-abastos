package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Estatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Estatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusRepository extends JpaRepository<Estatus, Long> {

}
