package mx.com.sharkit.repository;

import mx.com.sharkit.domain.TipoArticulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoArticulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoArticuloRepository extends JpaRepository<TipoArticulo, Long> {

}
