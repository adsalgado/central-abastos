package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import mx.com.sharkit.service.DefinicionParametrosService;
import mx.com.sharkit.service.dto.DefinicionParametrosDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing
 * {@link mx.com.sharkit.domain.DefinicionParametros}.
 */
@RestController
@RequestMapping("/api")
public class DefinicionParametrosResource {

	private final Logger log = LoggerFactory.getLogger(DefinicionParametrosResource.class);

	private static final String ENTITY_NAME = "definicionParametros";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final DefinicionParametrosService definicionParametrosService;

	public DefinicionParametrosResource(DefinicionParametrosService definicionParametrosService) {
		this.definicionParametrosService = definicionParametrosService;
	}

	/**
	 * {@code POST  /definicion-parametros} : Create a new definicionParametros.
	 *
	 * @param definicionParametrosDTO the definicionParametrosDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new definicionParametrosDTO, or with status
	 *         {@code 400 (Bad Request)} if the definicionParametros has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/definicion-parametros")
	public ResponseEntity<DefinicionParametrosDTO> createDefinicionParametros(
			@Valid @RequestBody DefinicionParametrosDTO definicionParametrosDTO) throws URISyntaxException {
		log.debug("REST request to save DefinicionParametros : {}", definicionParametrosDTO);
		if (definicionParametrosDTO.getId() != null) {
			throw new BadRequestAlertException("A new definicionParametros cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		DefinicionParametrosDTO result = definicionParametrosService.save(definicionParametrosDTO);
		return ResponseEntity
				.created(new URI("/api/definicion-parametros/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /definicion-parametros} : Updates an existing
	 * definicionParametros.
	 *
	 * @param definicionParametrosDTO the definicionParametrosDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated definicionParametrosDTO, or with status
	 *         {@code 400 (Bad Request)} if the definicionParametrosDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         definicionParametrosDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/definicion-parametros")
	public ResponseEntity<DefinicionParametrosDTO> updateDefinicionParametros(
			@Valid @RequestBody DefinicionParametrosDTO definicionParametrosDTO) throws URISyntaxException {
		log.debug("REST request to update DefinicionParametros : {}", definicionParametrosDTO);
		if (definicionParametrosDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		DefinicionParametrosDTO result = definicionParametrosService.save(definicionParametrosDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				definicionParametrosDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /definicion-parametros} : get all the definicionParametross.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of definicionParametross in body.
	 */
	@GetMapping("/definicion-parametros")
	public List<DefinicionParametrosDTO> getAllDefinicionParametross() {
		log.debug("REST request to get all DefinicionParametross");
		return definicionParametrosService.findAllDTO();
	}

	/**
	 * {@code GET  /definicion-parametros/:id} : get the "id" definicionParametros.
	 *
	 * @param id the id of the definicionParametrosDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the definicionParametrosDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/definicion-parametros/{id}")
	public ResponseEntity<DefinicionParametrosDTO> getDefinicionParametros(@PathVariable Integer id) {
		log.debug("REST request to get DefinicionParametros : {}", id);
		Optional<DefinicionParametrosDTO> definicionParametrosDTO = definicionParametrosService.findOne(id);
		return ResponseUtil.wrapOrNotFound(definicionParametrosDTO);
	}

	/**
	 * {@code DELETE  /definicion-parametros/:id} : delete the "id"
	 * definicionParametros.
	 *
	 * @param id the id of the definicionParametrosDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/definicion-parametros/{id}")
	public ResponseEntity<Void> deleteDefinicionParametros(@PathVariable Integer id) {
		log.debug("REST request to delete DefinicionParametros : {}", id);
		definicionParametrosService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
