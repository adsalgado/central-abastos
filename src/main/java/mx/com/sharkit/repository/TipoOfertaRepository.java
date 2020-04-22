package mx.com.sharkit.repository;

import mx.com.sharkit.domain.TipoOferta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoOferta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoOfertaRepository extends JpaRepository<TipoOferta, Long> {

}
