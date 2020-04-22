package mx.com.sharkit.repository;

import mx.com.sharkit.domain.ProductoImagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoImagenRepository extends JpaRepository<ProductoImagen, Long> {

}
