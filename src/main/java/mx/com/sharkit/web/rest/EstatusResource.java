package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.EstatusService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.EstatusDTO;

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
     * {@code POST  /estatuses} : Create a new estatus.
     *
     * @param estatusDTO the estatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusDTO, or with status {@code 400 (Bad Request)} if the estatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatuses")
    public ResponseEntity<EstatusDTO> createEstatus(@Valid @RequestBody EstatusDTO estatusDTO) throws URISyntaxException {
        log.debug("REST request to save Estatus : {}", estatusDTO);
        if (estatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusDTO result = estatusService.save(estatusDTO);
        return ResponseEntity.created(new URI("/api/estatuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatuses} : Updates an existing estatus.
     *
     * @param estatusDTO the estatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusDTO,
     * or with status {@code 400 (Bad Request)} if the estatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatuses")
    public ResponseEntity<EstatusDTO> updateEstatus(@Valid @RequestBody EstatusDTO estatusDTO) throws URISyntaxException {
        log.debug("REST request to update Estatus : {}", estatusDTO);
        if (estatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusDTO result = estatusService.save(estatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatuses} : get all the estatuses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatuses in body.
     */
    @GetMapping("/estatuses")
    public List<EstatusDTO> getAllEstatuses() {
        log.debug("REST request to get all Estatuses");
        return estatusService.findAll();
    }

    /**
     * {@code GET  /estatuses/:id} : get the "id" estatus.
     *
     * @param id the id of the estatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatuses/{id}")
    public ResponseEntity<EstatusDTO> getEstatus(@PathVariable Long id) {
        log.debug("REST request to get Estatus : {}", id);
        Optional<EstatusDTO> estatusDTO = estatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusDTO);
    }

    /**
     * {@code DELETE  /estatuses/:id} : delete the "id" estatus.
     *
     * @param id the id of the estatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatuses/{id}")
    public ResponseEntity<Void> deleteEstatus(@PathVariable Long id) {
        log.debug("REST request to delete Estatus : {}", id);
        estatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
