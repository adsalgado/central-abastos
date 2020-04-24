package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.CarritoHistoricoDetalle;


/**
 * Spring Data  repository for the CarritoHistoricoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoHistoricoDetalleRepository extends JpaRepository<CarritoHistoricoDetalle, Long> {

	List<CarritoHistoricoDetalle> findByCarritoHistoricoId(Long carritoHistoricoId);
	
}
