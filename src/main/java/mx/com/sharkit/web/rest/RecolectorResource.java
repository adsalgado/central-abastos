package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.RecolectorService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.RecolectorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Recolector}.
 */
@RestController
@RequestMapping("/api")
public class RecolectorResource {

    private final Logger log = LoggerFactory.getLogger(RecolectorResource.class);

    private static final String ENTITY_NAME = "recolector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecolectorService recolectorService;

    public RecolectorResource(RecolectorService recolectorService) {
        this.recolectorService = recolectorService;
    }

    /**
     * {@code POST  /recolectors} : Create a new recolector.
     *
     * @param recolectorDTO the recolectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recolectorDTO, or with status {@code 400 (Bad Request)} if the recolector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recolectors")
    public ResponseEntity<RecolectorDTO> createRecolector(@Valid @RequestBody RecolectorDTO recolectorDTO) throws URISyntaxException {
        log.debug("REST request to save Recolector : {}", recolectorDTO);
        if (recolectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new recolector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecolectorDTO result = recolectorService.save(recolectorDTO);
        return ResponseEntity.created(new URI("/api/recolectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recolectors} : Updates an existing recolector.
     *
     * @param recolectorDTO the recolectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recolectorDTO,
     * or with status {@code 400 (Bad Request)} if the recolectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recolectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recolectors")
    public ResponseEntity<RecolectorDTO> updateRecolector(@Valid @RequestBody RecolectorDTO recolectorDTO) throws URISyntaxException {
        log.debug("REST request to update Recolector : {}", recolectorDTO);
        if (recolectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecolectorDTO result = recolectorService.save(recolectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recolectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recolectors} : get all the recolectors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recolectors in body.
     */
    @GetMapping("/recolectors")
    public List<RecolectorDTO> getAllRecolectors() {
        log.debug("REST request to get all Recolectors");
        return recolectorService.findAll();
    }

    /**
     * {@code GET  /recolectors/:id} : get the "id" recolector.
     *
     * @param id the id of the recolectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recolectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recolectors/{id}")
    public ResponseEntity<RecolectorDTO> getRecolector(@PathVariable Long id) {
        log.debug("REST request to get Recolector : {}", id);
        Optional<RecolectorDTO> recolectorDTO = recolectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recolectorDTO);
    }

    /**
     * {@code DELETE  /recolectors/:id} : delete the "id" recolector.
     *
     * @param id the id of the recolectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recolectors/{id}")
    public ResponseEntity<Void> deleteRecolector(@PathVariable Long id) {
        log.debug("REST request to delete Recolector : {}", id);
        recolectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
