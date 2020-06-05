package mx.com.sharkit.web.admin.rest;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.service.PedidoProveedorService;
import mx.com.sharkit.service.dto.PedidoProveedorDTO;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * REST controller for managing {@link mx.com.sharkit.domain.PedidoProveedor}.
 */
@RestController
@RequestMapping("/api")
public class SeguimientoPedidoResource {

	private final Logger log = LoggerFactory.getLogger(SeguimientoPedidoResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private PedidoProveedorService pedidoProveedorService;

	/**
	 * {@code GET  /seguimiento-pedidos} : get all the pedidoProveedors.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pedidoProveedors in body.
	 */
	@GetMapping("/seguimiento-pedidos")
	public List<PedidoProveedorDTO> getAllPedidoProveedors(HttpServletRequest request) {
		log.debug("REST request to get all PedidoProveedors");
		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isAllEmpty(request.getQueryString())) {
			List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
			for (NameValuePair param : params) {
				paramsMap.put(param.getName(), param.getValue());
			}
		}
		log.debug("paramsMap: {}", paramsMap);
		return pedidoProveedorService.searchProductos(paramsMap);
	}

	/**
	 * {@code GET  /pedido-proveedores/:id} : get the "id" pedidoProveedor.
	 *
	 * @param id the id of the pedidoProveedorDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pedidoProveedorDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/seguimiento-pedidos/{id}")
	public ResponseEntity<PedidoProveedorDTO> getPedidoProveedor(@PathVariable Long id) {
		log.debug("REST request to get PedidoProveedor : {}", id);
		Optional<PedidoProveedorDTO> pedidoProveedorDTO = pedidoProveedorService.findOne(id);
		return ResponseUtil.wrapOrNotFound(pedidoProveedorDTO);
	}

}
