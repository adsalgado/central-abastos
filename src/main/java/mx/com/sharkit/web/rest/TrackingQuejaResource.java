package mx.com.sharkit.web.rest;

import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.TrackingQuejaService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.TrackingQuejaDTO;
import mx.com.sharkit.service.mapper.UserMapper;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.TrackingQueja}.
 */
@RestController
@RequestMapping("/api")
public class TrackingQuejaResource {

    private final Logger log = LoggerFactory.getLogger(TrackingQuejaResource.class);

    private static final String ENTITY_NAME = "trackingQueja";

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrackingQuejaService trackingQuejaService;

    public TrackingQuejaResource(TrackingQuejaService trackingQuejaService) {
        this.trackingQuejaService = trackingQuejaService;
    }

    /**
     * {@code POST  /tracking-quejas} : Create a new trackingQueja.
     *
     * @param trackingQuejaDTO the trackingQuejaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trackingQuejaDTO, or with status {@code 400 (Bad Request)} if the trackingQueja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tracking-quejas")
    public ResponseEntity<TrackingQuejaDTO> createTrackingQueja(@RequestBody TrackingQuejaDTO trackingQuejaDTO) throws URISyntaxException {
        log.debug("REST request to save TrackingQueja : {}", trackingQuejaDTO);
        if (trackingQuejaDTO.getId() != null) {
            throw new BadRequestAlertException("A new trackingQueja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<User> user = userService.getUserWithAuthorities();
		User usuario = user.isPresent() ? user.get() : null;
		if (usuario == null) {
			throw new BadRequestAlertException("Debes tener una sesión abierta para registrar el tracking de una queja", ENTITY_NAME, "idnull");
		}
		//Set the active user.
		trackingQuejaDTO.setUser(userMapper.userToUserDTO(usuario));
		trackingQuejaDTO.setUserId(usuario.getId());
        TrackingQuejaDTO result = trackingQuejaService.save(trackingQuejaDTO);
        return ResponseEntity.created(new URI("/api/tracking-quejas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tracking-quejas} : Updates an existing trackingQueja.
     *
     * @param trackingQuejaDTO the trackingQuejaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trackingQuejaDTO,
     * or with status {@code 400 (Bad Request)} if the trackingQuejaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trackingQuejaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tracking-quejas")
    public ResponseEntity<TrackingQuejaDTO> updateTrackingQueja(@RequestBody TrackingQuejaDTO trackingQuejaDTO) throws URISyntaxException {
        log.debug("REST request to update TrackingQueja : {}", trackingQuejaDTO);
        if (trackingQuejaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrackingQuejaDTO result = trackingQuejaService.save(trackingQuejaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trackingQuejaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tracking-quejas} : get all the trackingQuejas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackingQuejas in body.
     */
    @GetMapping("/tracking-quejas")
    public ResponseEntity<List<TrackingQuejaDTO>> getAllTrackingQuejas(Pageable pageable) {
        log.debug("REST request to get a page of TrackingQuejas");
        Page<TrackingQuejaDTO> page = trackingQuejaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tracking-quejas/:id} : get the "id" trackingQueja.
     *
     * @param id the id of the trackingQuejaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trackingQuejaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tracking-quejas/{id}")
    public ResponseEntity<TrackingQuejaDTO> getTrackingQueja(@PathVariable Long id) {
        log.debug("REST request to get TrackingQueja : {}", id);
        Optional<TrackingQuejaDTO> trackingQuejaDTO = trackingQuejaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackingQuejaDTO);
    }

    /**
     * {@code DELETE  /tracking-quejas/:id} : delete the "id" trackingQueja.
     *
     * @param id the id of the trackingQuejaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tracking-quejas/{id}")
    public ResponseEntity<Void> deleteTrackingQueja(@PathVariable Long id) {
        log.debug("REST request to delete TrackingQueja : {}", id);
        trackingQuejaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
