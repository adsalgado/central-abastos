package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.TipoArticulo;


/**
 * Spring Data  repository for the TipoArticulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoArticuloRepository extends JpaRepository<TipoArticulo, Long> {

	List<TipoArticulo> findByCategoriaId(Long categoriaId);
	
}
