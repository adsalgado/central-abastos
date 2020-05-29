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
import mx.com.sharkit.domain.enumeration.TipoEstatus;
import mx.com.sharkit.service.EstatusService;
import mx.com.sharkit.service.dto.EstatusDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Estatus}.
 */
@RestController
@RequestMapping("/api")
public class EstatusResource {

	private final Logger log = LoggerFactory.getLogger(EstatusResource.class);

	private static final String ENTITY_NAME = "estatus";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final EstatusService estatusService;

	public EstatusResource(EstatusService estatusService) {
		this.estatusService = estatusService;
	}

	/**
	 * {@code POST  /estatus} : Create a new estatus.
	 *
	 * @param estatusDTO the estatusDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new estatusDTO, or with status {@code 400 (Bad Request)} if
	 *         the estatus has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/estatus")
	public ResponseEntity<EstatusDTO> createEstatus(@Valid @RequestBody EstatusDTO estatusDTO)
			throws URISyntaxException {
		log.debug("REST request to save Estatus : {}", estatusDTO);
		if (estatusDTO.getId() != null) {
			throw new BadRequestAlertException("A new estatus cannot already have an ID", ENTITY_NAME, "idexists");
		}
		EstatusDTO result = estatusService.save(estatusDTO);
		return ResponseEntity
				.created(new URI("/api/estatus/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /estatus} : Updates an existing estatus.
	 *
	 * @param estatusDTO the estatusDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated estatusDTO, or with status {@code 400 (Bad Request)} if
	 *         the estatusDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the estatusDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/estatus")
	public ResponseEntity<EstatusDTO> updateEstatus(@Valid @RequestBody EstatusDTO estatusDTO)
			throws URISyntaxException {
		log.debug("REST request to update Estatus : {}", estatusDTO);
		if (estatusDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		EstatusDTO result = estatusService.save(estatusDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /estatus} : get all the estatuses.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of estatuses in body.
	 */
	@GetMapping("/estatus")
	public List<EstatusDTO> getAllEstatus() {
		log.debug("REST request to get all Estatus");
		return estatusService.findAll();
	}

	/**
	 * {@code GET  /estatus/tipoEstatus/{tipoEstatus} : get all the estatuses.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of estatuses in body.
	 */
	@GetMapping("/estatus/tipoEstatus/{tipoEstatus}")
	public List<EstatusDTO> getAllEstatus(@PathVariable TipoEstatus tipoEstatus) {
		log.debug("REST request to get all Estatus by tipoEstatus: {}", tipoEstatus);
		return estatusService.findAllByTipoEstatus(tipoEstatus);
	}

	/**
	 * {@code GET  /estatus/:id} : get the "id" estatus.
	 *
	 * @param id the id of the estatusDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the estatusDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/estatus/{id}")
	public ResponseEntity<EstatusDTO> getEstatus(@PathVariable Long id) {
		log.debug("REST request to get Estatus : {}", id);
		Optional<EstatusDTO> estatusDTO = estatusService.findOne(id);
		return ResponseUtil.wrapOrNotFound(estatusDTO);
	}

	/**
	 * {@code DELETE  /estatus/:id} : delete the "id" estatus.
	 *
	 * @param id the id of the estatusDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/estatus/{id}")
	public ResponseEntity<Void> deleteEstatus(@PathVariable Long id) {
		log.debug("REST request to delete Estatus : {}", id);
		estatusService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
