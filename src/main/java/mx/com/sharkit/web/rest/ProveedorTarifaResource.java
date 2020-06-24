package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.ProveedorTarifaService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.ProveedorTarifaDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.ProveedorTarifa}.
 */
@RestController
@RequestMapping("/api")
public class ProveedorTarifaResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorTarifaResource.class);

    private static final String ENTITY_NAME = "proveedorTarifa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProveedorTarifaService proveedorTarifaService;

    public ProveedorTarifaResource(ProveedorTarifaService proveedorTarifaService) {
        this.proveedorTarifaService = proveedorTarifaService;
    }

    /**
     * {@code POST  /proveedor-tarifas} : Create a new proveedorTarifa.
     *
     * @param proveedorTarifaDTO the proveedorTarifaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proveedorTarifaDTO, or with status {@code 400 (Bad Request)} if the proveedorTarifa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proveedor-tarifas")
    public ResponseEntity<ProveedorTarifaDTO> createProveedorTarifa(@Valid @RequestBody ProveedorTarifaDTO proveedorTarifaDTO) throws URISyntaxException {
        log.debug("REST request to save ProveedorTarifa : {}", proveedorTarifaDTO);
        if (proveedorTarifaDTO.getId() != null) {
            throw new BadRequestAlertException("A new proveedorTarifa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProveedorTarifaDTO result = proveedorTarifaService.save(proveedorTarifaDTO);
        return ResponseEntity.created(new URI("/api/proveedor-tarifas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proveedor-tarifas} : Updates an existing proveedorTarifa.
     *
     * @param proveedorTarifaDTO the proveedorTarifaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proveedorTarifaDTO,
     * or with status {@code 400 (Bad Request)} if the proveedorTarifaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proveedorTarifaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proveedor-tarifas")
    public ResponseEntity<ProveedorTarifaDTO> updateProveedorTarifa(@Valid @RequestBody ProveedorTarifaDTO proveedorTarifaDTO) throws URISyntaxException {
        log.debug("REST request to update ProveedorTarifa : {}", proveedorTarifaDTO);
        if (proveedorTarifaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProveedorTarifaDTO result = proveedorTarifaService.save(proveedorTarifaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proveedorTarifaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proveedor-tarifas} : get all the proveedorTarifas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proveedorTarifas in body.
     */
    @GetMapping("/proveedor-tarifas")
    public List<ProveedorTarifaDTO> getAllProveedorTarifas() {
        log.debug("REST request to get all ProveedorTarifas");
        return proveedorTarifaService.findAllOrderByRangoMinimo();
    }

    /**
     * {@code GET  /proveedor-tarifas/:id} : get the "id" proveedorTarifa.
     *
     * @param id the id of the proveedorTarifaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proveedorTarifaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proveedor-tarifas/{id}")
    public ResponseEntity<ProveedorTarifaDTO> getProveedorTarifa(@PathVariable Long id) {
        log.debug("REST request to get ProveedorTarifa : {}", id);
        Optional<ProveedorTarifaDTO> proveedorTarifaDTO = proveedorTarifaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proveedorTarifaDTO);
    }

    /**
     * {@code DELETE  /proveedor-tarifas/:id} : delete the "id" proveedorTarifa.
     *
     * @param id the id of the proveedorTarifaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proveedor-tarifas/{id}")
    public ResponseEntity<Void> deleteProveedorTarifa(@PathVariable Long id) {
        log.debug("REST request to delete ProveedorTarifa : {}", id);
        proveedorTarifaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
