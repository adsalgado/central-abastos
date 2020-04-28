package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.ProductoImagen;


/**
 * Spring Data  repository for the ProductoImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoImagenRepository extends JpaRepository<ProductoImagen, Long> {

	List<ProductoImagen> findByProductoProveedorIdOrderByIdAsc(Long productoId);
	
}
