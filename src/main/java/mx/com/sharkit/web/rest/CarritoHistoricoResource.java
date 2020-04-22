package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.CarritoHistoricoService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.CarritoHistoricoDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.CarritoHistorico}.
 */
@RestController
@RequestMapping("/api")
public class CarritoHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(CarritoHistoricoResource.class);

    private static final String ENTITY_NAME = "carritoHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoHistoricoService carritoHistoricoService;

    public CarritoHistoricoResource(CarritoHistoricoService carritoHistoricoService) {
        this.carritoHistoricoService = carritoHistoricoService;
    }

    /**
     * {@code POST  /carrito-historicos} : Create a new carritoHistorico.
     *
     * @param carritoHistoricoDTO the carritoHistoricoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoHistoricoDTO, or with status {@code 400 (Bad Request)} if the carritoHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrito-historicos")
    public ResponseEntity<CarritoHistoricoDTO> createCarritoHistorico(@Valid @RequestBody CarritoHistoricoDTO carritoHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to save CarritoHistorico : {}", carritoHistoricoDTO);
        if (carritoHistoricoDTO.getId() != null) {
            throw new BadRequestAlertException("A new carritoHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarritoHistoricoDTO result = carritoHistoricoService.save(carritoHistoricoDTO);
        return ResponseEntity.created(new URI("/api/carrito-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrito-historicos} : Updates an existing carritoHistorico.
     *
     * @param carritoHistoricoDTO the carritoHistoricoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoHistoricoDTO,
     * or with status {@code 400 (Bad Request)} if the carritoHistoricoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoHistoricoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrito-historicos")
    public ResponseEntity<CarritoHistoricoDTO> updateCarritoHistorico(@Valid @RequestBody CarritoHistoricoDTO carritoHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to update CarritoHistorico : {}", carritoHistoricoDTO);
        if (carritoHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarritoHistoricoDTO result = carritoHistoricoService.save(carritoHistoricoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoHistoricoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrito-historicos} : get all the carritoHistoricos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritoHistoricos in body.
     */
    @GetMapping("/carrito-historicos")
    public List<CarritoHistoricoDTO> getAllCarritoHistoricos() {
        log.debug("REST request to get all CarritoHistoricos");
        return carritoHistoricoService.findAll();
    }

    /**
     * {@code GET  /carrito-historicos/:id} : get the "id" carritoHistorico.
     *
     * @param id the id of the carritoHistoricoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoHistoricoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-historicos/{id}")
    public ResponseEntity<CarritoHistoricoDTO> getCarritoHistorico(@PathVariable Long id) {
        log.debug("REST request to get CarritoHistorico : {}", id);
        Optional<CarritoHistoricoDTO> carritoHistoricoDTO = carritoHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoHistoricoDTO);
    }

    /**
     * {@code DELETE  /carrito-historicos/:id} : delete the "id" carritoHistorico.
     *
     * @param id the id of the carritoHistoricoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrito-historicos/{id}")
    public ResponseEntity<Void> deleteCarritoHistorico(@PathVariable Long id) {
        log.debug("REST request to delete CarritoHistorico : {}", id);
        carritoHistoricoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
