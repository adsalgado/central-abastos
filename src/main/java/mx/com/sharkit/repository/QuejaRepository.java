package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.Queja;


/**
 * Spring Data  repository for the Queja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuejaRepository extends IBaseRepositorio<Queja, Long>, JpaSpecificationExecutor<Queja> {

}
