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
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.service.PedidoProveedorHistoricoService;
import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing
 * {@link mx.com.sharkit.domain.PedidoProveedorHistorico}.
 */
@RestController
@RequestMapping("/api")
public class PedidoProveedorHistoricoResource {

	private final Logger log = LoggerFactory.getLogger(PedidoProveedorHistoricoResource.class);

	private static final String ENTITY_NAME = "pedidoProveedorHistorico";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PedidoProveedorHistoricoService pedidoProveedorHistoricoService;

	public PedidoProveedorHistoricoResource(PedidoProveedorHistoricoService pedidoProveedorHistoricoService) {
		this.pedidoProveedorHistoricoService = pedidoProveedorHistoricoService;
	}

	/**
	 * {@code POST  /historico-pedido-proveedores} : Create a new
	 * pedidoProveedorHistorico.
	 *
	 * @param pedidoProveedorHistoricoDTO the pedidoProveedorHistoricoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pedidoProveedorHistoricoDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedorHistorico has already
	 *         an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/historico-pedido-proveedores")
	public ResponseEntity<PedidoProveedorHistoricoDTO> createPedidoProveedorHistorico(
			@Valid @RequestBody PedidoProveedorHistoricoDTO pedidoProveedorHistoricoDTO) throws URISyntaxException {
		log.debug("REST request to save PedidoProveedorHistorico : {}", pedidoProveedorHistoricoDTO);
		if (pedidoProveedorHistoricoDTO.getId() != null) {
			throw new BadRequestAlertException("A new pedidoProveedorHistorico cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		PedidoProveedorHistoricoDTO result = pedidoProveedorHistoricoService.save(pedidoProveedorHistoricoDTO);
		return ResponseEntity
				.created(new URI("/api/historico-pedido-proveedores/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /historico-pedido-proveedores} : Updates an existing
	 * pedidoProveedorHistorico.
	 *
	 * @param pedidoProveedorHistoricoDTO the pedidoProveedorHistoricoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pedidoProveedorHistoricoDTO, or with status
	 *         {@code 400 (Bad Request)} if the pedidoProveedorHistoricoDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         pedidoProveedorHistoricoDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/historico-pedido-proveedores")
	public ResponseEntity<PedidoProveedorHistoricoDTO> updatePedidoProveedorHistorico(
			@Valid @RequestBody PedidoProveedorHistoricoDTO pedidoProveedorHistoricoDTO) throws URISyntaxException {
		log.debug("REST request to update PedidoProveedorHistorico : {}", pedidoProveedorHistoricoDTO);
		if (pedidoProveedorHistoricoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PedidoProveedorHistoricoDTO result = pedidoProveedorHistoricoService.save(pedidoProveedorHistoricoDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				pedidoProveedorHistoricoDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /historico-pedido-proveedores} : get all the
	 * pedidoProveedorHistoricos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidoProveedorHistoricos in body.
	 */
	@GetMapping("/historico-pedido-proveedores")
	public List<PedidoProveedorHistoricoDTO> getAllPedidoProveedorHistoricos() {
		log.debug("REST request to get all PedidoProveedorHistoricos");
		return pedidoProveedorHistoricoService.findAllDTO();
	}

	/**
	 * {@code GET  /historico-pedido-proveedores/pedido-proveedor/{pedidoProveedorId}} : get all the
	 * pedidoProveedorHistoricos.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidoProveedorHistoricos in body.
	 */
	@GetMapping("/historico-pedido-proveedores/pedido-proveedor/{pedidoProveedorId}")
	public List<PedidoProveedorHistoricoDTO> getAllHistoricosByPedidoProveedorId(@PathVariable Long pedidoProveedorId) {
		log.debug("REST request to get all PedidoProveedorHistoricos");
		List<PedidoProveedorHistoricoDTO> listHist = pedidoProveedorHistoricoService.findByPedidoProveedorIdOrderByFecha(pedidoProveedorId);
		for (PedidoProveedorHistoricoDTO histDTO : listHist) {
			
			if (histDTO.getEstatusId().equals(Estatus.PEDIDO_SOLICITADO)) {
				histDTO.setDescripcion("Cliente solicita el pedido");
			} else if (histDTO.getEstatusId().equals(Estatus.PEDIDO_CONFIRMADO)) {
				histDTO.setDescripcion("Cliente confirma el pedido");
			} else if (histDTO.getEstatusId().equals(Estatus.PEDIDO_PAGADO)) {
				histDTO.setDescripcion("Cliente paga el pedido");
			} else if (histDTO.getEstatusId().equals(Estatus.PEDIDO_PREPARADO)) {
				histDTO.setDescripcion("Proveedor prepara el pedido");
			} else if (histDTO.getEstatusId().equals(Estatus.ENVIADO_A_TRANSPORTISTA)) {
				histDTO.setDescripcion("Proveedor envía el pedido al transportista");
			} else if (histDTO.getEstatusId().equals(Estatus.PEDIDO_ENVIADO)) {
				histDTO.setDescripcion("Transportista envía el pedido");
			} else if (histDTO.getEstatusId().equals(Estatus.PEDIDO_ENTREGADO)) {
				histDTO.setDescripcion("Transportista entrega el pedido");
			}

		}
		return listHist;
	}

	/**
	 * {@code GET  /historico-pedido-proveedores/:id} : get the "id"
	 * pedidoProveedorHistorico.
	 *
	 * @param id the id of the pedidoProveedorHistoricoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pedidoProveedorHistoricoDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/historico-pedido-proveedores/{id}")
	public ResponseEntity<PedidoProveedorHistoricoDTO> getPedidoProveedorHistorico(@PathVariable Long id) {
		log.debug("REST request to get PedidoProveedorHistorico : {}", id);
		Optional<PedidoProveedorHistoricoDTO> pedidoProveedorHistoricoDTO = pedidoProveedorHistoricoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(pedidoProveedorHistoricoDTO);
	}

	/**
	 * {@code DELETE  /historico-pedido-proveedores/:id} : delete the "id"
	 * pedidoProveedorHistorico.
	 *
	 * @param id the id of the pedidoProveedorHistoricoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/historico-pedido-proveedores/{id}")
	public ResponseEntity<Void> deletePedidoProveedorHistorico(@PathVariable Long id) {
		log.debug("REST request to delete PedidoProveedorHistorico : {}", id);
		pedidoProveedorHistoricoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
