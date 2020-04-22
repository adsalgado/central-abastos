package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.PagosService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.PagosDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.Pagos}.
 */
@RestController
@RequestMapping("/api")
public class PagosResource {

    private final Logger log = LoggerFactory.getLogger(PagosResource.class);

    private static final String ENTITY_NAME = "pagos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagosService pagosService;

    public PagosResource(PagosService pagosService) {
        this.pagosService = pagosService;
    }

    /**
     * {@code POST  /pagos} : Create a new pagos.
     *
     * @param pagosDTO the pagosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagosDTO, or with status {@code 400 (Bad Request)} if the pagos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pagos")
    public ResponseEntity<PagosDTO> createPagos(@RequestBody PagosDTO pagosDTO) throws URISyntaxException {
        log.debug("REST request to save Pagos : {}", pagosDTO);
        if (pagosDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagosDTO result = pagosService.save(pagosDTO);
        return ResponseEntity.created(new URI("/api/pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pagos} : Updates an existing pagos.
     *
     * @param pagosDTO the pagosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagosDTO,
     * or with status {@code 400 (Bad Request)} if the pagosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pagos")
    public ResponseEntity<PagosDTO> updatePagos(@RequestBody PagosDTO pagosDTO) throws URISyntaxException {
        log.debug("REST request to update Pagos : {}", pagosDTO);
        if (pagosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PagosDTO result = pagosService.save(pagosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pagos} : get all the pagos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagos in body.
     */
    @GetMapping("/pagos")
    public List<PagosDTO> getAllPagos() {
        log.debug("REST request to get all Pagos");
        return pagosService.findAll();
    }

    /**
     * {@code GET  /pagos/:id} : get the "id" pagos.
     *
     * @param id the id of the pagosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagosDTO> getPagos(@PathVariable Long id) {
        log.debug("REST request to get Pagos : {}", id);
        Optional<PagosDTO> pagosDTO = pagosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagosDTO);
    }

    /**
     * {@code DELETE  /pagos/:id} : delete the "id" pagos.
     *
     * @param id the id of the pagosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> deletePagos(@PathVariable Long id) {
        log.debug("REST request to delete Pagos : {}", id);
        pagosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
