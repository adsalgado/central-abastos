package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.enumeration.TipoEstatus;


/**
 * Spring Data  repository for the Estatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusRepository extends JpaRepository<Estatus, Long> {

	List<Estatus> findByTipoEstatus(TipoEstatus tipoEstatus);
	
	@Query(value = "SELECT * \n" + 
			"FROM estatus \n" +  
			"WHERE tipo_estatus = ?1 and nombre =  ?2 ", nativeQuery = true)
	Optional<Estatus> findStatusByTipoEstatusAndNombre(String estatusQueja, String nombre);
	
}
