package mx.com.sharkit.repository;

import mx.com.sharkit.domain.UsuarioImagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UsuarioImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioImagenRepository extends JpaRepository<UsuarioImagen, Long> {

}
