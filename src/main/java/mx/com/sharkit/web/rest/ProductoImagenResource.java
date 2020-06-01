package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.ProductoImagenService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.ProductoImagenDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.ProductoImagen}.
 */
@RestController
@RequestMapping("/api")
public class ProductoImagenResource {

    private final Logger log = LoggerFactory.getLogger(ProductoImagenResource.class);

    private static final String ENTITY_NAME = "productoImagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoImagenService productoImagenService;

    public ProductoImagenResource(ProductoImagenService productoImagenService) {
        this.productoImagenService = productoImagenService;
    }

    /**
     * {@code POST  /producto-imagens} : Create a new productoImagen.
     *
     * @param productoImagenDTO the productoImagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoImagenDTO, or with status {@code 400 (Bad Request)} if the productoImagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-imagens")
    public ResponseEntity<ProductoImagenDTO> createProductoImagen(@RequestBody ProductoImagenDTO productoImagenDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoImagen : {}", productoImagenDTO);
        if (productoImagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoImagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoImagenDTO result = productoImagenService.save(productoImagenDTO);
        return ResponseEntity.created(new URI("/api/producto-imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-imagens} : Updates an existing productoImagen.
     *
     * @param productoImagenDTO the productoImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoImagenDTO,
     * or with status {@code 400 (Bad Request)} if the productoImagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-imagens")
    public ResponseEntity<ProductoImagenDTO> updateProductoImagen(@RequestBody ProductoImagenDTO productoImagenDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoImagen : {}", productoImagenDTO);
        if (productoImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoImagenDTO result = productoImagenService.save(productoImagenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoImagenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /producto-imagens} : get all the productoImagens.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoImagens in body.
     */
    @GetMapping("/producto-imagens")
    public List<ProductoImagenDTO> getAllProductoImagens() {
        log.debug("REST request to get all ProductoImagens");
        return productoImagenService.findAll();
    }

    /**
     * {@code GET  /producto-imagens/:id} : get the "id" productoImagen.
     *
     * @param id the id of the productoImagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoImagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-imagens/{id}")
    public ResponseEntity<ProductoImagenDTO> getProductoImagen(@PathVariable Long id) {
        log.debug("REST request to get ProductoImagen : {}", id);
        Optional<ProductoImagenDTO> productoImagenDTO = productoImagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoImagenDTO);
    }

    /**
     * {@code DELETE  /producto-imagens/:id} : delete the "id" productoImagen.
     *
     * @param id the id of the productoImagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-imagens/{id}")
    public ResponseEntity<Void> deleteProductoImagen(@PathVariable Long id) {
        log.debug("REST request to delete ProductoImagen : {}", id);
        productoImagenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /producto-imagens/producto-proveedor/{productoProveedorId}} : get all the productoImagens.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoImagens in body.
     */
    @GetMapping("/producto-imagens/producto-proveedor/{productoProveedorId}")
    public List<ProductoImagenDTO> getAllProductoImagensByProductoProveedorId(@PathVariable Long productoProveedorId) {
        log.debug("REST request to get all ProductoImagens by productoProveedorId: {}", productoProveedorId);
        return productoImagenService.findByProductoProveedorId(productoProveedorId);
    }

}
