package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.ParametrosAplicacionService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.ParametrosAplicacionDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.ParametrosAplicacion}.
 */
@RestController
@RequestMapping("/api")
public class ParametrosAplicacionResource {

    private final Logger log = LoggerFactory.getLogger(ParametrosAplicacionResource.class);

    private static final String ENTITY_NAME = "parametrosAplicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrosAplicacionService parametrosAplicacionService;

    public ParametrosAplicacionResource(ParametrosAplicacionService parametrosAplicacionService) {
        this.parametrosAplicacionService = parametrosAplicacionService;
    }

    /**
     * {@code POST  /parametros-aplicacions} : Create a new parametrosAplicacion.
     *
     * @param parametrosAplicacionDTO the parametrosAplicacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametrosAplicacionDTO, or with status {@code 400 (Bad Request)} if the parametrosAplicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametros-aplicacions")
    public ResponseEntity<ParametrosAplicacionDTO> createParametrosAplicacion(@Valid @RequestBody ParametrosAplicacionDTO parametrosAplicacionDTO) throws URISyntaxException {
        log.debug("REST request to save ParametrosAplicacion : {}", parametrosAplicacionDTO);
        if (parametrosAplicacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new parametrosAplicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametrosAplicacionDTO result = parametrosAplicacionService.save(parametrosAplicacionDTO);
        return ResponseEntity.created(new URI("/api/parametros-aplicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametros-aplicacions} : Updates an existing parametrosAplicacion.
     *
     * @param parametrosAplicacionDTO the parametrosAplicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrosAplicacionDTO,
     * or with status {@code 400 (Bad Request)} if the parametrosAplicacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametrosAplicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametros-aplicacions")
    public ResponseEntity<ParametrosAplicacionDTO> updateParametrosAplicacion(@Valid @RequestBody ParametrosAplicacionDTO parametrosAplicacionDTO) throws URISyntaxException {
        log.debug("REST request to update ParametrosAplicacion : {}", parametrosAplicacionDTO);
        if (parametrosAplicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametrosAplicacionDTO result = parametrosAplicacionService.save(parametrosAplicacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrosAplicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametros-aplicacions} : get all the parametrosAplicacions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametrosAplicacions in body.
     */
    @GetMapping("/parametros-aplicacions")
    public List<ParametrosAplicacionDTO> getAllParametrosAplicacions() {
        log.debug("REST request to get all ParametrosAplicacions");
        return parametrosAplicacionService.findAll();
    }

    /**
     * {@code GET  /parametros-aplicacions/:id} : get the "id" parametrosAplicacion.
     *
     * @param id the id of the parametrosAplicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametrosAplicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametros-aplicacions/{id}")
    public ResponseEntity<ParametrosAplicacionDTO> getParametrosAplicacion(@PathVariable Long id) {
        log.debug("REST request to get ParametrosAplicacion : {}", id);
        Optional<ParametrosAplicacionDTO> parametrosAplicacionDTO = parametrosAplicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametrosAplicacionDTO);
    }

    /**
     * {@code DELETE  /parametros-aplicacions/:id} : delete the "id" parametrosAplicacion.
     *
     * @param id the id of the parametrosAplicacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametros-aplicacions/{id}")
    public ResponseEntity<Void> deleteParametrosAplicacion(@PathVariable Long id) {
        log.debug("REST request to delete ParametrosAplicacion : {}", id);
        parametrosAplicacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
