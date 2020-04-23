package mx.com.sharkit.customdao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Interfaz que define los métodos comunes para consultas con querys nativos
 * 
 * @author Adri@n
 *
 * @param <T> Clase genérica
 */
public interface ICustomQueryExecutor<T> {
	/**
	 * Búsqueda de todos los registros mediante query nativo
	 * 
	 * @param eClazz      Entidad a convertir
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	List<T> searchAllByQueryNativeToEntity(final Class<T> eClazz, String queryNative, Object... params);

	/**
	 * Búsqueda de todos los registros mediante query nativo, regresa una lista de
	 * objetos
	 * 
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	List<Object[]> findAllByQueryNative(String queryNative, Object... params);

	/**
	 * Búsqueda de todos los registros mediante query nativo, regresa una lista de
	 * Objetos Map
	 * 
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	List<Map<String, Object>> findAllByQueryNativeToMap(final String queryNative, final Object... params);

	/**
	 * Búsqueda de todos los registros mediante objeto Criteria de Hibernate
	 * 
	 * @param criteria    Criterios de la búsqueda
	 * @param firstResult Número de página a consultar
	 * @param maxResults  Número de registros a consultar
	 * @return Lista de objetos
	 */
	List<T> findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults);
}

