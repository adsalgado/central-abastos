package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.UsuarioDireccionService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.UsuarioDireccionDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.UsuarioDireccion}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioDireccionResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioDireccionResource.class);

    private static final String ENTITY_NAME = "usuarioDireccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioDireccionService usuarioDireccionService;

    public UsuarioDireccionResource(UsuarioDireccionService usuarioDireccionService) {
        this.usuarioDireccionService = usuarioDireccionService;
    }

    /**
     * {@code POST  /usuario-direccions} : Create a new usuarioDireccion.
     *
     * @param usuarioDireccionDTO the usuarioDireccionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioDireccionDTO, or with status {@code 400 (Bad Request)} if the usuarioDireccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-direccions")
    public ResponseEntity<UsuarioDireccionDTO> createUsuarioDireccion(@RequestBody UsuarioDireccionDTO usuarioDireccionDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarioDireccion : {}", usuarioDireccionDTO);
        if (usuarioDireccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioDireccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioDireccionDTO result = usuarioDireccionService.save(usuarioDireccionDTO);
        return ResponseEntity.created(new URI("/api/usuario-direccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-direccions} : Updates an existing usuarioDireccion.
     *
     * @param usuarioDireccionDTO the usuarioDireccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioDireccionDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioDireccionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioDireccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-direccions")
    public ResponseEntity<UsuarioDireccionDTO> updateUsuarioDireccion(@RequestBody UsuarioDireccionDTO usuarioDireccionDTO) throws URISyntaxException {
        log.debug("REST request to update UsuarioDireccion : {}", usuarioDireccionDTO);
        if (usuarioDireccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioDireccionDTO result = usuarioDireccionService.save(usuarioDireccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioDireccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-direccions} : get all the usuarioDireccions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioDireccions in body.
     */
    @GetMapping("/usuario-direccions")
    public List<UsuarioDireccionDTO> getAllUsuarioDireccions() {
        log.debug("REST request to get all UsuarioDireccions");
        return usuarioDireccionService.findAll();
    }

    /**
     * {@code GET  /usuario-direccions/:id} : get the "id" usuarioDireccion.
     *
     * @param id the id of the usuarioDireccionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioDireccionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-direccions/{id}")
    public ResponseEntity<UsuarioDireccionDTO> getUsuarioDireccion(@PathVariable Long id) {
        log.debug("REST request to get UsuarioDireccion : {}", id);
        Optional<UsuarioDireccionDTO> usuarioDireccionDTO = usuarioDireccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioDireccionDTO);
    }

    /**
     * {@code DELETE  /usuario-direccions/:id} : delete the "id" usuarioDireccion.
     *
     * @param id the id of the usuarioDireccionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-direccions/{id}")
    public ResponseEntity<Void> deleteUsuarioDireccion(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioDireccion : {}", id);
        usuarioDireccionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
