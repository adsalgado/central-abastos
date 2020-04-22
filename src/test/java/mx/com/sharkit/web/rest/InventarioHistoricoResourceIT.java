package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.InventarioHistorico;
import mx.com.sharkit.repository.InventarioHistoricoRepository;
import mx.com.sharkit.service.InventarioHistoricoService;
import mx.com.sharkit.service.dto.InventarioHistoricoDTO;
import mx.com.sharkit.service.mapper.InventarioHistoricoMapper;
import mx.com.sharkit.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mx.com.sharkit.domain.enumeration.TipoMovimiento;
/**
 * Integration tests for the {@link InventarioHistoricoResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class InventarioHistoricoResourceIT {

    private static final TipoMovimiento DEFAULT_TIPO_MOVIMIENTO = TipoMovimiento.ENTRADA;
    private static final TipoMovimiento UPDATED_TIPO_MOVIMIENTO = TipoMovimiento.SALIDA;

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);
    private static final BigDecimal SMALLER_CANTIDAD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_ANTERIOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ANTERIOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_ANTERIOR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_FINAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_FINAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_FINAL = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_FECHA_MOVIMIENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MOVIMIENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_MOVIMIENTO = Instant.ofEpochMilli(-1L);

    @Autowired
    private InventarioHistoricoRepository inventarioHistoricoRepository;

    @Autowired
    private InventarioHistoricoMapper inventarioHistoricoMapper;

    @Autowired
    private InventarioHistoricoService inventarioHistoricoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restInventarioHistoricoMockMvc;

    private InventarioHistorico inventarioHistorico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarioHistoricoResource inventarioHistoricoResource = new InventarioHistoricoResource(inventarioHistoricoService);
        this.restInventarioHistoricoMockMvc = MockMvcBuilders.standaloneSetup(inventarioHistoricoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventarioHistorico createEntity(EntityManager em) {
        InventarioHistorico inventarioHistorico = new InventarioHistorico()
            .tipoMovimiento(DEFAULT_TIPO_MOVIMIENTO)
            .cantidad(DEFAULT_CANTIDAD)
            .totalAnterior(DEFAULT_TOTAL_ANTERIOR)
            .totalFinal(DEFAULT_TOTAL_FINAL)
            .fechaMovimiento(DEFAULT_FECHA_MOVIMIENTO);
        return inventarioHistorico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventarioHistorico createUpdatedEntity(EntityManager em) {
        InventarioHistorico inventarioHistorico = new InventarioHistorico()
            .tipoMovimiento(UPDATED_TIPO_MOVIMIENTO)
            .cantidad(UPDATED_CANTIDAD)
            .totalAnterior(UPDATED_TOTAL_ANTERIOR)
            .totalFinal(UPDATED_TOTAL_FINAL)
            .fechaMovimiento(UPDATED_FECHA_MOVIMIENTO);
        return inventarioHistorico;
    }

    @BeforeEach
    public void initTest() {
        inventarioHistorico = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventarioHistorico() throws Exception {
        int databaseSizeBeforeCreate = inventarioHistoricoRepository.findAll().size();

        // Create the InventarioHistorico
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);
        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isCreated());

        // Validate the InventarioHistorico in the database
        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeCreate + 1);
        InventarioHistorico testInventarioHistorico = inventarioHistoricoList.get(inventarioHistoricoList.size() - 1);
        assertThat(testInventarioHistorico.getTipoMovimiento()).isEqualTo(DEFAULT_TIPO_MOVIMIENTO);
        assertThat(testInventarioHistorico.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testInventarioHistorico.getTotalAnterior()).isEqualTo(DEFAULT_TOTAL_ANTERIOR);
        assertThat(testInventarioHistorico.getTotalFinal()).isEqualTo(DEFAULT_TOTAL_FINAL);
        assertThat(testInventarioHistorico.getFechaMovimiento()).isEqualTo(DEFAULT_FECHA_MOVIMIENTO);
    }

    @Test
    @Transactional
    public void createInventarioHistoricoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarioHistoricoRepository.findAll().size();

        // Create the InventarioHistorico with an existing ID
        inventarioHistorico.setId(1L);
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventarioHistorico in the database
        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioHistoricoRepository.findAll().size();
        // set the field null
        inventarioHistorico.setTipoMovimiento(null);

        // Create the InventarioHistorico, which fails.
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioHistoricoRepository.findAll().size();
        // set the field null
        inventarioHistorico.setCantidad(null);

        // Create the InventarioHistorico, which fails.
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioHistoricoRepository.findAll().size();
        // set the field null
        inventarioHistorico.setTotalAnterior(null);

        // Create the InventarioHistorico, which fails.
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioHistoricoRepository.findAll().size();
        // set the field null
        inventarioHistorico.setTotalFinal(null);

        // Create the InventarioHistorico, which fails.
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioHistoricoRepository.findAll().size();
        // set the field null
        inventarioHistorico.setFechaMovimiento(null);

        // Create the InventarioHistorico, which fails.
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        restInventarioHistoricoMockMvc.perform(post("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventarioHistoricos() throws Exception {
        // Initialize the database
        inventarioHistoricoRepository.saveAndFlush(inventarioHistorico);

        // Get all the inventarioHistoricoList
        restInventarioHistoricoMockMvc.perform(get("/api/inventario-historicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventarioHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMovimiento").value(hasItem(DEFAULT_TIPO_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].totalAnterior").value(hasItem(DEFAULT_TOTAL_ANTERIOR.intValue())))
            .andExpect(jsonPath("$.[*].totalFinal").value(hasItem(DEFAULT_TOTAL_FINAL.intValue())))
            .andExpect(jsonPath("$.[*].fechaMovimiento").value(hasItem(DEFAULT_FECHA_MOVIMIENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getInventarioHistorico() throws Exception {
        // Initialize the database
        inventarioHistoricoRepository.saveAndFlush(inventarioHistorico);

        // Get the inventarioHistorico
        restInventarioHistoricoMockMvc.perform(get("/api/inventario-historicos/{id}", inventarioHistorico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventarioHistorico.getId().intValue()))
            .andExpect(jsonPath("$.tipoMovimiento").value(DEFAULT_TIPO_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.totalAnterior").value(DEFAULT_TOTAL_ANTERIOR.intValue()))
            .andExpect(jsonPath("$.totalFinal").value(DEFAULT_TOTAL_FINAL.intValue()))
            .andExpect(jsonPath("$.fechaMovimiento").value(DEFAULT_FECHA_MOVIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventarioHistorico() throws Exception {
        // Get the inventarioHistorico
        restInventarioHistoricoMockMvc.perform(get("/api/inventario-historicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventarioHistorico() throws Exception {
        // Initialize the database
        inventarioHistoricoRepository.saveAndFlush(inventarioHistorico);

        int databaseSizeBeforeUpdate = inventarioHistoricoRepository.findAll().size();

        // Update the inventarioHistorico
        InventarioHistorico updatedInventarioHistorico = inventarioHistoricoRepository.findById(inventarioHistorico.getId()).get();
        // Disconnect from session so that the updates on updatedInventarioHistorico are not directly saved in db
        em.detach(updatedInventarioHistorico);
        updatedInventarioHistorico
            .tipoMovimiento(UPDATED_TIPO_MOVIMIENTO)
            .cantidad(UPDATED_CANTIDAD)
            .totalAnterior(UPDATED_TOTAL_ANTERIOR)
            .totalFinal(UPDATED_TOTAL_FINAL)
            .fechaMovimiento(UPDATED_FECHA_MOVIMIENTO);
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(updatedInventarioHistorico);

        restInventarioHistoricoMockMvc.perform(put("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isOk());

        // Validate the InventarioHistorico in the database
        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeUpdate);
        InventarioHistorico testInventarioHistorico = inventarioHistoricoList.get(inventarioHistoricoList.size() - 1);
        assertThat(testInventarioHistorico.getTipoMovimiento()).isEqualTo(UPDATED_TIPO_MOVIMIENTO);
        assertThat(testInventarioHistorico.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testInventarioHistorico.getTotalAnterior()).isEqualTo(UPDATED_TOTAL_ANTERIOR);
        assertThat(testInventarioHistorico.getTotalFinal()).isEqualTo(UPDATED_TOTAL_FINAL);
        assertThat(testInventarioHistorico.getFechaMovimiento()).isEqualTo(UPDATED_FECHA_MOVIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingInventarioHistorico() throws Exception {
        int databaseSizeBeforeUpdate = inventarioHistoricoRepository.findAll().size();

        // Create the InventarioHistorico
        InventarioHistoricoDTO inventarioHistoricoDTO = inventarioHistoricoMapper.toDto(inventarioHistorico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventarioHistoricoMockMvc.perform(put("/api/inventario-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioHistoricoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventarioHistorico in the database
        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventarioHistorico() throws Exception {
        // Initialize the database
        inventarioHistoricoRepository.saveAndFlush(inventarioHistorico);

        int databaseSizeBeforeDelete = inventarioHistoricoRepository.findAll().size();

        // Delete the inventarioHistorico
        restInventarioHistoricoMockMvc.perform(delete("/api/inventario-historicos/{id}", inventarioHistorico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventarioHistorico> inventarioHistoricoList = inventarioHistoricoRepository.findAll();
        assertThat(inventarioHistoricoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventarioHistorico.class);
        InventarioHistorico inventarioHistorico1 = new InventarioHistorico();
        inventarioHistorico1.setId(1L);
        InventarioHistorico inventarioHistorico2 = new InventarioHistorico();
        inventarioHistorico2.setId(inventarioHistorico1.getId());
        assertThat(inventarioHistorico1).isEqualTo(inventarioHistorico2);
        inventarioHistorico2.setId(2L);
        assertThat(inventarioHistorico1).isNotEqualTo(inventarioHistorico2);
        inventarioHistorico1.setId(null);
        assertThat(inventarioHistorico1).isNotEqualTo(inventarioHistorico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventarioHistoricoDTO.class);
        InventarioHistoricoDTO inventarioHistoricoDTO1 = new InventarioHistoricoDTO();
        inventarioHistoricoDTO1.setId(1L);
        InventarioHistoricoDTO inventarioHistoricoDTO2 = new InventarioHistoricoDTO();
        assertThat(inventarioHistoricoDTO1).isNotEqualTo(inventarioHistoricoDTO2);
        inventarioHistoricoDTO2.setId(inventarioHistoricoDTO1.getId());
        assertThat(inventarioHistoricoDTO1).isEqualTo(inventarioHistoricoDTO2);
        inventarioHistoricoDTO2.setId(2L);
        assertThat(inventarioHistoricoDTO1).isNotEqualTo(inventarioHistoricoDTO2);
        inventarioHistoricoDTO1.setId(null);
        assertThat(inventarioHistoricoDTO1).isNotEqualTo(inventarioHistoricoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventarioHistoricoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inventarioHistoricoMapper.fromId(null)).isNull();
    }
}
