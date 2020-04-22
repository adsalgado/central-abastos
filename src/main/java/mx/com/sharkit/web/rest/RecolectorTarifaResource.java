package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.RecolectorTarifaService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.RecolectorTarifaDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.RecolectorTarifa}.
 */
@RestController
@RequestMapping("/api")
public class RecolectorTarifaResource {

    private final Logger log = LoggerFactory.getLogger(RecolectorTarifaResource.class);

    private static final String ENTITY_NAME = "recolectorTarifa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecolectorTarifaService recolectorTarifaService;

    public RecolectorTarifaResource(RecolectorTarifaService recolectorTarifaService) {
        this.recolectorTarifaService = recolectorTarifaService;
    }

    /**
     * {@code POST  /recolector-tarifas} : Create a new recolectorTarifa.
     *
     * @param recolectorTarifaDTO the recolectorTarifaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recolectorTarifaDTO, or with status {@code 400 (Bad Request)} if the recolectorTarifa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recolector-tarifas")
    public ResponseEntity<RecolectorTarifaDTO> createRecolectorTarifa(@Valid @RequestBody RecolectorTarifaDTO recolectorTarifaDTO) throws URISyntaxException {
        log.debug("REST request to save RecolectorTarifa : {}", recolectorTarifaDTO);
        if (recolectorTarifaDTO.getId() != null) {
            throw new BadRequestAlertException("A new recolectorTarifa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecolectorTarifaDTO result = recolectorTarifaService.save(recolectorTarifaDTO);
        return ResponseEntity.created(new URI("/api/recolector-tarifas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recolector-tarifas} : Updates an existing recolectorTarifa.
     *
     * @param recolectorTarifaDTO the recolectorTarifaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recolectorTarifaDTO,
     * or with status {@code 400 (Bad Request)} if the recolectorTarifaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recolectorTarifaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recolector-tarifas")
    public ResponseEntity<RecolectorTarifaDTO> updateRecolectorTarifa(@Valid @RequestBody RecolectorTarifaDTO recolectorTarifaDTO) throws URISyntaxException {
        log.debug("REST request to update RecolectorTarifa : {}", recolectorTarifaDTO);
        if (recolectorTarifaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecolectorTarifaDTO result = recolectorTarifaService.save(recolectorTarifaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recolectorTarifaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recolector-tarifas} : get all the recolectorTarifas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recolectorTarifas in body.
     */
    @GetMapping("/recolector-tarifas")
    public List<RecolectorTarifaDTO> getAllRecolectorTarifas() {
        log.debug("REST request to get all RecolectorTarifas");
        return recolectorTarifaService.findAll();
    }

    /**
     * {@code GET  /recolector-tarifas/:id} : get the "id" recolectorTarifa.
     *
     * @param id the id of the recolectorTarifaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recolectorTarifaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recolector-tarifas/{id}")
    public ResponseEntity<RecolectorTarifaDTO> getRecolectorTarifa(@PathVariable Long id) {
        log.debug("REST request to get RecolectorTarifa : {}", id);
        Optional<RecolectorTarifaDTO> recolectorTarifaDTO = recolectorTarifaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recolectorTarifaDTO);
    }

    /**
     * {@code DELETE  /recolector-tarifas/:id} : delete the "id" recolectorTarifa.
     *
     * @param id the id of the recolectorTarifaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recolector-tarifas/{id}")
    public ResponseEntity<Void> deleteRecolectorTarifa(@PathVariable Long id) {
        log.debug("REST request to delete RecolectorTarifa : {}", id);
        recolectorTarifaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
