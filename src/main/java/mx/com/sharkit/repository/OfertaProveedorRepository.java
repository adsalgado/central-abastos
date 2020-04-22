package mx.com.sharkit.repository;

import mx.com.sharkit.domain.OfertaProveedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfertaProveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfertaProveedorRepository extends JpaRepository<OfertaProveedor, Long> {

}
