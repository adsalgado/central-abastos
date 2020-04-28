package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.TarjetaService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.TarjetaDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Tarjeta}.
 */
@RestController
@RequestMapping("/api")
public class TarjetaResource {

    private final Logger log = LoggerFactory.getLogger(TarjetaResource.class);

    private static final String ENTITY_NAME = "tarjeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarjetaService tarjetaService;
    
    private final UserService userService;

    public TarjetaResource(TarjetaService tarjetaService, UserService userService) {
        this.tarjetaService = tarjetaService;
        this.userService = userService;
    }

    /**
     * {@code POST  /tarjetas} : Create a new tarjeta.
     *
     * @param tarjetaDTO the tarjetaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarjetaDTO, or with status {@code 400 (Bad Request)} if the tarjeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarjetas")
    public ResponseEntity<TarjetaDTO> createTarjeta(@Valid @RequestBody TarjetaDTO tarjetaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarjeta : {}", tarjetaDTO);
        if (tarjetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarjeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<User> user = userService.getUserWithAuthorities();
        Long usuarioId =  user.isPresent() ? user.get().getId() : 0L;
        if (usuarioId == 0) {
            throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
        }
        
        LocalDateTime now = LocalDateTime.now();
        tarjetaDTO.setUsuarioId(usuarioId);
        tarjetaDTO.setFechaAlta(now);

        TarjetaDTO result = tarjetaService.save(tarjetaDTO);
        return ResponseEntity.created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarjetas} : Updates an existing tarjeta.
     *
     * @param tarjetaDTO the tarjetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarjetaDTO,
     * or with status {@code 400 (Bad Request)} if the tarjetaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarjetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarjetas")
    public ResponseEntity<TarjetaDTO> updateTarjeta(@Valid @RequestBody TarjetaDTO tarjetaDTO) throws URISyntaxException {
        log.debug("REST request to update Tarjeta : {}", tarjetaDTO);
        if (tarjetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        Optional<User> user = userService.getUserWithAuthorities();
        Long usuarioId =  user.isPresent() ? user.get().getId() : 0L;
        if (usuarioId == 0) {
            throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
        }
        
        LocalDateTime now = LocalDateTime.now();
        tarjetaDTO.setUsuarioId(usuarioId);
        tarjetaDTO.setFechaAlta(now);
        
        TarjetaDTO result = tarjetaService.save(tarjetaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarjetas} : get all the tarjetas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarjetas in body.
     */
    @GetMapping("/tarjetas")
    public List<TarjetaDTO> getAllTarjetas() {
        log.debug("REST request to get all Tarjetas");
        Optional<User> user = userService.getUserWithAuthorities();
        Long usuarioId =  user.isPresent() ? user.get().getId() : 0L;
        if (usuarioId == 0) {
            throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
        }
        
        return tarjetaService.findByUsuarioId(usuarioId);
    }

    /**
     * {@code GET  /tarjetas/:id} : get the "id" tarjeta.
     *
     * @param id the id of the tarjetaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarjetaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarjetas/{id}")
    public ResponseEntity<TarjetaDTO> getTarjeta(@PathVariable Long id) {
        log.debug("REST request to get Tarjeta : {}", id);
        Optional<TarjetaDTO> tarjetaDTO = tarjetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarjetaDTO);
    }

    /**
     * {@code DELETE  /tarjetas/:id} : delete the "id" tarjeta.
     *
     * @param id the id of the tarjetaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarjetas/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        log.debug("REST request to delete Tarjeta : {}", id);
        tarjetaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
