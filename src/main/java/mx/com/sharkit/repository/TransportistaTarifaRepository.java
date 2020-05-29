package mx.com.sharkit.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.TransportistaTarifa;


/**
 * Spring Data  repository for the TransportistaTarifa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportistaTarifaRepository extends JpaRepository<TransportistaTarifa, Long> {

	@Query(value = "SELECT 	tt.* \n" + 
			"FROM 	transportista_tarifa tt\n" + 
			"WHERE 	tt.transportista_id = ?1 \n" + 
			"AND 		?2 > tt.rango_minimo\n" + 
			"AND  		?3 <= tt.rango_maximo\n" + 
			"ORDER BY tt.precio DESC", nativeQuery = true)
	List<TransportistaTarifa> findTarifaTransportista(Long transportistaId, BigDecimal valor, BigDecimal valorDup);

	List<TransportistaTarifa> findByTransportistaIdOrderByRangoMinimo(Long transportistaId);

}
