package mx.com.sharkit.customdao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repositorio base que define los métodos comunes para acceso a datos de los
 * repositorios
 * 
 * @author Adri@n
 * @param <T> Clase Genérica
 * @param <ID> Objeto que representa el Id de la entidad
 */
@NoRepositoryBean
public interface IBaseRepositorio<T, ID extends Serializable> extends JpaRepository<T, ID>, ICustomQueryExecutor<T> {

}