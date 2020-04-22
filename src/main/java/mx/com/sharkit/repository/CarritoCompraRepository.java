package mx.com.sharkit.repository;

import mx.com.sharkit.domain.CarritoCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarritoCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Long> {

}
