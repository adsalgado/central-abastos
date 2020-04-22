package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.InventarioHistoricoService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.InventarioHistoricoDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.InventarioHistorico}.
 */
@RestController
@RequestMapping("/api")
public class InventarioHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(InventarioHistoricoResource.class);

    private static final String ENTITY_NAME = "inventarioHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventarioHistoricoService inventarioHistoricoService;

    public InventarioHistoricoResource(InventarioHistoricoService inventarioHistoricoService) {
        this.inventarioHistoricoService = inventarioHistoricoService;
    }

    /**
     * {@code POST  /inventario-historicos} : Create a new inventarioHistorico.
     *
     * @param inventarioHistoricoDTO the inventarioHistoricoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventarioHistoricoDTO, or with status {@code 400 (Bad Request)} if the inventarioHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventario-historicos")
    public ResponseEntity<InventarioHistoricoDTO> createInventarioHistorico(@Valid @RequestBody InventarioHistoricoDTO inventarioHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to save InventarioHistorico : {}", inventarioHistoricoDTO);
        if (inventarioHistoricoDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventarioHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventarioHistoricoDTO result = inventarioHistoricoService.save(inventarioHistoricoDTO);
        return ResponseEntity.created(new URI("/api/inventario-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventario-historicos} : Updates an existing inventarioHistorico.
     *
     * @param inventarioHistoricoDTO the inventarioHistoricoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventarioHistoricoDTO,
     * or with status {@code 400 (Bad Request)} if the inventarioHistoricoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventarioHistoricoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventario-historicos")
    public ResponseEntity<InventarioHistoricoDTO> updateInventarioHistorico(@Valid @RequestBody InventarioHistoricoDTO inventarioHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to update InventarioHistorico : {}", inventarioHistoricoDTO);
        if (inventarioHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventarioHistoricoDTO result = inventarioHistoricoService.save(inventarioHistoricoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventarioHistoricoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventario-historicos} : get all the inventarioHistoricos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventarioHistoricos in body.
     */
    @GetMapping("/inventario-historicos")
    public List<InventarioHistoricoDTO> getAllInventarioHistoricos() {
        log.debug("REST request to get all InventarioHistoricos");
        return inventarioHistoricoService.findAll();
    }

    /**
     * {@code GET  /inventario-historicos/:id} : get the "id" inventarioHistorico.
     *
     * @param id the id of the inventarioHistoricoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventarioHistoricoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventario-historicos/{id}")
    public ResponseEntity<InventarioHistoricoDTO> getInventarioHistorico(@PathVariable Long id) {
        log.debug("REST request to get InventarioHistorico : {}", id);
        Optional<InventarioHistoricoDTO> inventarioHistoricoDTO = inventarioHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventarioHistoricoDTO);
    }

    /**
     * {@code DELETE  /inventario-historicos/:id} : delete the "id" inventarioHistorico.
     *
     * @param id the id of the inventarioHistoricoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventario-historicos/{id}")
    public ResponseEntity<Void> deleteInventarioHistorico(@PathVariable Long id) {
        log.debug("REST request to delete InventarioHistorico : {}", id);
        inventarioHistoricoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
