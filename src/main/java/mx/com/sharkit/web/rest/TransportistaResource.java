package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.TransportistaService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.TransportistaDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.Transportista}.
 */
@RestController
@RequestMapping("/api")
public class TransportistaResource {

    private final Logger log = LoggerFactory.getLogger(TransportistaResource.class);

    private static final String ENTITY_NAME = "transportista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportistaService transportistaService;

    public TransportistaResource(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    /**
     * {@code POST  /transportistas} : Create a new transportista.
     *
     * @param transportistaDTO the transportistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportistaDTO, or with status {@code 400 (Bad Request)} if the transportista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transportistas")
    public ResponseEntity<TransportistaDTO> createTransportista(@Valid @RequestBody TransportistaDTO transportistaDTO) throws URISyntaxException {
        log.debug("REST request to save Transportista : {}", transportistaDTO);
        if (transportistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new transportista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransportistaDTO result = transportistaService.save(transportistaDTO);
        return ResponseEntity.created(new URI("/api/transportistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transportistas} : Updates an existing transportista.
     *
     * @param transportistaDTO the transportistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportistaDTO,
     * or with status {@code 400 (Bad Request)} if the transportistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transportistas")
    public ResponseEntity<TransportistaDTO> updateTransportista(@Valid @RequestBody TransportistaDTO transportistaDTO) throws URISyntaxException {
        log.debug("REST request to update Transportista : {}", transportistaDTO);
        if (transportistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransportistaDTO result = transportistaService.save(transportistaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transportistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transportistas} : get all the transportistas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportistas in body.
     */
    @GetMapping("/transportistas")
    public List<TransportistaDTO> getAllTransportistas() {
        log.debug("REST request to get all Transportistas");
        return transportistaService.findAll();
    }

    /**
     * {@code GET  /transportistas/:id} : get the "id" transportista.
     *
     * @param id the id of the transportistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transportistas/{id}")
    public ResponseEntity<TransportistaDTO> getTransportista(@PathVariable Long id) {
        log.debug("REST request to get Transportista : {}", id);
        Optional<TransportistaDTO> transportistaDTO = transportistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transportistaDTO);
    }

    /**
     * {@code DELETE  /transportistas/:id} : delete the "id" transportista.
     *
     * @param id the id of the transportistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transportistas/{id}")
    public ResponseEntity<Void> deleteTransportista(@PathVariable Long id) {
        log.debug("REST request to delete Transportista : {}", id);
        transportistaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
