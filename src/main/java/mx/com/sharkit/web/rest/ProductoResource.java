package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.service.CategoriaService;
import mx.com.sharkit.service.ProductoService;
import mx.com.sharkit.service.TipoArticuloService;
import mx.com.sharkit.service.UtilService;
import mx.com.sharkit.service.dto.CategoriaDTO;
import mx.com.sharkit.service.dto.ProductoCategoriaDTO;
import mx.com.sharkit.service.dto.ProductoDTO;
import mx.com.sharkit.service.dto.ProductoTipoArticuloDTO;
import mx.com.sharkit.service.dto.ProductosHomeDTO;
import mx.com.sharkit.service.dto.ProductosHomeTipoDTO;
import mx.com.sharkit.service.dto.TipoArticuloDTO;
import mx.com.sharkit.service.mapper.ProductoMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Producto}.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

	private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

	private static final String ENTITY_NAME = "producto";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProductoService productoService;

	private final UtilService utilService;

	private final CategoriaService categoriaService;
	
	private final TipoArticuloService tipoArticuloService;

	@Autowired
	private ProductoMapper productoMapper;

	public ProductoResource(ProductoService productoService, UtilService utilService,
			CategoriaService categoriaService, TipoArticuloService tipoArticuloService) {
		this.productoService = productoService;
		this.utilService = utilService;
		this.categoriaService = categoriaService;
		this.tipoArticuloService = tipoArticuloService;
	}

	/**
	 * {@code POST  /productos} : Create a new producto.
	 *
	 * @param productoDTO the productoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new productoDTO, or with status {@code 400 (Bad Request)} if
	 *         the producto has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/productos")
	public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO)
			throws URISyntaxException {
		log.debug("REST request to save Producto : {}", productoDTO);
		if (productoDTO.getId() != null) {
			throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ProductoDTO result = productoService.save(productoDTO);
		return ResponseEntity
				.created(new URI("/api/productos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /productos} : Updates an existing producto.
	 *
	 * @param productoDTO the productoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated productoDTO, or with status {@code 400 (Bad Request)} if
	 *         the productoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the productoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/productos")
	public ResponseEntity<ProductoDTO> updateProducto(@Valid @RequestBody ProductoDTO productoDTO)
			throws URISyntaxException {
		log.debug("REST request to update Producto : {}", productoDTO);
		if (productoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductoDTO result = productoService.save(productoDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /productos} : get all the productos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productos in body.
	 */
	@GetMapping("/productos")
	public List<ProductoDTO> getAllProductos() {
		log.debug("REST request to get all Productos");

		String queryNative = "select * from producto where id = 1";
		List<Producto> lstProd = utilService.findAllByQueryNativeToEntity(Producto.class, queryNative, new Object[] {});
		log.info("*********");
		log.info("lstProd: {}", lstProd);
		log.info("*********");

		List<ProductoDTO> lstProdDto = lstProd.stream().map(productoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

		log.info("*********");
		log.info("lstProdDTO: {}", lstProdDto);
		log.info("*********");

		List<Map<String, Object>> lstMapProd = utilService.findAllByQueryNativeToMap(queryNative, new Object[] {});
		log.info("*********");
		log.info("lstMapProd: {}", lstMapProd);
		log.info("*********");

		return productoService.findAll();
	}

	/**
	 * {@code GET  /productos} : get all the productos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productos in body.
	 */
	@GetMapping("/productos/search")
	public List<ProductoDTO> getSearchProductos(HttpServletRequest request) {
		log.debug("REST request to get all Productos");

		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isAllEmpty(request.getQueryString())) {
			List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
			for (NameValuePair param : params) {
				paramsMap.put(param.getName(), param.getValue());
			}
		}
		int iPagina = 0;
		int iLimite = REGISTROS_POR_PAGINA;

		String pagina = request.getParameter("page");
		if (!StringUtils.isAllBlank(pagina)) {
			iPagina = Integer.valueOf(pagina);
		}
		String limite = request.getParameter("limit");
		if (!StringUtils.isAllBlank(limite)) {
			iLimite = Integer.valueOf(limite);
		}
		String sort = !StringUtils.isAllBlank(request.getParameter("sort")) ? request.getParameter("sort")
				: ORDENAMIENTO_DEFAULT;
		String sortType = !StringUtils.isAllBlank(request.getParameter("sortType")) ? request.getParameter("sortType")
				: TIPO_ORDENAMIENTO_DEFAULT;

		Sort sortOrder = StringUtils.equals("desc", sortType) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

		log.info("sort {}", sort);
		log.info("sortType {}", sortType);
		Pageable pageable = PageRequest.of(iPagina, iLimite, sortOrder);

		return productoService.searchProductos(paramsMap, pageable);

	}

	/**
	 * {@code GET  /productos/:id} : get the "id" producto.
	 *
	 * @param id the id of the productoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the productoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/productos/{id}")
	public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
		log.debug("REST request to get Producto : {}", id);
		Optional<ProductoDTO> productoDTO = productoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(productoDTO);
	}

	/**
	 * {@code DELETE  /productos/:id} : delete the "id" producto.
	 *
	 * @param id the id of the productoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
		log.debug("REST request to delete Producto : {}", id);
		productoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /productos} : get all the productos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productos in body.
	 */
	@GetMapping("/productos/home/{seccionId}")
	public ProductosHomeDTO getSearchProductosHome(@PathVariable Long seccionId, HttpServletRequest request) {
		log.debug("REST request to get all Productos");

		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isAllEmpty(request.getQueryString())) {
			List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
			for (NameValuePair param : params) {
				paramsMap.put(param.getName(), param.getValue());
			}
		}
		int iPagina = 0;
		int iLimite = REGISTROS_POR_PAGINA;

		String pagina = request.getParameter("page");
		if (!StringUtils.isAllBlank(pagina)) {
			iPagina = Integer.valueOf(pagina);
		}
		String limite = request.getParameter("limit");
		if (!StringUtils.isAllBlank(limite)) {
			iLimite = Integer.valueOf(limite);
		}
		String sort = !StringUtils.isAllBlank(request.getParameter("sort")) ? request.getParameter("sort")
				: ORDENAMIENTO_DEFAULT;
		String sortType = !StringUtils.isAllBlank(request.getParameter("sortType")) ? request.getParameter("sortType")
				: TIPO_ORDENAMIENTO_DEFAULT;

		Sort sortOrder = StringUtils.equals("desc", sortType) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

		log.info("sort {}", sort);
		log.info("sortType {}", sortType);
		Pageable pageable = PageRequest.of(iPagina, iLimite, sortOrder);

		ProductosHomeDTO productosHomeDTO = new ProductosHomeDTO();
		List<CategoriaDTO> categorias = categoriaService.findBySeccionId(seccionId);
		List<ProductoCategoriaDTO> productos = categorias.stream().map(cat -> {
//			String queryString = String.format("?categoriaId=%s&limit=10", cat.getId());
			ProductoCategoriaDTO pcDTO = new ProductoCategoriaDTO();
			paramsMap.put("categoriaId", cat.getId());
			paramsMap.put("limit", 10);
			pcDTO.setCategoria(cat);
			pcDTO.setProductos(productoService.searchProductos(paramsMap, pageable));
			return pcDTO;
		}).collect(Collectors.toCollection(LinkedList::new));
		productosHomeDTO.setProductosCategoria(productos);

		return productosHomeDTO;

	}

	/**
	 * {@code GET  /productos} : get all the productos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productos in body.
	 */
	@GetMapping("/productos/categoria/{categoriaId}")
	public ProductosHomeTipoDTO getProductosCategoria(@PathVariable Long categoriaId, HttpServletRequest request) {
		log.debug("REST request to get all Productos");

		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isAllEmpty(request.getQueryString())) {
			List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
			for (NameValuePair param : params) {
				paramsMap.put(param.getName(), param.getValue());
			}
		}
		int iPagina = 0;
		int iLimite = REGISTROS_POR_PAGINA;

		String pagina = request.getParameter("page");
		if (!StringUtils.isAllBlank(pagina)) {
			iPagina = Integer.valueOf(pagina);
		}
		String limite = request.getParameter("limit");
		if (!StringUtils.isAllBlank(limite)) {
			iLimite = Integer.valueOf(limite);
		}
		String sort = !StringUtils.isAllBlank(request.getParameter("sort")) ? request.getParameter("sort")
				: ORDENAMIENTO_DEFAULT;
		String sortType = !StringUtils.isAllBlank(request.getParameter("sortType")) ? request.getParameter("sortType")
				: TIPO_ORDENAMIENTO_DEFAULT;

		Sort sortOrder = StringUtils.equals("desc", sortType) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

		log.info("sort {}", sort);
		log.info("sortType {}", sortType);
		Pageable pageable = PageRequest.of(iPagina, iLimite, sortOrder);

		ProductosHomeTipoDTO productosHomeDTO = new ProductosHomeTipoDTO();
		List<TipoArticuloDTO> tipos = tipoArticuloService.findByCategoriaId(categoriaId);
		List<ProductoTipoArticuloDTO> productos = tipos.stream().map(tip -> {
//			String queryString = String.format("?categoriaId=%s&limit=10", cat.getId());
			ProductoTipoArticuloDTO pcDTO = new ProductoTipoArticuloDTO();
			paramsMap.put("tipoArticuloId", tip.getId());
			paramsMap.put("limit", 10);
			pcDTO.setTipoArticulo(tip);
			pcDTO.setProductos(productoService.searchProductos(paramsMap, pageable));
			return pcDTO;
		}).collect(Collectors.toCollection(LinkedList::new));
		productosHomeDTO.setProductosTipoArticulo(productos);

		return productosHomeDTO;

	}

}
