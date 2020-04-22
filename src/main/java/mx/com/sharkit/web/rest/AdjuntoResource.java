package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.service.AdjuntoService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Adjunto}.
 */
@RestController
@RequestMapping("/api")
public class AdjuntoResource {

    private final Logger log = LoggerFactory.getLogger(AdjuntoResource.class);

    private static final String ENTITY_NAME = "adjunto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjuntoService adjuntoService;

    public AdjuntoResource(AdjuntoService adjuntoService) {
        this.adjuntoService = adjuntoService;
    }

    /**
     * {@code POST  /adjuntos} : Create a new adjunto.
     *
     * @param adjuntoDTO the adjuntoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjuntoDTO, or with status {@code 400 (Bad Request)} if the adjunto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjuntos")
    public ResponseEntity<AdjuntoDTO> createAdjunto(@Valid @RequestBody AdjuntoDTO adjuntoDTO) throws URISyntaxException {
        log.debug("REST request to save Adjunto : {}", adjuntoDTO);
        if (adjuntoDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjunto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjuntoDTO result = adjuntoService.save(adjuntoDTO);
        return ResponseEntity.created(new URI("/api/adjuntos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjuntos} : Updates an existing adjunto.
     *
     * @param adjuntoDTO the adjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the adjuntoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjuntos")
    public ResponseEntity<AdjuntoDTO> updateAdjunto(@Valid @RequestBody AdjuntoDTO adjuntoDTO) throws URISyntaxException {
        log.debug("REST request to update Adjunto : {}", adjuntoDTO);
        if (adjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjuntoDTO result = adjuntoService.save(adjuntoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjuntoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjuntos} : get all the adjuntos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjuntos in body.
     */
    @GetMapping("/adjuntos")
    public List<AdjuntoDTO> getAllAdjuntos() {
        log.debug("REST request to get all Adjuntos");
        return adjuntoService.findAll();
    }

    /**
     * {@code GET  /adjuntos/:id} : get the "id" adjunto.
     *
     * @param id the id of the adjuntoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjuntoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjuntos/{id}")
    public ResponseEntity<AdjuntoDTO> getAdjunto(@PathVariable Long id) {
        log.debug("REST request to get Adjunto : {}", id);
        Optional<AdjuntoDTO> adjuntoDTO = adjuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjuntoDTO);
    }

    /**
     * {@code DELETE  /adjuntos/:id} : delete the "id" adjunto.
     *
     * @param id the id of the adjuntoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjuntos/{id}")
    public ResponseEntity<Void> deleteAdjunto(@PathVariable Long id) {
        log.debug("REST request to delete Adjunto : {}", id);
        adjuntoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/adjuntos/download/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Long id) {

		AdjuntoDTO adjunto = adjuntoService.findOne(id).orElse(new AdjuntoDTO());
		return new ResponseEntity<>(adjunto.getFile(), HttpStatus.OK);
	}
	
}
