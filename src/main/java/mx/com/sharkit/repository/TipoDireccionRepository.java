package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.TipoDireccion;

/**
 * Spring Data repository for the TipoDireccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDireccionRepository  extends IBaseRepositorio<TipoDireccion, Long>, JpaSpecificationExecutor<TipoDireccion> {

}

