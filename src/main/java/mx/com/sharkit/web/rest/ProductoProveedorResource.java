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
import org.hibernate.criterion.Order;
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
import mx.com.sharkit.service.CategoriaService;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.TipoArticuloService;
import mx.com.sharkit.service.UtilService;
import mx.com.sharkit.service.dto.CategoriaDTO;
import mx.com.sharkit.service.dto.ProductoProveedorCategoriaDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProductoProveedorTipoArticuloDTO;
import mx.com.sharkit.service.dto.ProductosProveedorHomeDTO;
import mx.com.sharkit.service.dto.ProductosProveedorHomeTipoDTO;
import mx.com.sharkit.service.dto.TipoArticuloDTO;
import mx.com.sharkit.service.mapper.ProductoProveedorMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class ProductoProveedorResource {

	private final Logger log = LoggerFactory.getLogger(ProductoProveedorResource.class);

	private static final String ENTITY_NAME = "productoProveedor";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProductoProveedorService productoProveedorService;
	
	private final UtilService utilService;

	private final CategoriaService categoriaService;
	
	private final TipoArticuloService tipoArticuloService;

	@Autowired
	private ProductoProveedorMapper productoProveedorMapper;

	public ProductoProveedorResource(ProductoProveedorService productoProveedorService, UtilService utilService,
			CategoriaService categoriaService, TipoArticuloService tipoArticuloService) {
		this.productoProveedorService = productoProveedorService;
		this.utilService = utilService;
		this.categoriaService = categoriaService;
		this.tipoArticuloService = tipoArticuloService;
	}

	/**
	 * {@code POST  /proveedor-productos} : Create a new productoProveedor.
	 *
	 * @param productoProveedorDTO the productoProveedorDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new productoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the productoProveedor has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/proveedor-productos")
	public ResponseEntity<ProductoProveedorDTO> createProductoProveedor(
			@Valid @RequestBody ProductoProveedorDTO productoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to save ProductoProveedor : {}", productoProveedorDTO);
		if (productoProveedorDTO.getId() != null) {
			throw new BadRequestAlertException("A new productoProveedor cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		ProductoProveedorDTO result = productoProveedorService.save(productoProveedorDTO);
		return ResponseEntity
				.created(new URI("/api/proveedor-productos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /proveedor-productos} : Updates an existing productoProveedor.
	 *
	 * @param productoProveedorDTO the productoProveedorDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated productoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the productoProveedorDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         productoProveedorDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/proveedor-productos")
	public ResponseEntity<ProductoProveedorDTO> updateProductoProveedor(
			@Valid @RequestBody ProductoProveedorDTO productoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to update ProductoProveedor : {}", productoProveedorDTO);
		if (productoProveedorDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductoProveedorDTO result = productoProveedorService.save(productoProveedorDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				productoProveedorDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /proveedor-productos} : get all the productoProveedors.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productoProveedors in body.
	 */
	@GetMapping("/proveedor-productos")
	public List<ProductoProveedorDTO> getAllProductoProveedors() {
		log.debug("REST request to get all ProductoProveedors");
		return productoProveedorService.findAllDTO();
	}

	/**
	 * {@code GET  /proveedor-productos/:id} : get the "id" productoProveedor.
	 *
	 * @param id the id of the productoProveedorDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the productoProveedorDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/proveedor-productos/{id}")
	public ResponseEntity<ProductoProveedorDTO> getProductoProveedor(@PathVariable Long id) {
		log.debug("REST request to get ProductoProveedor : {}", id);
		Optional<ProductoProveedorDTO> productoProveedorDTO = productoProveedorService.findOne(id);
		return ResponseUtil.wrapOrNotFound(productoProveedorDTO);
	}

	/**
	 * {@code DELETE  /proveedor-productos/:id} : delete the "id" productoProveedor.
	 *
	 * @param id the id of the productoProveedorDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/proveedor-productos/{id}")
	public ResponseEntity<Void> deleteProductoProveedor(@PathVariable Long id) {
		log.debug("REST request to delete ProductoProveedor : {}", id);
		productoProveedorService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /proveedor-productos} : get all the productoProveedors.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productoProveedors in body.
	 */
	@GetMapping("/proveedor-productos/search")
	public List<ProductoProveedorDTO> searcProductoProveedors(HttpServletRequest request) {
		log.debug("REST request to get all ProductoProveedors");

		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isAllEmpty(request.getQueryString())) {
			List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
			for (NameValuePair param : params) {
				paramsMap.put(param.getName(), param.getValue());
			}
		}
		Integer iPagina = 0;
		Integer iLimite = REGISTROS_POR_PAGINA;

		String pagina = request.getParameter("page");
		if (pagina != null && StringUtils.isNumeric(pagina)) {
			iPagina = Integer.valueOf(pagina);			
		}
		
		String limite = request.getParameter("limit");
		if (limite != null && StringUtils.isNumeric(limite)) {
			iLimite = Integer.valueOf(limite);
		}
		
//		String sort = !StringUtils.isAllBlank(request.getParameter("sort")) ? request.getParameter("sort")
//				: ORDENAMIENTO_DEFAULT;
//		String sortType = !StringUtils.isAllBlank(request.getParameter("sortType")) ? request.getParameter("sortType")
//				: TIPO_ORDENAMIENTO_DEFAULT;
//
//		Sort sortOrder = StringUtils.equals("desc", sortType) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

		return productoProveedorService.searchProductos(paramsMap, iPagina, iLimite, Order.asc("producto.nombre"));
	}
	
	/**
	 * {@code GET  /productos} : get all the productos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productos in body.
	 */
	@GetMapping("/proveedor-productos/home/{seccionId}")
	public ProductosProveedorHomeDTO getSearchProductosHome(@PathVariable Long seccionId, HttpServletRequest request) {
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

		ProductosProveedorHomeDTO productosHomeDTO = new ProductosProveedorHomeDTO();
		List<CategoriaDTO> categorias = categoriaService.findBySeccionId(seccionId);
		List<ProductoProveedorCategoriaDTO> productos = categorias.stream().map(cat -> {
//			String queryString = String.format("?categoriaId=%s&limit=10", cat.getId());
			ProductoProveedorCategoriaDTO pcDTO = new ProductoProveedorCategoriaDTO();
			paramsMap.put("categoriaId", cat.getId().toString());
			paramsMap.put("limit", 10);
			pcDTO.setCategoria(cat);
			pcDTO.setProductos(productoProveedorService.searchProductos(paramsMap, 0, 10, Order.asc("producto.nombre")));
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
	@GetMapping("/proveedor-productos/categoria/{categoriaId}")
	public ProductosProveedorHomeTipoDTO getProductosCategoria(@PathVariable Long categoriaId, HttpServletRequest request) {
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

		ProductosProveedorHomeTipoDTO productosHomeDTO = new ProductosProveedorHomeTipoDTO();
		List<TipoArticuloDTO> tipos = tipoArticuloService.findByCategoriaId(categoriaId);
		List<ProductoProveedorTipoArticuloDTO> productos = tipos.stream().map(tip -> {
//			String queryString = String.format("?categoriaId=%s&limit=10", cat.getId());
			ProductoProveedorTipoArticuloDTO pcDTO = new ProductoProveedorTipoArticuloDTO();
			paramsMap.put("tipoArticuloId", tip.getId().toString());
			paramsMap.put("limit", 10);
			pcDTO.setTipoArticulo(tip);
			pcDTO.setProductos(productoProveedorService.searchProductos(paramsMap, 0, 10, Order.asc("producto.nombre")));
			return pcDTO;
		}).collect(Collectors.toCollection(LinkedList::new));
		productosHomeDTO.setProductosTipoArticulo(productos);

		return productosHomeDTO;

	}
	
	
	/**
	 * {@code GET  /proveedor-productos/producto/{productoId}} : get all the productoProveedors by productoId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productoProveedors in body.
	 */
	@GetMapping("/proveedor-productos/producto/{productoId}")
	public List<ProductoProveedorDTO> getAllProductoProveedorsByProductoId(@PathVariable Long productoId) {
		log.debug("REST request to get all ProductoProveedors by productoId: {}", productoId);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("productoId", productoId.toString());
		paramsMap.put("limit", 100);
		return productoProveedorService.searchProductos(paramsMap, 0, 100, Order.asc("producto.nombre"));
	}

	/**
	 * {@code GET  /proveedor-productos/producto/{productoId}} : get all the productoProveedors by productoId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of productoProveedors in body.
	 */
	@GetMapping("/proveedor-productos/proveedor/{proveedorId}")
	public List<ProductoProveedorDTO> getAllProductoProveedorsByProveedorId(@PathVariable Long proveedorId) {
		log.debug("REST request to get all ProductoProveedors by proveedorId: {}", proveedorId);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("proveedorId", proveedorId.toString());
		paramsMap.put("limit", 100);
		return productoProveedorService.searchProductos(paramsMap, 0, 100, Order.asc("producto.nombre"));
	}

}
