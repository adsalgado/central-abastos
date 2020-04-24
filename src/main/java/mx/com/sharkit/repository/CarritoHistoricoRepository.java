package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.CarritoHistorico;


/**
 * Spring Data  repository for the CarritoHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoHistoricoRepository extends JpaRepository<CarritoHistorico, Long> {

	List<CarritoHistorico> findByClienteId(Long clienteId);

}
