package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.Documento;

/**
 * Spring Data repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository  extends IBaseRepositorio<Documento, Long>, JpaSpecificationExecutor<Documento> {

}

