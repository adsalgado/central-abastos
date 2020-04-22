package mx.com.sharkit.web.rest;

import mx.com.sharkit.service.HistoricoPedidoService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.HistoricoPedidoDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.HistoricoPedido}.
 */
@RestController
@RequestMapping("/api")
public class HistoricoPedidoResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoPedidoResource.class);

    private static final String ENTITY_NAME = "historicoPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoricoPedidoService historicoPedidoService;

    public HistoricoPedidoResource(HistoricoPedidoService historicoPedidoService) {
        this.historicoPedidoService = historicoPedidoService;
    }

    /**
     * {@code POST  /historico-pedidos} : Create a new historicoPedido.
     *
     * @param historicoPedidoDTO the historicoPedidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historicoPedidoDTO, or with status {@code 400 (Bad Request)} if the historicoPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historico-pedidos")
    public ResponseEntity<HistoricoPedidoDTO> createHistoricoPedido(@RequestBody HistoricoPedidoDTO historicoPedidoDTO) throws URISyntaxException {
        log.debug("REST request to save HistoricoPedido : {}", historicoPedidoDTO);
        if (historicoPedidoDTO.getId() != null) {
            throw new BadRequestAlertException("A new historicoPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricoPedidoDTO result = historicoPedidoService.save(historicoPedidoDTO);
        return ResponseEntity.created(new URI("/api/historico-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historico-pedidos} : Updates an existing historicoPedido.
     *
     * @param historicoPedidoDTO the historicoPedidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicoPedidoDTO,
     * or with status {@code 400 (Bad Request)} if the historicoPedidoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historicoPedidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historico-pedidos")
    public ResponseEntity<HistoricoPedidoDTO> updateHistoricoPedido(@RequestBody HistoricoPedidoDTO historicoPedidoDTO) throws URISyntaxException {
        log.debug("REST request to update HistoricoPedido : {}", historicoPedidoDTO);
        if (historicoPedidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoricoPedidoDTO result = historicoPedidoService.save(historicoPedidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicoPedidoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historico-pedidos} : get all the historicoPedidos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicoPedidos in body.
     */
    @GetMapping("/historico-pedidos")
    public List<HistoricoPedidoDTO> getAllHistoricoPedidos() {
        log.debug("REST request to get all HistoricoPedidos");
        return historicoPedidoService.findAll();
    }

    /**
     * {@code GET  /historico-pedidos/:id} : get the "id" historicoPedido.
     *
     * @param id the id of the historicoPedidoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historicoPedidoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historico-pedidos/{id}")
    public ResponseEntity<HistoricoPedidoDTO> getHistoricoPedido(@PathVariable Long id) {
        log.debug("REST request to get HistoricoPedido : {}", id);
        Optional<HistoricoPedidoDTO> historicoPedidoDTO = historicoPedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historicoPedidoDTO);
    }

    /**
     * {@code DELETE  /historico-pedidos/:id} : delete the "id" historicoPedido.
     *
     * @param id the id of the historicoPedidoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historico-pedidos/{id}")
    public ResponseEntity<Void> deleteHistoricoPedido(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoPedido : {}", id);
        historicoPedidoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
