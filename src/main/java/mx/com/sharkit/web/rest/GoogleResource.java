package mx.com.sharkit.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.DistanceMatrix;

import mx.com.sharkit.service.GoogleService;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.Categoria}.
 */
@RestController
@RequestMapping("/api")
public class GoogleResource {

    private final Logger log = LoggerFactory.getLogger(GoogleResource.class);

    private static final String ENTITY_NAME = "google";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoogleService googleService;

    public GoogleResource(GoogleService googleService) {
        this.googleService = googleService;
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categoria.
     *
     * @param id the id of the categoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/google/direcciones")
    public ResponseEntity<DistanceMatrix> getCategoria(@RequestParam String origin, @RequestParam String destination) {
        log.debug("REST request to get distance between {} and {}", origin, destination);
        DistanceMatrix matrix = googleService.calculaDistancia(origin, destination);
        if (matrix != null && matrix.rows.length > 0 && matrix.rows[0].elements.length > 0 ) {
            long distanceInMeters = matrix.rows[0].elements[0].distance.inMeters;        	
            log.debug("distanceInMeters {}", distanceInMeters);
        }
        return ResponseEntity.ok().body(matrix);
    }

}
