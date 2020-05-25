package mx.com.sharkit.web.proveedor.rest;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.excel.objectbinding.domain.Base;
import mx.com.sharkit.excel.objectbinding.domain.ErrorDTO;
import mx.com.sharkit.excel.objectbinding.domain.ExcelFile;
import mx.com.sharkit.excel.objectbinding.domain.ProductoCargaDTO;
import mx.com.sharkit.excel.objectbinding.handler.FileUploadTemplateHandler;
import mx.com.sharkit.excel.objectbinding.utils.ExcelUtilityParallelProcessor;
import mx.com.sharkit.excel.objectbinding.validator.ProductoCargaValidator;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.ProveedorService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.dto.SubastasResponseDTO;
import mx.com.sharkit.service.mapper.ProveedorMapper;
import mx.com.sharkit.web.rest.ProductoProveedorResource;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
@RestController
@RequestMapping("/api/proveedor")
public class InventarioProveedorResource {

	private final Logger log = LoggerFactory.getLogger(ProductoProveedorResource.class);

	private static final String ENTITY_NAME = "productoProveedor";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private ProductoProveedorService productoProveedorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private FileUploadTemplateHandler fileUploadTemplateHandler;

	@Autowired
	private ExcelUtilityParallelProcessor excelUtilityParallelProcessor;
	
	@Autowired
	private ProductoCargaValidator productoCargaValidator;

	@Autowired
	private ProveedorMapper proveedorMapper;

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
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		ProveedorDTO proveedor = proveedorService.findOneByusuarioId(usuarioId).orElse(null);
		if (proveedor == null) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}
		productoProveedorDTO.setProveedorId(proveedor.getId());		
		ProductoProveedorDTO result = productoProveedorService.saveProductoProveedor(productoProveedorDTO);

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
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		ProveedorDTO proveedor = proveedorService.findOneByusuarioId(usuarioId).orElse(null);
		if (proveedor == null) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}

		ProductoProveedorDTO result = productoProveedorService.updateProductoProveedor(productoProveedorDTO);
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
		log.debug("REST request to get all ProductoProveedors of productos");
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		ProveedorDTO proveedor = proveedorService.findOneByusuarioId(usuarioId).orElse(null);
		if (proveedor == null) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}

		return productoProveedorService.findByProveedorId(proveedor.getId());
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

	@PostMapping(value = "/proveedor-productos/carga-masiva", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SubastasResponseDTO> cargaMasivaProductos( @RequestBody AdjuntoDTO adjuntoDTO) {
		
		log.debug("REST carga masiva de productos, adjuntoDTO: {}", adjuntoDTO);
		if (adjuntoDTO == null || adjuntoDTO.getFile() == null || StringUtils.isAllBlank(adjuntoDTO.getFileName()) ) {
			throw new BadRequestAlertException("El archivo de carga es requerido", ENTITY_NAME, "idnull");
		}
		
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		ProveedorDTO proveedor = proveedorService.findOneByusuarioId(usuarioId).orElse(null);
		if (proveedor == null) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}
		
		SubastasResponseDTO response = new SubastasResponseDTO();
		try {
			List<ProductoCargaDTO> productosCarga = convertXl(adjuntoDTO);
			
			if (productosCarga != null && !productosCarga.isEmpty()) {
				List<ErrorDTO> errores = new ArrayList<>();
				for (ProductoCargaDTO prodCarga : productosCarga) {
					if (prodCarga.getErrors() != null && prodCarga.getErrors().hasErrors()) {
						for (FieldError fe : prodCarga.getErrors().getFieldErrors()) {
							ErrorDTO errorDTO = new ErrorDTO();
							errorDTO.setRow(prodCarga.getRow());
							errorDTO.setField(fe.getField());
							errorDTO.setErrorMessage(fe.getDefaultMessage());
							errores.add(errorDTO);
						}
					}
				}
							
				if (errores == null || errores.isEmpty()) {
					productoProveedorService.cargaMasivaProductosProveedor(productosCarga, proveedor);				
				} else {
					response.setError(true);
					response.setMessageError("Errores en archivo");
					response.setData(errores);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
				
			} else {
				throw new BadRequestAlertException("El archivo esta vacío.", ENTITY_NAME, "idnull");
			}
			
			
		} catch (Exception e) {
			log.error("Exception: {}", e);
			throw new BadRequestAlertException("Ocurrió un error en la carga masiva.", ENTITY_NAME, "iderror");
		}
		
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/files/carga-productos", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> getJson(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		
		log.debug("REST carga masiva de productos");
		Optional<User> user = userService.getUserWithAuthorities();
		Long usuarioId = user.isPresent() ? user.get().getId() : 0L;
		if (usuarioId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		ProveedorDTO proveedor = proveedorService.findOneByusuarioId(usuarioId).orElse(null);
		if (proveedor == null) {
			throw new BadRequestAlertException("El usuario no es proveedor", ENTITY_NAME, "idnull");
		}
		
		try {
			productoProveedorService.cargaMasivaProductosProveedor(convertXl(file), proveedor);
		} catch (Exception e) {
			log.error("Exception: {}", e);
			throw new BadRequestAlertException("Ocurrió un error en la carga masiva.", ENTITY_NAME, "iderror");
		}
		return ResponseEntity.ok().build();

	}

	private <T extends Base> List<T> convertXl(MultipartFile file) throws Exception {
		List<T> list = null;
		if (!file.isEmpty()) {

			ExecutorService executorService = Executors
					.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 16);

			try {
				Workbook workbook;
				if (file.getOriginalFilename().endsWith("xls")) {
					workbook = new HSSFWorkbook(file.getInputStream());
				} else if (file.getOriginalFilename().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(file.getInputStream());
				} else {
					return list;
				}

				ExcelFile excelFile = new ExcelFile(workbook.getSheetAt(0), true, 2000,
						fileUploadTemplateHandler.getProductoCargaTemplate(), ProductoCargaDTO.class, productoCargaValidator);

				log.info("Parallel Processing Start time ~~~~~~~~~~~~~~~");
				list = excelUtilityParallelProcessor.readExcelInParallel(excelFile);

				return list;

			} catch (Exception e) {
				throw e;
			} finally {
				try {
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			throw new Exception("You failed to upload   because the file was empty.");
		}
	}

	private <T extends Base> List<T> convertXl(AdjuntoDTO file) throws Exception {
		List<T> list = null;
		if (file != null && file.getFile() != null && file.getFileName() != null) {

			ExecutorService executorService = Executors
					.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 16);

			try {
				Workbook workbook;
				if (file.getFileName().endsWith("xls")) {
					workbook = new HSSFWorkbook(new ByteArrayInputStream(file.getFile()));
				} else if (file.getFileName().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getFile()));
				} else {
					return list;
				}

				ExcelFile excelFile = new ExcelFile(workbook.getSheetAt(0), true, 2000,
						fileUploadTemplateHandler.getProductoCargaTemplate(), ProductoCargaDTO.class, productoCargaValidator);

				log.info("Parallel Processing Start time ~~~~~~~~~~~~~~~");
				list = excelUtilityParallelProcessor.readExcelInParallel(excelFile);

				return list;

			} catch (Exception e) {
				throw e;
			} finally {
				try {
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			throw new Exception("You failed to upload   because the file was empty.");
		}
	}

}
