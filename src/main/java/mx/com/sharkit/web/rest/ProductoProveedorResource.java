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
import mx.com.sharkit.service.ProductoProveedorService;
import mx.com.sharkit.service.dto.ProductoProveedorDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.ProductoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class ProductoProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ProductoProveedorResource.class);

    private static final String ENTITY_NAME = "productoProveedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoProveedorService productoProveedorService;

    public ProductoProveedorResource(ProductoProveedorService productoProveedorService) {
        this.productoProveedorService = productoProveedorService;
    }

    /**
     * {@code POST  /productoProveedors} : Create a new productoProveedor.
     *
     * @param productoProveedorDTO the productoProveedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoProveedorDTO, or with status {@code 400 (Bad Request)} if the productoProveedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productoProveedors")
    public ResponseEntity<ProductoProveedorDTO> createProductoProveedor(@Valid @RequestBody ProductoProveedorDTO productoProveedorDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoProveedor : {}", productoProveedorDTO);
        if (productoProveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoProveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoProveedorDTO result = productoProveedorService.save(productoProveedorDTO);
        return ResponseEntity.created(new URI("/api/productoProveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productoProveedors} : Updates an existing productoProveedor.
     *
     * @param productoProveedorDTO the productoProveedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoProveedorDTO,
     * or with status {@code 400 (Bad Request)} if the productoProveedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoProveedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productoProveedors")
    public ResponseEntity<ProductoProveedorDTO> updateProductoProveedor(@Valid @RequestBody ProductoProveedorDTO productoProveedorDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoProveedor : {}", productoProveedorDTO);
        if (productoProveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoProveedorDTO result = productoProveedorService.save(productoProveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoProveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /productoProveedors} : get all the productoProveedors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoProveedors in body.
     */
    @GetMapping("/productoProveedors")
    public List<ProductoProveedorDTO> getAllProductoProveedors() {
        log.debug("REST request to get all ProductoProveedors");
        return productoProveedorService.findAllDTO();
    }

    /**
     * {@code GET  /productoProveedors/:id} : get the "id" productoProveedor.
     *
     * @param id the id of the productoProveedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoProveedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productoProveedors/{id}")
    public ResponseEntity<ProductoProveedorDTO> getProductoProveedor(@PathVariable Long id) {
        log.debug("REST request to get ProductoProveedor : {}", id);
        Optional<ProductoProveedorDTO> productoProveedorDTO = productoProveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoProveedorDTO);
    }

    /**
     * {@code DELETE  /productoProveedors/:id} : delete the "id" productoProveedor.
     *
     * @param id the id of the productoProveedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productoProveedors/{id}")
    public ResponseEntity<Void> deleteProductoProveedor(@PathVariable Long id) {
        log.debug("REST request to delete ProductoProveedor : {}", id);
        productoProveedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
