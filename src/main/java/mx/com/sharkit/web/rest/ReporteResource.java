package mx.com.sharkit.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.sharkit.service.DynScriptService;
import mx.com.sharkit.service.dto.SubastasResponseDTO;

/**
 * REST controller for managing Reports.
 */
@RestController
@RequestMapping("/api")
public class ReporteResource {

	private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private static final String ENTITY_NAME = "reportes";
    
    private static final String CLAVE_REPORTES_ABASTOS = "REPORTES_ABASTOS";

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
    public ResponseEntity<SubastasResponseDTO> getReporteAbastos(@RequestBody Map<String, Object> params) {
        log.debug("REST request to get Report... params: {}", params);
        
        SubastasResponseDTO subastasResponseDTO = new SubastasResponseDTO();
        
        log.debug("params.containsKey(\"claveReporte\"): {}", params.containsKey("claveReporte"));
        log.debug("params.get(\"claveReporte\"): {}", params.get("claveReporte"));
        if (params == null || !params.containsKey("claveReporte")) {
        	subastasResponseDTO.setError(true);
        	subastasResponseDTO.setMessageError("No se especificó la clave del reporte");
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(subastasResponseDTO);
        }
        
        try {
        	String claveReporte = (String) params.get("claveReporte");
        	subastasResponseDTO.setData(dynScriptService.getRegistros(CLAVE_REPORTES_ABASTOS, claveReporte , params));
        	
		} catch (Exception e) {
			log.error("error {}", e);
			subastasResponseDTO.setError(true);
        	subastasResponseDTO.setMessageError("Ocurrió un error al generar el reporte.");
        	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(subastasResponseDTO);
		}
		return ResponseEntity.ok().body(subastasResponseDTO);
    }

}
