package mx.com.sharkit.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import mx.com.sharkit.domain.User;
import mx.com.sharkit.service.CarritoCompraService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.CarritoCompraDTO;
import mx.com.sharkit.service.dto.CarritoComprasCompletoDTO;
import mx.com.sharkit.service.dto.CarritoComprasProveedorDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

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

	private final UserService userService;

	public CarritoCompraResource(CarritoCompraService carritoCompraService, UserService userService) {
		this.carritoCompraService = carritoCompraService;
		this.userService = userService;
	}

	/**
	 * {@code POST  /carrito-compras} : Create a new carritoCompra.
	 *
	 * @param carritoCompraDTO the carritoCompraDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new carritoCompraDTO, or with status
	 *         {@code 400 (Bad Request)} if the carritoCompra has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/carrito-compras")
	public ResponseEntity<CarritoCompraDTO> createCarritoCompra(@Valid @RequestBody CarritoCompraDTO carritoCompraDTO)
			throws URISyntaxException {
		log.debug("REST request to save CarritoCompra : {}", carritoCompraDTO);
		if (carritoCompraDTO.getId() != null) {
			throw new BadRequestAlertException("A new carritoCompra cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		Optional<CarritoCompraDTO> optCarritoDto = carritoCompraService
				.findOneClienteIdAndProductoProveedorId(clienteId, carritoCompraDTO.getProductoProveedorId());
		if (optCarritoDto.isPresent()) {
			throw new BadRequestAlertException("Ya existe este producto en el carrito.", ENTITY_NAME, "idexists");
		}

		carritoCompraDTO.setClienteId(clienteId);
		carritoCompraDTO.setFechaAlta(LocalDateTime.now());

		CarritoCompraDTO result = carritoCompraService.save(carritoCompraDTO);
		return ResponseEntity
				.created(new URI("/api/carrito-compras/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /carrito-compras} : Updates an existing carritoCompra.
	 *
	 * @param carritoCompraDTO the carritoCompraDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated carritoCompraDTO, or with status
	 *         {@code 400 (Bad Request)} if the carritoCompraDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         carritoCompraDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/carrito-compras")
	public ResponseEntity<CarritoCompraDTO> updateCarritoCompra(@Valid @RequestBody CarritoCompraDTO carritoCompraDTO)
			throws URISyntaxException {
		log.debug("REST request to update CarritoCompra : {}", carritoCompraDTO);

		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idexists");
		}
		CarritoCompraDTO result = carritoCompraDTO;

		Optional<CarritoCompraDTO> optCarritoDto = carritoCompraService
				.findOneClienteIdAndProductoProveedorId(clienteId, carritoCompraDTO.getProductoProveedorId());
		if (optCarritoDto.isPresent()) {
			CarritoCompraDTO dto = optCarritoDto.get();
			dto.setCantidad(carritoCompraDTO.getCantidad());
			result = carritoCompraService.save(dto);
		}

		if (result.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /carrito-compras} : get all the carritoCompras by clienteId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of carritoCompras in body.
	 */
	@GetMapping("/carrito-compras")
	public List<CarritoCompraDTO> getAllCarritoComprasByClienteId() {
		log.debug("REST request to get all CarritoCompras");
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		return carritoCompraService.findAllByClienteId(clienteId);
	}

	/**
	 * {@code GET  /carrito-compras/:id} : get the "id" carritoCompra.
	 *
	 * @param id the id of the carritoCompraDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the carritoCompraDTO, or with status {@code 404 (Not Found)}.
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
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}
		carritoCompraService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /carrito-compras-proveedor} : get all the carritoCompras by
	 * clienteId.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of carritoCompras in body.
	 */
	@GetMapping("/carrito-compras-proveedor")
	public ResponseEntity<CarritoComprasCompletoDTO> getAllCarritoOrderByProveedor() {
		log.debug("REST request to get all CarritoCompras");
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

		CarritoComprasCompletoDTO carritoComprasCompletoDTO = new CarritoComprasCompletoDTO();

		Map<Long, CarritoComprasProveedorDTO> mapProveedores = new HashMap<>();

		List<CarritoCompraDTO> listCarrito = carritoCompraService.findAllByClienteId(clienteId);
		for (CarritoCompraDTO carritoCompraDTO : listCarrito) {
			Long proveedorId = carritoCompraDTO.getProductoProveedor().getProveedorId();
			if (!mapProveedores.containsKey(proveedorId)) {
				CarritoComprasProveedorDTO carritoProveedor = new CarritoComprasProveedorDTO();
				carritoProveedor.setListCarrito(new ArrayList<>());
				carritoProveedor.setProveedor(carritoCompraDTO.getProductoProveedor().getProveedor());
				carritoProveedor.setTotal(BigDecimal.ZERO);
				carritoProveedor.setTotalProductos(BigDecimal.ZERO);
				// Aqui se debe calcular la comisión del transporte
				carritoProveedor.setComisionTransporte(BigDecimal.ZERO);

				mapProveedores.put(proveedorId, carritoProveedor);

			}
			CarritoComprasProveedorDTO carritoProveedor = mapProveedores.get(proveedorId);
			carritoProveedor.getListCarrito().add(carritoCompraDTO);
			carritoProveedor.setTotalProductos(carritoProveedor.getTotalProductos()
					.add(carritoCompraDTO.getPrecio().multiply(carritoCompraDTO.getCantidad())));
		}

		BigDecimal totalProductos = BigDecimal.ZERO;
		BigDecimal totalComisionTransporte = BigDecimal.ZERO;
		List<CarritoComprasProveedorDTO> listCarritoProveedorDTO = new ArrayList<>();
		
		for (Map.Entry<Long, CarritoComprasProveedorDTO> provId : mapProveedores.entrySet()) {
			CarritoComprasProveedorDTO carritoDTO = provId.getValue();
			carritoDTO.setTotal(carritoDTO.getTotalProductos().add(carritoDTO.getComisionTransporte()));
			totalProductos = totalProductos.add(carritoDTO.getTotalProductos());
			totalComisionTransporte = totalComisionTransporte.add(carritoDTO.getComisionTransporte());
			listCarritoProveedorDTO.add(carritoDTO);
		}

		BigDecimal total = BigDecimal.ZERO;
		BigDecimal totalSinComisionStripe = BigDecimal.ZERO;
		BigDecimal comisionStripe = BigDecimal.ZERO;
		totalSinComisionStripe = totalSinComisionStripe.add(totalProductos).add(totalComisionTransporte);
		comisionStripe = totalSinComisionStripe.multiply(new BigDecimal("0.036")).add(new BigDecimal("3"));
		total = totalSinComisionStripe.add(comisionStripe);
		
		carritoComprasCompletoDTO.setTotal(total);
		carritoComprasCompletoDTO.setTotalSinComisionStripe(totalSinComisionStripe);
		carritoComprasCompletoDTO.setComisionStripe(comisionStripe);
		carritoComprasCompletoDTO.setTotalProductos(totalProductos);
		carritoComprasCompletoDTO.setTotalComisionTransporte(totalComisionTransporte);
		carritoComprasCompletoDTO.setListCarritoProveedores(listCarritoProveedorDTO);

		return ResponseEntity.ok().body(carritoComprasCompletoDTO);

	}

}
