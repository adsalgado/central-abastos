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
import mx.com.sharkit.service.TipoDireccionService;
import mx.com.sharkit.service.dto.TipoDireccionDTO;
import mx.com.sharkit.service.mapper.TipoDireccionMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.TipoDireccion}.
 */
@RestController
@RequestMapping("/api")
public class TipoDireccionResource {

	private final Logger log = LoggerFactory.getLogger(TipoDireccionResource.class);

	private static final String ENTITY_NAME = "tipoDireccion";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TipoDireccionService tipoDireccionService;

	@Autowired
	private TipoDireccionMapper tipoDireccionMapper;

	public TipoDireccionResource(TipoDireccionService tipoDireccionService) {
		this.tipoDireccionService = tipoDireccionService;
	}

	/**
	 * {@code POST  /tipo-direcciones} : Create a new tipoDireccion.
	 *
	 * @param tipoDireccionDTO the tipoDireccionDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tipoDireccionDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoDireccion has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tipo-direcciones")
	public ResponseEntity<TipoDireccionDTO> createTipoDireccion(
			@Valid @RequestBody TipoDireccionDTO tipoDireccionDTO) throws URISyntaxException {
		log.debug("REST request to save TipoDireccion : {}", tipoDireccionDTO);
		if (tipoDireccionDTO.getId() != null) {
			throw new BadRequestAlertException("A new tipoDireccion cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		TipoDireccionDTO result = tipoDireccionService.save(tipoDireccionDTO);
		return ResponseEntity
				.created(new URI("/api/tipo-direcciones/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /tipo-direcciones} : Updates an existing tipoDireccion.
	 *
	 * @param tipoDireccionDTO the tipoDireccionDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tipoDireccionDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoDireccionDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         tipoDireccionDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tipo-direcciones")
	public ResponseEntity<TipoDireccionDTO> updateTipoDireccion(
			@Valid @RequestBody TipoDireccionDTO tipoDireccionDTO) throws URISyntaxException {
		log.debug("REST request to update TipoDireccion : {}", tipoDireccionDTO);
		if (tipoDireccionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TipoDireccionDTO result = tipoDireccionService.save(tipoDireccionDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				tipoDireccionDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /tipo-direcciones} : get all the tipoDireccions.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tipoDireccions in body.
	 */
	@GetMapping("/tipo-direcciones")
	public List<TipoDireccionDTO> getAllTipoDireccions() {
		log.debug("REST request to get all TipoDireccions");
		return tipoDireccionService.findAllDTO();
	}

	/**
	 * {@code GET  /tipo-direcciones/:id} : get the "id" tipoDireccion.
	 *
	 * @param id the id of the tipoDireccionDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tipoDireccionDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tipo-direcciones/{id}")
	public ResponseEntity<TipoDireccionDTO> getTipoDireccion(@PathVariable Long id) {
		log.debug("REST request to get TipoDireccion : {}", id);
		Optional<TipoDireccionDTO> tipoDireccionDTO = tipoDireccionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tipoDireccionDTO);
	}

	/**
	 * {@code DELETE  /tipo-direcciones/:id} : delete the "id" tipoDireccion.
	 *
	 * @param id the id of the tipoDireccionDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tipo-direcciones/{id}")
	public ResponseEntity<Void> deleteTipoDireccion(@PathVariable Long id) {
		log.debug("REST request to delete TipoDireccion : {}", id);
		tipoDireccionService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
