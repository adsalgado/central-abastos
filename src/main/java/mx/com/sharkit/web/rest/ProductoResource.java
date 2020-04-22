package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import mx.com.sharkit.service.ProductoService;
import mx.com.sharkit.service.dto.ProductoDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Producto}.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

	private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

	private static final String ENTITY_NAME = "producto";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProductoService productoService;

	public ProductoResource(ProductoService productoService) {
		this.productoService = productoService;
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
		List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
		Map<String, Object> paramsMap = new HashMap<>();
		for (NameValuePair param : params) {
			paramsMap.put(param.getName(), param.getValue());
		}
		
		Pageable firstPageWith = PageRequest.of(0, 2);
		
		return productoService.searchProductos(paramsMap,firstPageWith);
		
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
}
