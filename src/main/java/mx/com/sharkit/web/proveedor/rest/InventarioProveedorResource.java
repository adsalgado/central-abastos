package mx.com.sharkit.web.proveedor.rest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mx.com.sharkit.domain.User;
import mx.com.sharkit.excel.objectbinding.domain.Base;
import mx.com.sharkit.excel.objectbinding.domain.ExcelFile;
import mx.com.sharkit.excel.objectbinding.domain.ProductoCargaDTO;
import mx.com.sharkit.excel.objectbinding.handler.FileUploadTemplateHandler;
import mx.com.sharkit.excel.objectbinding.utils.ExcelUtilityParallelProcessor;
import mx.com.sharkit.excel.objectbinding.validator.ProductoCargaValidator;
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.ProveedorService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.ProveedorDTO;
import mx.com.sharkit.service.mapper.ProveedorMapper;
import mx.com.sharkit.web.rest.ProductoProveedorResource;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

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

}
