package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.TipoOfertaService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.TipoOfertaDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.TipoOferta}.
 */
@RestController
@RequestMapping("/api")
public class TipoOfertaResource {

    private final Logger log = LoggerFactory.getLogger(TipoOfertaResource.class);

    private static final String ENTITY_NAME = "tipoOferta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoOfertaService tipoOfertaService;

    public TipoOfertaResource(TipoOfertaService tipoOfertaService) {
        this.tipoOfertaService = tipoOfertaService;
    }

    /**
     * {@code POST  /tipo-ofertas} : Create a new tipoOferta.
     *
     * @param tipoOfertaDTO the tipoOfertaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoOfertaDTO, or with status {@code 400 (Bad Request)} if the tipoOferta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-ofertas")
    public ResponseEntity<TipoOfertaDTO> createTipoOferta(@Valid @RequestBody TipoOfertaDTO tipoOfertaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoOferta : {}", tipoOfertaDTO);
        if (tipoOfertaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoOferta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoOfertaDTO result = tipoOfertaService.save(tipoOfertaDTO);
        return ResponseEntity.created(new URI("/api/tipo-ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-ofertas} : Updates an existing tipoOferta.
     *
     * @param tipoOfertaDTO the tipoOfertaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoOfertaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoOfertaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoOfertaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-ofertas")
    public ResponseEntity<TipoOfertaDTO> updateTipoOferta(@Valid @RequestBody TipoOfertaDTO tipoOfertaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoOferta : {}", tipoOfertaDTO);
        if (tipoOfertaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoOfertaDTO result = tipoOfertaService.save(tipoOfertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoOfertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-ofertas} : get all the tipoOfertas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoOfertas in body.
     */
    @GetMapping("/tipo-ofertas")
    public List<TipoOfertaDTO> getAllTipoOfertas() {
        log.debug("REST request to get all TipoOfertas");
        return tipoOfertaService.findAll();
    }

    /**
     * {@code GET  /tipo-ofertas/:id} : get the "id" tipoOferta.
     *
     * @param id the id of the tipoOfertaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoOfertaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-ofertas/{id}")
    public ResponseEntity<TipoOfertaDTO> getTipoOferta(@PathVariable Long id) {
        log.debug("REST request to get TipoOferta : {}", id);
        Optional<TipoOfertaDTO> tipoOfertaDTO = tipoOfertaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoOfertaDTO);
    }

    /**
     * {@code DELETE  /tipo-ofertas/:id} : delete the "id" tipoOferta.
     *
     * @param id the id of the tipoOfertaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-ofertas/{id}")
    public ResponseEntity<Void> deleteTipoOferta(@PathVariable Long id) {
        log.debug("REST request to delete TipoOferta : {}", id);
        tipoOfertaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
