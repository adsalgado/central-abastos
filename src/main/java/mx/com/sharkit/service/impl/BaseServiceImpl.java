package mx.com.sharkit.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.service.BaseService;

/**
 * Clase que implementa los métodos comunes a los servicios genéricos
 * 
 * @author Adri@n
 * @param <T> Clase tipo Entidad
 * @param <ID> Objeto que representa el Id de la entidad
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> implements Serializable, BaseService<T, ID> {

	/**
	 * Objeto utilizado para la serialización de la clase
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Clase genérica tipo entidad
	 */
	private Class<T> entityClass;

	/**
	 * Repositorio genérico
	 */
	@Autowired
	protected IBaseRepositorio<T, ID> repository;

	/**
	 * Búsqueda de objeto por Id
	 * 
	 * @param id Id del objeto a buscar
	 * @return Optional del objeto encontrado
	 */
	@Override
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	/**
	 * Persiste el objeto en la base de datos
	 * 
	 * @param entity Objeto a persistir
	 */
	@Override
	public void save(T entity) {
		repository.save(entity);
	}

	/**
	 * Elimina el objeto en la base de datos
	 * 
	 * @param id Id del Objeto a persistir
	 */
	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	/**
	 * Búsqueda de todos los registros
	 * 
	 * @return Lista de objetos encontrados
	 */
	@Override
	public List<T> findAll() {
		return repository.findAll();

	}

	/**
	 * Búsqueda de todos los registros con parámetros de paginación y ordenamiento
	 * 
	 * @param pageable Parámetros de paginación y ordenamiento
	 * @return Lista de objetos encontrados
	 */
	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
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
	public List<T> findAllByQueryNativeToEntity(final Class<T> eClazz, String queryNative, Object... params) {
		return repository.searchAllByQueryNativeToEntity(eClazz, queryNative, params);
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
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllByQueryNativeToMap(String queryNative, Object... params) {
		return repository.findAllByQueryNativeToMap(queryNative, params);

	}

	/**
	 * Búsqueda de todos los registros mediante objeto Criteria de Hibernate
	 * 
	 * @param criteria Criterios de la búsqueda
	 * @return Lista de objetos
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, null, null);
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
	@Transactional(readOnly = true)
	public List<T> findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults) {
		return repository.findByCriteria(criteria, firstResult, maxResults);
	}

	/**
	 * Método que recupera el tipo de Clase usando reflexión
	 * 
	 * @return Class type
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getTClass() {
		if (entityClass == null) {
			ParameterizedType absDaoType = (ParameterizedType) this.getClass().getGenericSuperclass();
			entityClass = (Class<T>) absDaoType.getActualTypeArguments()[0];
		}
		return entityClass;
	}

//	    @Override
//	    public Optional<T> findOne(Example<T> example) {
//	        return repository.findOne(example);
//	    }
	//
//	    @Override
//	    public List<T> save(Iterable<T> itrbl) {
//	        return repository.saveAll(itrbl);
//	    }
	//
//	    @Override
//	    public void update(T entity) {
//	        repository.save(entity);
//	    }
	//
//	    @Override
//	    public List<T> update(Iterable<T> itrbl) {
//	        return repository.saveAll(itrbl);
//	    }
	//
//	    @Override
//	    public void delete(T entity) {
//	        repository.delete(entity);
//	    }
	//
//	    @Override
//	    public void delete(Iterable<T> itrbl) {
//	        repository.deleteAll(itrbl);
//	    }
	//
//	    @Override
//	    public void deleteAll() {
//	        repository.deleteAll();
//	    }
	//
//	    @Override
//	    public void deleteAll(Iterable<T> itrbl) {
//	        repository.deleteAll(itrbl);
//	    }
	//
//	    @Override
//	    public List<T> findAll(Example<T> example) {
//	        return repository.findAll(example);
//	    }
	//
//	    @Override
//	    public List<T> findAll(Iterable<ID> id) {
//	        return repository.findAllById(id);
//	    }
	//
//	    @Override
//	    public List<T> findAll(Example<T> example, Sort sort) {
//	        return repository.findAll(example, sort);
//	    }
	//
//	    @Override
//	    public List<T> findAll(Sort sort) {
//	        return repository.findAll(sort);
//	    }
	//
//	    @Override
//	    public Page<T> findAll(Example<T> example, Pageable pageable) {
//	        return repository.findAll(example, pageable);
//	    }
	//
//		@Override
//	    public List<Object[]> findAllByQueryNative(String queryNative, Object... params) {
//	    	return repository.findAllByQueryNative(queryNative, params);
//	    }

}
