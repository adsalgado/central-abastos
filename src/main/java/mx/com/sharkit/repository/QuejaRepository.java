package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Queja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Queja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuejaRepository extends JpaRepository<Queja, Long> {

}
