package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Transportista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transportista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

}
