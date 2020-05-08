package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.TipoPersona;

/**
 * Spring Data repository for the TipoPersona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPersonaRepository  extends IBaseRepositorio<TipoPersona, Long>, JpaSpecificationExecutor<TipoPersona> {

}

