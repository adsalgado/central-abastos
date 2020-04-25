package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.TipoArticuloService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.TipoArticuloDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.TipoArticulo}.
 */
@RestController
@RequestMapping("/api")
public class TipoArticuloResource {

    private final Logger log = LoggerFactory.getLogger(TipoArticuloResource.class);

    private static final String ENTITY_NAME = "tipoArticulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoArticuloService tipoArticuloService;

    public TipoArticuloResource(TipoArticuloService tipoArticuloService) {
        this.tipoArticuloService = tipoArticuloService;
    }

    /**
     * {@code POST  /tipo-articulos} : Create a new tipoArticulo.
     *
     * @param tipoArticuloDTO the tipoArticuloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoArticuloDTO, or with status {@code 400 (Bad Request)} if the tipoArticulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-articulos")
    public ResponseEntity<TipoArticuloDTO> createTipoArticulo(@Valid @RequestBody TipoArticuloDTO tipoArticuloDTO) throws URISyntaxException {
        log.debug("REST request to save TipoArticulo : {}", tipoArticuloDTO);
        if (tipoArticuloDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoArticulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoArticuloDTO result = tipoArticuloService.save(tipoArticuloDTO);
        return ResponseEntity.created(new URI("/api/tipo-articulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-articulos} : Updates an existing tipoArticulo.
     *
     * @param tipoArticuloDTO the tipoArticuloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoArticuloDTO,
     * or with status {@code 400 (Bad Request)} if the tipoArticuloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoArticuloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-articulos")
    public ResponseEntity<TipoArticuloDTO> updateTipoArticulo(@Valid @RequestBody TipoArticuloDTO tipoArticuloDTO) throws URISyntaxException {
        log.debug("REST request to update TipoArticulo : {}", tipoArticuloDTO);
        if (tipoArticuloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoArticuloDTO result = tipoArticuloService.save(tipoArticuloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoArticuloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-articulos} : get all the tipoArticulos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoArticulos in body.
     */
    @GetMapping("/tipo-articulos")
    public List<TipoArticuloDTO> getAllTipoArticulos() {
        log.debug("REST request to get all TipoArticulos");
        return tipoArticuloService.findAll();
    }

    /**
     * {@code GET  /tipo-articulos/:id} : get the "id" tipoArticulo.
     *
     * @param id the id of the tipoArticuloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoArticuloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-articulos/{id}")
    public ResponseEntity<TipoArticuloDTO> getTipoArticulo(@PathVariable Long id) {
        log.debug("REST request to get TipoArticulo : {}", id);
        Optional<TipoArticuloDTO> tipoArticuloDTO = tipoArticuloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoArticuloDTO);
    }

    /**
     * {@code DELETE  /tipo-articulos/:id} : delete the "id" tipoArticulo.
     *
     * @param id the id of the tipoArticuloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-articulos/{id}")
    public ResponseEntity<Void> deleteTipoArticulo(@PathVariable Long id) {
        log.debug("REST request to delete TipoArticulo : {}", id);
        tipoArticuloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /tipo-articulos} : get all the tipoArticulos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoArticulos in body.
     */
    @GetMapping("/tipo-articulos/categoria/{categoriaId}")
    public List<TipoArticuloDTO> getAllTipoArticulosByCategoria(@PathVariable Long categoriaId) {
        log.debug("REST request to get all TipoArticulos by categoriaId {}", categoriaId);
        return tipoArticuloService.findByCategoriaId(categoriaId);
    }

}
