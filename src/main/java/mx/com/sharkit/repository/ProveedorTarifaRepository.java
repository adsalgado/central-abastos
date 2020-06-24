package mx.com.sharkit.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.ProveedorTarifa;
import mx.com.sharkit.domain.TransportistaTarifa;


/**
 * Spring Data  repository for the TransportistaTarifa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorTarifaRepository extends JpaRepository<ProveedorTarifa, Long> {

	@Query(value = "SELECT 	pt.* \n" + 
			"FROM 	proveedor_tarifa pt\n" + 
			"WHERE 	?2 > pt.rango_minimo\n" + 
			"AND  	?3 <= pt.rango_maximo\n" + 
			"ORDER BY pt.porcentaje_comision DESC", nativeQuery = true)
	List<ProveedorTarifa> findByRango(BigDecimal valor, BigDecimal valorDup);
	
	@Query(value = "SELECT 	pt.* \n" + 
			"FROM 	proveedor_tarifa pt\n" + 
			"ORDER BY pt.rango_minimo", nativeQuery = true)
	List<ProveedorTarifa> findAllOrderByRangoMinimo();

}
