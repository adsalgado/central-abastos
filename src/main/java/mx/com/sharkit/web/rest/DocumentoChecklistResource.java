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
import mx.com.sharkit.service.DocumentoChecklistService;
import mx.com.sharkit.service.dto.DocumentoChecklistDTO;
import mx.com.sharkit.service.mapper.DocumentoChecklistMapper;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.DocumentoChecklist}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoChecklistResource {

	private final Logger log = LoggerFactory.getLogger(DocumentoChecklistResource.class);

	private static final String ENTITY_NAME = "documentoChecklist";

	private static final Integer REGISTROS_POR_PAGINA = 15;

	private static final String ORDENAMIENTO_DEFAULT = "producto.nombre";

	private static final String TIPO_ORDENAMIENTO_DEFAULT = "asc";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final DocumentoChecklistService documentoChecklistService;

	@Autowired
	private DocumentoChecklistMapper documentoChecklistMapper;

	public DocumentoChecklistResource(DocumentoChecklistService documentoChecklistService) {
		this.documentoChecklistService = documentoChecklistService;
	}

	/**
	 * {@code POST  /documento-checklist} : Create a new documentoChecklist.
	 *
	 * @param documentoChecklistDTO the documentoChecklistDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new documentoChecklistDTO, or with status
	 *         {@code 400 (Bad Request)} if the documentoChecklist has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/documento-checklist")
	public ResponseEntity<DocumentoChecklistDTO> createDocumentoChecklist(
			@Valid @RequestBody DocumentoChecklistDTO documentoChecklistDTO) throws URISyntaxException {
		log.debug("REST request to save DocumentoChecklist : {}", documentoChecklistDTO);
		if (documentoChecklistDTO.getId() != null) {
			throw new BadRequestAlertException("A new documentoChecklist cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		DocumentoChecklistDTO result = documentoChecklistService.save(documentoChecklistDTO);
		return ResponseEntity
				.created(new URI("/api/documento-checklist/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /documento-checklist} : Updates an existing documentoChecklist.
	 *
	 * @param documentoChecklistDTO the documentoChecklistDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated documentoChecklistDTO, or with status
	 *         {@code 400 (Bad Request)} if the documentoChecklistDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         documentoChecklistDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/documento-checklist")
	public ResponseEntity<DocumentoChecklistDTO> updateDocumentoChecklist(
			@Valid @RequestBody DocumentoChecklistDTO documentoChecklistDTO) throws URISyntaxException {
		log.debug("REST request to update DocumentoChecklist : {}", documentoChecklistDTO);
		if (documentoChecklistDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		DocumentoChecklistDTO result = documentoChecklistService.save(documentoChecklistDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				documentoChecklistDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /documento-checklist} : get all the documentoChecklists.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of documentoChecklists in body.
	 */
	@GetMapping("/documento-checklist")
	public List<DocumentoChecklistDTO> getAllDocumentoChecklists() {
		log.debug("REST request to get all DocumentoChecklists");
		return documentoChecklistService.findAllDTO();
	}

	/**
	 * {@code GET  /documento-checklist/:id} : get the "id" documentoChecklist.
	 *
	 * @param id the id of the documentoChecklistDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the documentoChecklistDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/documento-checklist/{id}")
	public ResponseEntity<DocumentoChecklistDTO> getDocumentoChecklist(@PathVariable Long id) {
		log.debug("REST request to get DocumentoChecklist : {}", id);
		Optional<DocumentoChecklistDTO> documentoChecklistDTO = documentoChecklistService.findOne(id);
		return ResponseUtil.wrapOrNotFound(documentoChecklistDTO);
	}

	/**
	 * {@code DELETE  /documento-checklist/:id} : delete the "id" documentoChecklist.
	 *
	 * @param id the id of the documentoChecklistDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/documento-checklist/{id}")
	public ResponseEntity<Void> deleteDocumentoChecklist(@PathVariable Long id) {
		log.debug("REST request to delete DocumentoChecklist : {}", id);
		documentoChecklistService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
