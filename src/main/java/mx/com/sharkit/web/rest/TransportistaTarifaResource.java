package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.TransportistaTarifaService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.TransportistaTarifaDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.TransportistaTarifa}.
 */
@RestController
@RequestMapping("/api")
public class TransportistaTarifaResource {

    private final Logger log = LoggerFactory.getLogger(TransportistaTarifaResource.class);

    private static final String ENTITY_NAME = "transportistaTarifa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportistaTarifaService transportistaTarifaService;

    public TransportistaTarifaResource(TransportistaTarifaService transportistaTarifaService) {
        this.transportistaTarifaService = transportistaTarifaService;
    }

    /**
     * {@code POST  /transportista-tarifas} : Create a new transportistaTarifa.
     *
     * @param transportistaTarifaDTO the transportistaTarifaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportistaTarifaDTO, or with status {@code 400 (Bad Request)} if the transportistaTarifa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transportista-tarifas")
    public ResponseEntity<TransportistaTarifaDTO> createTransportistaTarifa(@Valid @RequestBody TransportistaTarifaDTO transportistaTarifaDTO) throws URISyntaxException {
        log.debug("REST request to save TransportistaTarifa : {}", transportistaTarifaDTO);
        if (transportistaTarifaDTO.getId() != null) {
            throw new BadRequestAlertException("A new transportistaTarifa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransportistaTarifaDTO result = transportistaTarifaService.save(transportistaTarifaDTO);
        return ResponseEntity.created(new URI("/api/transportista-tarifas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transportista-tarifas} : Updates an existing transportistaTarifa.
     *
     * @param transportistaTarifaDTO the transportistaTarifaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportistaTarifaDTO,
     * or with status {@code 400 (Bad Request)} if the transportistaTarifaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportistaTarifaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transportista-tarifas")
    public ResponseEntity<TransportistaTarifaDTO> updateTransportistaTarifa(@Valid @RequestBody TransportistaTarifaDTO transportistaTarifaDTO) throws URISyntaxException {
        log.debug("REST request to update TransportistaTarifa : {}", transportistaTarifaDTO);
        if (transportistaTarifaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransportistaTarifaDTO result = transportistaTarifaService.save(transportistaTarifaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transportistaTarifaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transportista-tarifas} : get all the transportistaTarifas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportistaTarifas in body.
     */
    @GetMapping("/transportista-tarifas")
    public List<TransportistaTarifaDTO> getAllTransportistaTarifas() {
        log.debug("REST request to get all TransportistaTarifas");
        return transportistaTarifaService.findAll();
    }

    /**
     * {@code GET  /transportista-tarifas/:id} : get the "id" transportistaTarifa.
     *
     * @param id the id of the transportistaTarifaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportistaTarifaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transportista-tarifas/{id}")
    public ResponseEntity<TransportistaTarifaDTO> getTransportistaTarifa(@PathVariable Long id) {
        log.debug("REST request to get TransportistaTarifa : {}", id);
        Optional<TransportistaTarifaDTO> transportistaTarifaDTO = transportistaTarifaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transportistaTarifaDTO);
    }

    /**
     * {@code DELETE  /transportista-tarifas/:id} : delete the "id" transportistaTarifa.
     *
     * @param id the id of the transportistaTarifaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transportista-tarifas/{id}")
    public ResponseEntity<Void> deleteTransportistaTarifa(@PathVariable Long id) {
        log.debug("REST request to delete TransportistaTarifa : {}", id);
        transportistaTarifaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /transportista-tarifas/transportista/{transportistaId}} : get all the transportistaTarifas by transportistaId.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportistaTarifas in body.
     */
    @GetMapping("/transportista-tarifas/transportista/{transportistaId}")
    public List<TransportistaTarifaDTO> getAllTransportistaTarifasByTransportistaId(@PathVariable Long transportistaId) {
        log.debug("REST request to get all TransportistaTarifas by transportistaId: {}", transportistaId);
        return transportistaTarifaService.findAllByTransportistaId(transportistaId);
    }

}
