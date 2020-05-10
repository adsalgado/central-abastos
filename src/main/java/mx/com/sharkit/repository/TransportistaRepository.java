package mx.com.sharkit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Transportista;


/**
 * Spring Data  repository for the Transportista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

	Optional<Transportista> findOneByusuarioId(Long usuarioId);
	
}
