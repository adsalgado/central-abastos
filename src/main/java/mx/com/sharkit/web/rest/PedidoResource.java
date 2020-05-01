package mx.com.sharkit.web.rest;

import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;
import mx.com.sharkit.service.dto.PedidoAltaDTO;
import mx.com.sharkit.service.dto.PedidoDTO;

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
 * REST controller for managing {@link mx.com.sharkit.domain.Pedido}.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private static final String ENTITY_NAME = "pedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidoService pedidoService;
    
    private final UserService userService;

    public PedidoResource(PedidoService pedidoService, UserService userService) {
        this.pedidoService = pedidoService;
        this.userService = userService;
    }

    /**
     * {@code POST  /pedidos} : Create a new pedido.
     *
     * @param pedidoDTO the pedidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedidoDTO, or with status {@code 400 (Bad Request)} if the pedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PostMapping("/pedidos")
//    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
//        log.debug("REST request to save Pedido : {}", pedidoDTO);
//        if (pedidoDTO.getId() != null) {
//            throw new BadRequestAlertException("A new pedido cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        PedidoDTO result = pedidoService.save(pedidoDTO);
//        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code POST  /pedidos} : Create a new pedido.
     *
     * @param pedidoDTO the pedidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedidoDTO, or with status {@code 400 (Bad Request)} if the pedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pedidos")
    public ResponseEntity<PedidoDTO> createPedidoNuevo(@RequestBody PedidoAltaDTO pedidoAltaDTO) throws URISyntaxException, Exception {
        log.debug("REST request to save pedidoAltaDTO : {}", pedidoAltaDTO);
        if (pedidoAltaDTO.getProductos() == null || pedidoAltaDTO.getProductos().isEmpty()) {
            throw new BadRequestAlertException("Lista de productos vacía", ENTITY_NAME, "arraynull");
        }
        
        Optional<User> user = userService.getUserWithAuthorities();
        Long clienteId =  user.isPresent() ? user.get().getId() : 0L;
        if (clienteId == 0) {
            throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
        }
        pedidoAltaDTO.setUsuarioId(clienteId);
        PedidoDTO result = pedidoService.generaNuevoPedido(pedidoAltaDTO);
        
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pedidos} : Updates an existing pedido.
     *
     * @param pedidoDTO the pedidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedidoDTO,
     * or with status {@code 400 (Bad Request)} if the pedidoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pedidos")
    public ResponseEntity<PedidoDTO> updatePedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedidoDTO);
        if (pedidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PedidoDTO result = pedidoService.save(pedidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pedidos} : get all the pedidos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidos in body.
     */
    @GetMapping("/pedidos")
    public List<PedidoDTO> getAllPedidos() {
        log.debug("REST request to get all Pedidos");
        return pedidoService.findAll();
    }

    /**
     * {@code GET  /pedidos/:id} : get the "id" pedido.
     *
     * @param id the id of the pedidoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedidoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Optional<PedidoDTO> pedidoDTO = pedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pedidoDTO);
    }

    /**
     * {@code DELETE  /pedidos/:id} : delete the "id" pedido.
     *
     * @param id the id of the pedidoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.debug("REST request to delete Pedido : {}", id);
        pedidoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
