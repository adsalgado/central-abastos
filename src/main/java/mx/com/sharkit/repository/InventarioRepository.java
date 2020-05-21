package mx.com.sharkit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Inventario;


/**
 * Spring Data  repository for the Inventario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

	Optional<Inventario> findOneByProductoProveedorId(Long productoProveedorId);
	
}
