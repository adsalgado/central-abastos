package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.enumeration.TipoEstatus;


/**
 * Spring Data  repository for the Estatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusRepository extends JpaRepository<Estatus, Long> {

	List<Estatus> findByTipoEstatus(TipoEstatus tipoEstatus);
	
}
