package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.SeccionService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.SeccionDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.Seccion}.
 */
@RestController
@RequestMapping("/api")
public class SeccionResource {

    private final Logger log = LoggerFactory.getLogger(SeccionResource.class);

    private static final String ENTITY_NAME = "seccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionService seccionService;

    public SeccionResource(SeccionService seccionService) {
        this.seccionService = seccionService;
    }

    /**
     * {@code POST  /seccions} : Create a new seccion.
     *
     * @param seccionDTO the seccionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccionDTO, or with status {@code 400 (Bad Request)} if the seccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccions")
    public ResponseEntity<SeccionDTO> createSeccion(@Valid @RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to save Seccion : {}", seccionDTO);
        if (seccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new seccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.created(new URI("/api/seccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccions} : Updates an existing seccion.
     *
     * @param seccionDTO the seccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionDTO,
     * or with status {@code 400 (Bad Request)} if the seccionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccions")
    public ResponseEntity<SeccionDTO> updateSeccion(@Valid @RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to update Seccion : {}", seccionDTO);
        if (seccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seccions} : get all the seccions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccions in body.
     */
    @GetMapping("/seccions")
    public List<SeccionDTO> getAllSeccions() {
        log.debug("REST request to get all Seccions");
        return seccionService.findAll();
    }

    /**
     * {@code GET  /seccions/:id} : get the "id" seccion.
     *
     * @param id the id of the seccionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccions/{id}")
    public ResponseEntity<SeccionDTO> getSeccion(@PathVariable Long id) {
        log.debug("REST request to get Seccion : {}", id);
        Optional<SeccionDTO> seccionDTO = seccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seccionDTO);
    }

    /**
     * {@code DELETE  /seccions/:id} : delete the "id" seccion.
     *
     * @param id the id of the seccionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccions/{id}")
    public ResponseEntity<Void> deleteSeccion(@PathVariable Long id) {
        log.debug("REST request to delete Seccion : {}", id);
        seccionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
