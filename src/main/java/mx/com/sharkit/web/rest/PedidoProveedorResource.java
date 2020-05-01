package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import mx.com.sharkit.service.mapper.PedidoProveedorMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.PedidoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class PedidoProveedorResource {

	private final Logger log = LoggerFactory.getLogger(PedidoProveedorResource.class);

	private static final String ENTITY_NAME = "pedidoProveedor";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PedidoProveedorService pedidoProveedorService;

	@Autowired
	private PedidoProveedorMapper pedidoProveedorMapper;

	public PedidoProveedorResource(PedidoProveedorService pedidoProveedorService) {
		this.pedidoProveedorService = pedidoProveedorService;
	}

	/**
	 * {@code POST  /pedido-proveedores} : Create a new pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the pedidoProveedorDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pedidoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedor has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/pedido-proveedores")
	public ResponseEntity<PedidoProveedorDTO> createPedidoProveedor(
			@Valid @RequestBody PedidoProveedorDTO pedidoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to save PedidoProveedor : {}", pedidoProveedorDTO);
		if (pedidoProveedorDTO.getId() != null) {
			throw new BadRequestAlertException("A new pedidoProveedor cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		PedidoProveedorDTO result = pedidoProveedorService.save(pedidoProveedorDTO);
		return ResponseEntity
				.created(new URI("/api/pedido-proveedores/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /pedido-proveedores} : Updates an existing pedidoProveedor.
	 *
	 * @param pedidoProveedorDTO the pedidoProveedorDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoProveedorDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedorDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         pedidoProveedorDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pedido-proveedores")
	public ResponseEntity<PedidoProveedorDTO> updatePedidoProveedor(
			@Valid @RequestBody PedidoProveedorDTO pedidoProveedorDTO) throws URISyntaxException {
		log.debug("REST request to update PedidoProveedor : {}", pedidoProveedorDTO);
		if (pedidoProveedorDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PedidoProveedorDTO result = pedidoProveedorService.save(pedidoProveedorDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				pedidoProveedorDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /pedido-proveedores} : get all the pedidoProveedors.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidoProveedors in body.
	 */
	@GetMapping("/pedido-proveedores")
	public List<PedidoProveedorDTO> getAllPedidoProveedors() {
		log.debug("REST request to get all PedidoProveedors");
		return pedidoProveedorService.findAllDTO();
	}

	/**
	 * {@code GET  /pedido-proveedores/:id} : get the "id" pedidoProveedor.
	 *
	 * @param id the id of the pedidoProveedorDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pedidoProveedorDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/pedido-proveedores/{id}")
	public ResponseEntity<PedidoProveedorDTO> getPedidoProveedor(@PathVariable Long id) {
		log.debug("REST request to get PedidoProveedor : {}", id);
		Optional<PedidoProveedorDTO> pedidoProveedorDTO = pedidoProveedorService.findOne(id);
		return ResponseUtil.wrapOrNotFound(pedidoProveedorDTO);
	}

	/**
	 * {@code DELETE  /pedido-proveedores/:id} : delete the "id" pedidoProveedor.
	 *
	 * @param id the id of the pedidoProveedorDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/pedido-proveedores/{id}")
	public ResponseEntity<Void> deletePedidoProveedor(@PathVariable Long id) {
		log.debug("REST request to delete PedidoProveedor : {}", id);
		pedidoProveedorService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
