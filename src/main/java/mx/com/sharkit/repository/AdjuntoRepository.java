package mx.com.sharkit.repository;

import mx.com.sharkit.domain.Adjunto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Adjunto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjuntoRepository extends JpaRepository<Adjunto, Long> {

}
