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
import mx.com.sharkit.service.TipoPersonaService;
import mx.com.sharkit.service.dto.TipoPersonaDTO;
import mx.com.sharkit.service.mapper.TipoPersonaMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.TipoPersona}.
 */
@RestController
@RequestMapping("/api")
public class TipoPersonaResource {

	private final Logger log = LoggerFactory.getLogger(TipoPersonaResource.class);

	private static final String ENTITY_NAME = "tipoPersona";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TipoPersonaService tipoPersonaService;

	@Autowired
	private TipoPersonaMapper tipoPersonaMapper;

	public TipoPersonaResource(TipoPersonaService tipoPersonaService) {
		this.tipoPersonaService = tipoPersonaService;
	}

	/**
	 * {@code POST  /tipo-personas} : Create a new tipoPersona.
	 *
	 * @param tipoPersonaDTO the tipoPersonaDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tipoPersonaDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoPersona has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tipo-personas")
	public ResponseEntity<TipoPersonaDTO> createTipoPersona(
			@Valid @RequestBody TipoPersonaDTO tipoPersonaDTO) throws URISyntaxException {
		log.debug("REST request to save TipoPersona : {}", tipoPersonaDTO);
		if (tipoPersonaDTO.getId() != null) {
			throw new BadRequestAlertException("A new tipoPersona cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		TipoPersonaDTO result = tipoPersonaService.save(tipoPersonaDTO);
		return ResponseEntity
				.created(new URI("/api/tipo-personas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /tipo-personas} : Updates an existing tipoPersona.
	 *
	 * @param tipoPersonaDTO the tipoPersonaDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tipoPersonaDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoPersonaDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         tipoPersonaDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tipo-personas")
	public ResponseEntity<TipoPersonaDTO> updateTipoPersona(
			@Valid @RequestBody TipoPersonaDTO tipoPersonaDTO) throws URISyntaxException {
		log.debug("REST request to update TipoPersona : {}", tipoPersonaDTO);
		if (tipoPersonaDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TipoPersonaDTO result = tipoPersonaService.save(tipoPersonaDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				tipoPersonaDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /tipo-personas} : get all the tipoPersonas.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tipoPersonas in body.
	 */
	@GetMapping("/tipo-personas")
	public List<TipoPersonaDTO> getAllTipoPersonas() {
		log.debug("REST request to get all TipoPersonas");
		return tipoPersonaService.findAllDTO();
	}

	/**
	 * {@code GET  /tipo-personas/:id} : get the "id" tipoPersona.
	 *
	 * @param id the id of the tipoPersonaDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tipoPersonaDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tipo-personas/{id}")
	public ResponseEntity<TipoPersonaDTO> getTipoPersona(@PathVariable Long id) {
		log.debug("REST request to get TipoPersona : {}", id);
		Optional<TipoPersonaDTO> tipoPersonaDTO = tipoPersonaService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tipoPersonaDTO);
	}

	/**
	 * {@code DELETE  /tipo-personas/:id} : delete the "id" tipoPersona.
	 *
	 * @param id the id of the tipoPersonaDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tipo-personas/{id}")
	public ResponseEntity<Void> deleteTipoPersona(@PathVariable Long id) {
		log.debug("REST request to delete TipoPersona : {}", id);
		tipoPersonaService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
