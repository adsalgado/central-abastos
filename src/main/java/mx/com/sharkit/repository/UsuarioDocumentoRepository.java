package mx.com.sharkit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.UsuarioDocumento;


/**
 * Spring Data  repository for the UsuarioImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioDocumentoRepository extends IBaseRepositorio<UsuarioDocumento, Long>, JpaSpecificationExecutor<UsuarioDocumento> {

	List<UsuarioDocumento> findByUsuarioId(Long usuarioId);
	
	Optional<UsuarioDocumento> findOneByUsuarioIdAndDocumentoId(Long usuarioId, Long documentoId);
	
}
