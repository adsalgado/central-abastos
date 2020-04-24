package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.UsuarioDireccion;


/**
 * Spring Data  repository for the UsuarioImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioDireccionRepository extends JpaRepository<UsuarioDireccion, Long> {

}
