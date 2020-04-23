package mx.com.sharkit.customdao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Clase que implementa las operaciónes básicas de los repositorios
 * 
 * @author Adri@n
 * @param <T> Clase Genérica
 * @param <ID> Objeto que representa el Id de la entidad
 */
public class BaseRepositorio<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements IBaseRepositorio<T, ID> {

	/**
	 * Objeto utilizado por JPA para acceso a datos de las entidades
	 */
	private EntityManager entityManager;

	/**
	 * Constructor de la clase
	 * 
	 * @param domainClass   Entidad JPA
	 * @param entityManager {@link EntityManager}
	 */
	public BaseRepositorio(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param entityInformation Información de las entidades JPA
	 * @param entityManager {@link EntityManager}
	 */
	public BaseRepositorio(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * Búsqueda de todos los registros mediante query nativo
	 * 
	 * @param eClazz      Entidad a convertir
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	@Override
	public List<T> searchAllByQueryNativeToEntity(Class<T> eClazz, String queryNative, Object... params) {
		Query query = entityManager.createNativeQuery(queryNative, eClazz);
		int noParam = 1;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}
		return query.getResultList();
	}

	/**
	 * Búsqueda de todos los registros mediante query nativo, regresa una lista de
	 * objetos
	 * 
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	@Override
	public List<Object[]> findAllByQueryNative(String queryNative, Object... params) {
		Query query = entityManager.createNativeQuery(queryNative);
		int noParam = 1;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}
		return query.getResultList();
	}

	/**
	 * Búsqueda de todos los registros mediante query nativo, regresa una lista de
	 * Objetos Map
	 * 
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	@Override
	public List<Map<String, Object>> findAllByQueryNativeToMap(String queryNative, Object... params) {
		Session session = entityManager.unwrap(Session.class);
		SQLQuery<Map<String, Object>> query = session.createSQLQuery(queryNative);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		int noParam = 0;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}

		return query.list();
	}

	/**
	 * Búsqueda de todos los registros mediante objeto Criteria de Hibernate
	 * 
	 * @param criteria    Criterios de la búsqueda
	 * @param firstResult Número de página a consultar
	 * @param maxResults  Número de registros a consultar
	 * @return Lista de objetos
	 */
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults) {
		Session session = entityManager.unwrap(Session.class);
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		if (firstResult != null) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}

}
