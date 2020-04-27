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
import mx.com.sharkit.service.PromocionService;
import mx.com.sharkit.service.dto.PromocionDTO;
import mx.com.sharkit.service.mapper.PromocionMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Promocion}.
 */
@RestController
@RequestMapping("/api")
public class PromocionResource {

	private final Logger log = LoggerFactory.getLogger(PromocionResource.class);

	private static final String ENTITY_NAME = "promocion";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PromocionService promocionService;

	@Autowired
	private PromocionMapper promocionMapper;

	public PromocionResource(PromocionService promocionService) {
		this.promocionService = promocionService;
	}

	/**
	 * {@code POST  /promociones} : Create a new promocion.
	 *
	 * @param promocionDTO the promocionDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new promocionDTO, or with status
	 *         {@code 400 (Bad Request)} if the promocion has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/promociones")
	public ResponseEntity<PromocionDTO> createPromocion(
			@Valid @RequestBody PromocionDTO promocionDTO) throws URISyntaxException {
		log.debug("REST request to save Promocion : {}", promocionDTO);
		if (promocionDTO.getId() != null) {
			throw new BadRequestAlertException("A new promocion cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		PromocionDTO result = promocionService.save(promocionDTO);
		return ResponseEntity
				.created(new URI("/api/promociones/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /promociones} : Updates an existing promocion.
	 *
	 * @param promocionDTO the promocionDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated promocionDTO, or with status
	 *         {@code 400 (Bad Request)} if the promocionDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         promocionDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/promociones")
	public ResponseEntity<PromocionDTO> updatePromocion(
			@Valid @RequestBody PromocionDTO promocionDTO) throws URISyntaxException {
		log.debug("REST request to update Promocion : {}", promocionDTO);
		if (promocionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PromocionDTO result = promocionService.save(promocionDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				promocionDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /promociones} : get all the promocions.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of promocions in body.
	 */
	@GetMapping("/promociones")
	public List<PromocionDTO> getAllPromocions() {
		log.debug("REST request to get all Promocions");
		return promocionService.findAllDTO();
	}

	/**
	 * {@code GET  /promociones/:id} : get the "id" promocion.
	 *
	 * @param id the id of the promocionDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the promocionDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/promociones/{id}")
	public ResponseEntity<PromocionDTO> getPromocion(@PathVariable Long id) {
		log.debug("REST request to get Promocion : {}", id);
		Optional<PromocionDTO> promocionDTO = promocionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(promocionDTO);
	}

	/**
	 * {@code DELETE  /promociones/:id} : delete the "id" promocion.
	 *
	 * @param id the id of the promocionDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/promociones/{id}")
	public ResponseEntity<Void> deletePromocion(@PathVariable Long id) {
		log.debug("REST request to delete Promocion : {}", id);
		promocionService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
