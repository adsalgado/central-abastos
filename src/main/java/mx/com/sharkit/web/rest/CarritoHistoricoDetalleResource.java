package mx.com.sharkit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import mx.com.sharkit.service.CarritoHistoricoDetalleService;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.CarritoHistoricoDetalle}.
 */
@RestController
@RequestMapping("/api")
public class CarritoHistoricoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(CarritoHistoricoDetalleResource.class);

    private static final String ENTITY_NAME = "carritoHistoricoDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoHistoricoDetalleService carritoHistoricoDetalleService;

    public CarritoHistoricoDetalleResource(CarritoHistoricoDetalleService carritoHistoricoDetalleService) {
        this.carritoHistoricoDetalleService = carritoHistoricoDetalleService;
    }

    /**
     * {@code POST  /carrito-historico-detalles} : Create a new carritoHistoricoDetalle.
     *
     * @param carritoHistoricoDetalleDTO the carritoHistoricoDetalleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoHistoricoDetalleDTO, or with status {@code 400 (Bad Request)} if the carritoHistoricoDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrito-historico-detalles")
    public ResponseEntity<CarritoHistoricoDetalleDTO> createCarritoHistoricoDetalle(@Valid @RequestBody CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save CarritoHistoricoDetalle : {}", carritoHistoricoDetalleDTO);
        if (carritoHistoricoDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new carritoHistoricoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarritoHistoricoDetalleDTO result = carritoHistoricoDetalleService.save(carritoHistoricoDetalleDTO);
        return ResponseEntity.created(new URI("/api/carrito-historico-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrito-historico-detalles} : Updates an existing carritoHistoricoDetalle.
     *
     * @param carritoHistoricoDetalleDTO the carritoHistoricoDetalleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoHistoricoDetalleDTO,
     * or with status {@code 400 (Bad Request)} if the carritoHistoricoDetalleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoHistoricoDetalleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrito-historico-detalles")
    public ResponseEntity<CarritoHistoricoDetalleDTO> updateCarritoHistoricoDetalle(@Valid @RequestBody CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to update CarritoHistoricoDetalle : {}", carritoHistoricoDetalleDTO);
        if (carritoHistoricoDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<CarritoHistoricoDetalleDTO> optHistoricoDetalle = 
        		carritoHistoricoDetalleService.findOne(carritoHistoricoDetalleDTO.getId());
        CarritoHistoricoDetalleDTO result = null;
        if (optHistoricoDetalle.isPresent()) {
        	CarritoHistoricoDetalleDTO historicoDetalle = optHistoricoDetalle.get();
        	log.debug("historicoDetalle: {}", historicoDetalle);
        	historicoDetalle.setCantidad(carritoHistoricoDetalleDTO.getCantidad());
        	result = carritoHistoricoDetalleService.save(historicoDetalle);
        } else {
        	throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        result = result != null ? result : carritoHistoricoDetalleDTO;
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoHistoricoDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrito-historico-detalles} : get all the carritoHistoricoDetalles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritoHistoricoDetalles in body.
     */
    @GetMapping("/carrito-historico-detalles")
    public List<CarritoHistoricoDetalleDTO> getAllCarritoHistoricoDetalles() {
        log.debug("REST request to get all CarritoHistoricoDetalles");
        return carritoHistoricoDetalleService.findAll();
    }

    /**
     * {@code GET  /carrito-historico-detalles/:id} : get the "id" carritoHistoricoDetalle.
     *
     * @param id the id of the carritoHistoricoDetalleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoHistoricoDetalleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-historico-detalles/{id}")
    public ResponseEntity<CarritoHistoricoDetalleDTO> getCarritoHistoricoDetalle(@PathVariable Long id) {
        log.debug("REST request to get CarritoHistoricoDetalle : {}", id);
        Optional<CarritoHistoricoDetalleDTO> carritoHistoricoDetalleDTO = carritoHistoricoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoHistoricoDetalleDTO);
    }

    /**
     * {@code DELETE  /carrito-historico-detalles/:id} : delete the "id" carritoHistoricoDetalle.
     *
     * @param id the id of the carritoHistoricoDetalleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrito-historico-detalles/{id}")
    public ResponseEntity<Void> deleteCarritoHistoricoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete CarritoHistoricoDetalle : {}", id);
        carritoHistoricoDetalleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
