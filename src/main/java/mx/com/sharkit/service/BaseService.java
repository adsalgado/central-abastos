package mx.com.sharkit.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que define los métodos comunes a los servicios genéricos
 * 
 * @author Adri@n
 * @param <T> Clase tipo Entidad
 * @param <ID> Objeto que representa el Id de la entidad
 */
public interface BaseService<T, ID extends Serializable> {

	/**
	 * Búsqueda de objeto por Id
	 * 
	 * @param id Id del objeto a buscar
	 * @return Optional del objeto encontrado
	 */
	Optional<T> findById(ID id);

	/**
	 * Persiste el objeto en la base de datos
	 * 
	 * @param entity Objeto a persistir
	 */
	void save(T entity);

	/**
	 * Elimina el objeto en la base de datos
	 * 
	 * @param id Id del Objeto a persistir
	 */
	void deleteById(ID id);

	/**
	 * Búsqueda de todos los registros
	 * 
	 * @return Lista de objetos encontrados
	 */
	List<T> findAll();

	/**
	 * Búsqueda de todos los registros con parámetros de paginación y ordenamiento
	 * 
	 * @param pageable Parámetros de paginación y ordenamiento
	 * @return Lista de objetos encontrados
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * Búsqueda de todos los registros mediante query nativo
	 * 
	 * @param eClazz      Entidad a convertir
	 * @param queryNative Consulta SQL nativa
	 * @param params      Parámetros de la consulta
	 * @return Lista de objetos
	 */
	List<T> findAllByQueryNativeToEntity(final Class<T> eClazz, final String queryNative, final Object... params);

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
	 * @param criteria Criterios de la búsqueda
	 * @return Lista de objetos
	 */
	List<T> findByCriteria(final DetachedCriteria criteria);

	/**
	 * Búsqueda de todos los registros mediante objeto Criteria de Hibernate
	 * 
	 * @param criteria    Criterios de la búsqueda
	 * @param firstResult Número de página a consultar
	 * @param maxResults  Número de registros a consultar
	 * @return Lista de objetos
	 */
	List<T> findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults);

//		Optional<T> findOne(Example<T> example);
	//
//		List<T> save(Iterable<T> itrbl);
	//
//		void update(T entity);
	//
//		List<T> update(Iterable<T> itrbl);
	//
//		void delete(Iterable<T> itrbl);
	//
//		void delete(T entity);
	//
//		void deleteAll();
	//
//		void deleteAll(Iterable<T> itrbl);
	//
//		List<T> findAll(Example<T> example);
	//
//		List<T> findAll(Iterable<ID> id);
	//
//		List<T> findAll(Sort sort);
	//
//		List<T> findAll(Example<T> example, Sort sort);
	//
//		Page<T> findAll(Example<T> example, Pageable pageable);
	//
//		List<Object[]> findAllByQueryNative(final String queryNative, final Object... params);

}
