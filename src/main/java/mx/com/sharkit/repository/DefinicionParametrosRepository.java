package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.DefinicionParametros;

@SuppressWarnings("unused")
@Repository
public interface DefinicionParametrosRepository
		extends IBaseRepositorio<DefinicionParametros, Integer>, JpaSpecificationExecutor<DefinicionParametros> {

    List<DefinicionParametros> findByTipoParametroId(Integer tipoParametroId); 
    
    @Query(value = 
            "SELECT rep.* \n" +
            "FROM   cfg_definicion_parametros sec, cfg_definicion_parametros rep \n" +
            "WHERE  sec.tipo_parametro_id = ?1 \n" +
            "AND    sec.clave_parametro = ?2 \n" +
            "AND    rep.parent_id = sec.id \n" +
            "AND    rep.clave_parametro = ?3", nativeQuery = true)
    DefinicionParametros getParametroByTipoAndSeccionAndClave(Integer tipoParametro, String sSeccion, String sCveScript);
}
