package mx.com.sharkit.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import mx.com.sharkit.service.CarritoHistoricoDetalleService;
import mx.com.sharkit.service.CarritoHistoricoService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.CarritoCompraDTO;
import mx.com.sharkit.service.dto.CarritoHistoricoCompletoDTO;
import mx.com.sharkit.service.dto.CarritoHistoricoDTO;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;
import mx.com.sharkit.service.dto.CarritoHistoricoProveedorDTO;
import mx.com.sharkit.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.CarritoHistorico}.
 */
@RestController
@RequestMapping("/api")
public class CarritoHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(CarritoHistoricoResource.class);

    private static final String ENTITY_NAME = "carritoHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoHistoricoService carritoHistoricoService;
    
	private final CarritoHistoricoDetalleService carritoHistoricoDetalleService;
    
    private final CarritoCompraService carritoCompraService;

	private final UserService userService;

    public CarritoHistoricoResource(CarritoHistoricoService carritoHistoricoService, CarritoCompraService carritoCompraService, CarritoHistoricoDetalleService carritoHistoricoDetalleService, UserService userService) {
        this.carritoHistoricoService = carritoHistoricoService;
        this.carritoCompraService = carritoCompraService;
        this.carritoHistoricoDetalleService = carritoHistoricoDetalleService;
        this.userService = userService;
    }

    /**
     * {@code POST  /carrito-historicos} : Create a new carritoHistorico.
     *
     * @param carritoHistoricoDTO the carritoHistoricoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoHistoricoDTO, or with status {@code 400 (Bad Request)} if the carritoHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrito-historicos")
    public ResponseEntity<CarritoHistoricoDTO> createCarritoHistorico(@Valid @RequestBody CarritoHistoricoDTO carritoHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to save CarritoHistorico : {}", carritoHistoricoDTO);
        if (carritoHistoricoDTO.getId() != null) {
            throw new BadRequestAlertException("A new carritoHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<User> user = userService.getUserWithAuthorities();
        Long clienteId =  user.isPresent() ? user.get().getId() : 0L;
        if (clienteId == 0) {
            throw new BadRequestAlertException("El cliente es requerido.", ENTITY_NAME, "idexists");
        }
        
        List<CarritoCompraDTO> lstCarrito = carritoCompraService.findAllByClienteId(clienteId);
        if (lstCarrito.isEmpty()) {
            throw new BadRequestAlertException("No existen productos en el carrito.", ENTITY_NAME, "idexists");
        }

        carritoHistoricoDTO.setFechaAlta(LocalDateTime.now());
        carritoHistoricoDTO.setClienteId(clienteId);
        CarritoHistoricoDTO result = carritoHistoricoService.save(carritoHistoricoDTO);
        
        List<CarritoHistoricoDetalleDTO> lstHist = lstCarrito.stream().map(ch -> {
        	CarritoHistoricoDetalleDTO dto = new CarritoHistoricoDetalleDTO();
        	dto.setCantidad(ch.getCantidad());
        	dto.setPrecio(ch.getPrecio());
        	dto.setProductoProveedorId(ch.getProductoProveedorId());
        	dto.setCarritoHistoricoId(result.getId());
        		return dto;
        }).collect(Collectors.toList());
        
        carritoHistoricoDetalleService.saveAll(lstHist);
        
        // Borrar el carrito
//        carritoCompraService.deleteByClienteId(clienteId);
        
        return ResponseEntity.created(new URI("/api/carrito-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrito-historicos} : Updates an existing carritoHistorico.
     *
     * @param carritoHistoricoDTO the carritoHistoricoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoHistoricoDTO,
     * or with status {@code 400 (Bad Request)} if the carritoHistoricoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoHistoricoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrito-historicos")
    public ResponseEntity<CarritoHistoricoDTO> updateCarritoHistorico(@Valid @RequestBody CarritoHistoricoDTO carritoHistoricoDTO) throws URISyntaxException {
        log.debug("REST request to update CarritoHistorico : {}", carritoHistoricoDTO);
        if (carritoHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<User> user = userService.getUserWithAuthorities();
        Long clienteId =  user.isPresent() ? user.get().getId() : 0L;
        if (clienteId == 0) {
            throw new BadRequestAlertException("El cliente es requerido.", ENTITY_NAME, "idexists");
        }
        Optional<CarritoHistoricoDTO> carritoOpt = carritoHistoricoService.findOne(carritoHistoricoDTO.getId());
        CarritoHistoricoDTO carrito = null;
        CarritoHistoricoDTO result = null;
        if (carritoOpt.isPresent()) {
        	carrito = carritoOpt.get();
        	carrito.setNombre(carritoHistoricoDTO.getNombre());
            result = carritoHistoricoService.save(carritoHistoricoDTO);
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoHistoricoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrito-historicos} : get all the carritoHistoricos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritoHistoricos in body.
     */
    @GetMapping("/carrito-historicos")
    public List<CarritoHistoricoDTO> getAllCarritoHistoricos() {
        log.debug("REST request to get all CarritoHistoricos");
        
        Optional<User> user = userService.getUserWithAuthorities();
        Long clienteId =  user.isPresent() ? user.get().getId() : 0L;
        if (clienteId == 0) {
            throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idexists");
        }
        
        List<CarritoHistoricoDTO> carritosHistorico = new ArrayList<>();
        carritoHistoricoService.findByClienteId(clienteId).forEach(ch -> {
        	ch.setCarritoHistoricoDetalles(carritoHistoricoDetalleService.findByCarritoHistoricoId(ch.getId()));
        	carritosHistorico.add(ch);
        });
        return carritosHistorico;
    }

    /**
     * {@code GET  /carrito-historicos/:id} : get the "id" carritoHistorico.
     *
     * @param id the id of the carritoHistoricoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoHistoricoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-historicos/{id}")
    public ResponseEntity<CarritoHistoricoDTO> getCarritoHistorico(@PathVariable Long id) {
        log.debug("REST request to get CarritoHistorico : {}", id);
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

        Optional<CarritoHistoricoDTO> carritoHistoricoDTO = carritoHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoHistoricoDTO);
    }
        
    /**
     * {@code GET  /carrito-historicos-proveedores/:id} : get the "id" carritoHistorico.
     *
     * @param id the id of the carritoHistoricoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoHistoricoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-historicos-proveedores/{id}")
    public ResponseEntity<CarritoHistoricoCompletoDTO> getCarritoHistoricoProveedores(@PathVariable Long id) {
        log.debug("REST request to get CarritoHistorico : {}", id);
		Optional<User> user = userService.getUserWithAuthorities();
		Long clienteId = user.isPresent() ? user.get().getId() : 0L;
		if (clienteId == 0) {
			throw new BadRequestAlertException("El cliente es requerido", ENTITY_NAME, "idnull");
		}

        CarritoHistoricoDTO carritoHistoricoDTO = carritoHistoricoService.findOne(id).orElse(null);
		CarritoHistoricoCompletoDTO carritoHistoricoCompletoDTO = new CarritoHistoricoCompletoDTO();
        
        if (carritoHistoricoDTO != null) {
        	carritoHistoricoCompletoDTO.setId(carritoHistoricoDTO.getId());
    		carritoHistoricoCompletoDTO.setNombre(carritoHistoricoDTO.getNombre());
    		carritoHistoricoCompletoDTO.setClienteId(clienteId);

    		Map<Long, CarritoHistoricoProveedorDTO> mapProveedores = new HashMap<>();
    
    		List<CarritoHistoricoDetalleDTO> listCarrito = carritoHistoricoDetalleService.findByCarritoHistoricoId(carritoHistoricoDTO.getId());
    		for (CarritoHistoricoDetalleDTO carritoCompraDTO : listCarrito) {
    			Long proveedorId = carritoCompraDTO.getProductoProveedor().getProveedorId();
    			if (!mapProveedores.containsKey(proveedorId)) {
    				CarritoHistoricoProveedorDTO carritoProveedor = new CarritoHistoricoProveedorDTO();
    				carritoProveedor.setListCarrito(new ArrayList<>());
    				carritoProveedor.setProveedor(carritoCompraDTO.getProductoProveedor().getProveedor());
    				carritoProveedor.setTotal(BigDecimal.ZERO);
    				carritoProveedor.setTotalProductos(BigDecimal.ZERO);
    				// Aqui se debe calcular la comisión del transporte
    				carritoProveedor.setComisionTransporte(BigDecimal.ZERO);
    
    				mapProveedores.put(proveedorId, carritoProveedor);
    
    			}
    			CarritoHistoricoProveedorDTO carritoProveedor = mapProveedores.get(proveedorId);
    			carritoProveedor.getListCarrito().add(carritoCompraDTO);
    			carritoProveedor.setTotalProductos(carritoProveedor.getTotalProductos()
    					.add(carritoCompraDTO.getPrecio().multiply(carritoCompraDTO.getCantidad())));
    		}
    
    		BigDecimal totalProductos = BigDecimal.ZERO;
    		BigDecimal totalComisionTransporte = BigDecimal.ZERO;
    		List<CarritoHistoricoProveedorDTO> listCarritoProveedorDTO = new ArrayList<>();
    		
    		for (Map.Entry<Long, CarritoHistoricoProveedorDTO> provId : mapProveedores.entrySet()) {
    			CarritoHistoricoProveedorDTO carritoDTO = provId.getValue();
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
    		
    		carritoHistoricoCompletoDTO.setTotal(total);
    		carritoHistoricoCompletoDTO.setTotalSinComisionStripe(totalSinComisionStripe);
    		carritoHistoricoCompletoDTO.setComisionStripe(comisionStripe);
    		carritoHistoricoCompletoDTO.setTotalProductos(totalProductos);
    		carritoHistoricoCompletoDTO.setTotalComisionTransporte(totalComisionTransporte);
    		carritoHistoricoCompletoDTO.setListHistoricoProveedores(listCarritoProveedorDTO);
        	
        }

		return ResponseEntity.ok().body(carritoHistoricoCompletoDTO);

    }

    /**
     * {@code DELETE  /carrito-historicos/:id} : delete the "id" carritoHistorico.
     *
     * @param id the id of the carritoHistoricoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrito-historicos/{id}")
    public ResponseEntity<Void> deleteCarritoHistorico(@PathVariable Long id) {
        log.debug("REST request to delete CarritoHistorico : {}", id);
        
        carritoHistoricoDetalleService.findByCarritoHistoricoId(id).forEach(chd -> 
        	carritoHistoricoDetalleService.delete(chd.getId())
        );
        
        carritoHistoricoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
