package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.OfertaProveedorService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.OfertaProveedorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.OfertaProveedor}.
 */
@RestController
@RequestMapping("/api")
public class OfertaProveedorResource {

    private final Logger log = LoggerFactory.getLogger(OfertaProveedorResource.class);

    private static final String ENTITY_NAME = "ofertaProveedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfertaProveedorService ofertaProveedorService;

    public OfertaProveedorResource(OfertaProveedorService ofertaProveedorService) {
        this.ofertaProveedorService = ofertaProveedorService;
    }

    /**
     * {@code POST  /oferta-proveedors} : Create a new ofertaProveedor.
     *
     * @param ofertaProveedorDTO the ofertaProveedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ofertaProveedorDTO, or with status {@code 400 (Bad Request)} if the ofertaProveedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oferta-proveedors")
    public ResponseEntity<OfertaProveedorDTO> createOfertaProveedor(@RequestBody OfertaProveedorDTO ofertaProveedorDTO) throws URISyntaxException {
        log.debug("REST request to save OfertaProveedor : {}", ofertaProveedorDTO);
        if (ofertaProveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new ofertaProveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfertaProveedorDTO result = ofertaProveedorService.save(ofertaProveedorDTO);
        return ResponseEntity.created(new URI("/api/oferta-proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oferta-proveedors} : Updates an existing ofertaProveedor.
     *
     * @param ofertaProveedorDTO the ofertaProveedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ofertaProveedorDTO,
     * or with status {@code 400 (Bad Request)} if the ofertaProveedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ofertaProveedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/oferta-proveedors")
    public ResponseEntity<OfertaProveedorDTO> updateOfertaProveedor(@RequestBody OfertaProveedorDTO ofertaProveedorDTO) throws URISyntaxException {
        log.debug("REST request to update OfertaProveedor : {}", ofertaProveedorDTO);
        if (ofertaProveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfertaProveedorDTO result = ofertaProveedorService.save(ofertaProveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ofertaProveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /oferta-proveedors} : get all the ofertaProveedors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ofertaProveedors in body.
     */
    @GetMapping("/oferta-proveedors")
    public List<OfertaProveedorDTO> getAllOfertaProveedors() {
        log.debug("REST request to get all OfertaProveedors");
        return ofertaProveedorService.findAll();
    }

    /**
     * {@code GET  /oferta-proveedors/:id} : get the "id" ofertaProveedor.
     *
     * @param id the id of the ofertaProveedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ofertaProveedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oferta-proveedors/{id}")
    public ResponseEntity<OfertaProveedorDTO> getOfertaProveedor(@PathVariable Long id) {
        log.debug("REST request to get OfertaProveedor : {}", id);
        Optional<OfertaProveedorDTO> ofertaProveedorDTO = ofertaProveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ofertaProveedorDTO);
    }

    /**
     * {@code DELETE  /oferta-proveedors/:id} : delete the "id" ofertaProveedor.
     *
     * @param id the id of the ofertaProveedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oferta-proveedors/{id}")
    public ResponseEntity<Void> deleteOfertaProveedor(@PathVariable Long id) {
        log.debug("REST request to delete OfertaProveedor : {}", id);
        ofertaProveedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
