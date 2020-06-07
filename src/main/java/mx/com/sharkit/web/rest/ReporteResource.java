package mx.com.sharkit.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.sharkit.service.DynScriptService;

/**
 * REST controller for managing Reports.
 */
@RestController
@RequestMapping("/api")
public class ReporteResource {

	private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private static final String ENTITY_NAME = "reportes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private DynScriptService dynScriptService;

	/**
     * {@code GET  /reportes} : get all the reportes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of records of report.
     */
    @PostMapping("/reportes")
    public List<Map<String, Object>> getReporteAbastos(@RequestBody Map<String, Object> params) {
        log.debug("REST request to get all Seccions");
        try {
			return dynScriptService.getRegistros("REPORTES_ABASTOS", "REPORTE_COSTOS", params);
		} catch (Exception e) {
			log.error("error {}", e);
		}
		return null;
    }

}
