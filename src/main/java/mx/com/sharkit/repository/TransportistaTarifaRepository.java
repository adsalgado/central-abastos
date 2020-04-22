package mx.com.sharkit.repository;

import mx.com.sharkit.domain.TransportistaTarifa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransportistaTarifa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportistaTarifaRepository extends JpaRepository<TransportistaTarifa, Long> {

}
