package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.UsuarioImagenService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.UsuarioImagenDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.UsuarioImagen}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioImagenResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioImagenResource.class);

    private static final String ENTITY_NAME = "usuarioImagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioImagenService usuarioImagenService;

    public UsuarioImagenResource(UsuarioImagenService usuarioImagenService) {
        this.usuarioImagenService = usuarioImagenService;
    }

    /**
     * {@code POST  /usuario-imagens} : Create a new usuarioImagen.
     *
     * @param usuarioImagenDTO the usuarioImagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioImagenDTO, or with status {@code 400 (Bad Request)} if the usuarioImagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-imagens")
    public ResponseEntity<UsuarioImagenDTO> createUsuarioImagen(@RequestBody UsuarioImagenDTO usuarioImagenDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarioImagen : {}", usuarioImagenDTO);
        if (usuarioImagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioImagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioImagenDTO result = usuarioImagenService.save(usuarioImagenDTO);
        return ResponseEntity.created(new URI("/api/usuario-imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-imagens} : Updates an existing usuarioImagen.
     *
     * @param usuarioImagenDTO the usuarioImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioImagenDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioImagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-imagens")
    public ResponseEntity<UsuarioImagenDTO> updateUsuarioImagen(@RequestBody UsuarioImagenDTO usuarioImagenDTO) throws URISyntaxException {
        log.debug("REST request to update UsuarioImagen : {}", usuarioImagenDTO);
        if (usuarioImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioImagenDTO result = usuarioImagenService.save(usuarioImagenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioImagenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-imagens} : get all the usuarioImagens.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioImagens in body.
     */
    @GetMapping("/usuario-imagens")
    public List<UsuarioImagenDTO> getAllUsuarioImagens() {
        log.debug("REST request to get all UsuarioImagens");
        return usuarioImagenService.findAll();
    }

    /**
     * {@code GET  /usuario-imagens/:id} : get the "id" usuarioImagen.
     *
     * @param id the id of the usuarioImagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioImagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-imagens/{id}")
    public ResponseEntity<UsuarioImagenDTO> getUsuarioImagen(@PathVariable Long id) {
        log.debug("REST request to get UsuarioImagen : {}", id);
        Optional<UsuarioImagenDTO> usuarioImagenDTO = usuarioImagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioImagenDTO);
    }

    /**
     * {@code DELETE  /usuario-imagens/:id} : delete the "id" usuarioImagen.
     *
     * @param id the id of the usuarioImagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-imagens/{id}")
    public ResponseEntity<Void> deleteUsuarioImagen(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioImagen : {}", id);
        usuarioImagenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
