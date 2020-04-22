package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.CarritoCompraService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.CarritoCompraDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.CarritoCompra}.
 */
@RestController
@RequestMapping("/api")
public class CarritoCompraResource {

    private final Logger log = LoggerFactory.getLogger(CarritoCompraResource.class);

    private static final String ENTITY_NAME = "carritoCompra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoCompraService carritoCompraService;

    public CarritoCompraResource(CarritoCompraService carritoCompraService) {
        this.carritoCompraService = carritoCompraService;
    }

    /**
     * {@code POST  /carrito-compras} : Create a new carritoCompra.
     *
     * @param carritoCompraDTO the carritoCompraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoCompraDTO, or with status {@code 400 (Bad Request)} if the carritoCompra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrito-compras")
    public ResponseEntity<CarritoCompraDTO> createCarritoCompra(@Valid @RequestBody CarritoCompraDTO carritoCompraDTO) throws URISyntaxException {
        log.debug("REST request to save CarritoCompra : {}", carritoCompraDTO);
        if (carritoCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new carritoCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarritoCompraDTO result = carritoCompraService.save(carritoCompraDTO);
        return ResponseEntity.created(new URI("/api/carrito-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrito-compras} : Updates an existing carritoCompra.
     *
     * @param carritoCompraDTO the carritoCompraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoCompraDTO,
     * or with status {@code 400 (Bad Request)} if the carritoCompraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoCompraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrito-compras")
    public ResponseEntity<CarritoCompraDTO> updateCarritoCompra(@Valid @RequestBody CarritoCompraDTO carritoCompraDTO) throws URISyntaxException {
        log.debug("REST request to update CarritoCompra : {}", carritoCompraDTO);
        if (carritoCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarritoCompraDTO result = carritoCompraService.save(carritoCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrito-compras} : get all the carritoCompras.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritoCompras in body.
     */
    @GetMapping("/carrito-compras")
    public List<CarritoCompraDTO> getAllCarritoCompras() {
        log.debug("REST request to get all CarritoCompras");
        return carritoCompraService.findAll();
    }

    /**
     * {@code GET  /carrito-compras/:id} : get the "id" carritoCompra.
     *
     * @param id the id of the carritoCompraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoCompraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-compras/{id}")
    public ResponseEntity<CarritoCompraDTO> getCarritoCompra(@PathVariable Long id) {
        log.debug("REST request to get CarritoCompra : {}", id);
        Optional<CarritoCompraDTO> carritoCompraDTO = carritoCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoCompraDTO);
    }

    /**
     * {@code DELETE  /carrito-compras/:id} : delete the "id" carritoCompra.
     *
     * @param id the id of the carritoCompraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrito-compras/{id}")
    public ResponseEntity<Void> deleteCarritoCompra(@PathVariable Long id) {
        log.debug("REST request to delete CarritoCompra : {}", id);
        carritoCompraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
