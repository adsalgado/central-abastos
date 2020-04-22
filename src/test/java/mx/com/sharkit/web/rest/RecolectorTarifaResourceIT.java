package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.RecolectorTarifa;
import mx.com.sharkit.repository.RecolectorTarifaRepository;
import mx.com.sharkit.service.RecolectorTarifaService;
import mx.com.sharkit.service.dto.RecolectorTarifaDTO;
import mx.com.sharkit.service.mapper.RecolectorTarifaMapper;
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
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecolectorTarifaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class RecolectorTarifaResourceIT {

    private static final BigDecimal DEFAULT_RANGO_MINIMO = new BigDecimal(1);
    private static final BigDecimal UPDATED_RANGO_MINIMO = new BigDecimal(2);
    private static final BigDecimal SMALLER_RANGO_MINIMO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RANGO_MAXIMO = new BigDecimal(1);
    private static final BigDecimal UPDATED_RANGO_MAXIMO = new BigDecimal(2);
    private static final BigDecimal SMALLER_RANGO_MAXIMO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRECIO = new BigDecimal(1 - 1);

    @Autowired
    private RecolectorTarifaRepository recolectorTarifaRepository;

    @Autowired
    private RecolectorTarifaMapper recolectorTarifaMapper;

    @Autowired
    private RecolectorTarifaService recolectorTarifaService;

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

    private MockMvc restRecolectorTarifaMockMvc;

    private RecolectorTarifa recolectorTarifa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecolectorTarifaResource recolectorTarifaResource = new RecolectorTarifaResource(recolectorTarifaService);
        this.restRecolectorTarifaMockMvc = MockMvcBuilders.standaloneSetup(recolectorTarifaResource)
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
    public static RecolectorTarifa createEntity(EntityManager em) {
        RecolectorTarifa recolectorTarifa = new RecolectorTarifa()
            .rangoMinimo(DEFAULT_RANGO_MINIMO)
            .rangoMaximo(DEFAULT_RANGO_MAXIMO)
            .precio(DEFAULT_PRECIO);
        return recolectorTarifa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecolectorTarifa createUpdatedEntity(EntityManager em) {
        RecolectorTarifa recolectorTarifa = new RecolectorTarifa()
            .rangoMinimo(UPDATED_RANGO_MINIMO)
            .rangoMaximo(UPDATED_RANGO_MAXIMO)
            .precio(UPDATED_PRECIO);
        return recolectorTarifa;
    }

    @BeforeEach
    public void initTest() {
        recolectorTarifa = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecolectorTarifa() throws Exception {
        int databaseSizeBeforeCreate = recolectorTarifaRepository.findAll().size();

        // Create the RecolectorTarifa
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);
        restRecolectorTarifaMockMvc.perform(post("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isCreated());

        // Validate the RecolectorTarifa in the database
        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeCreate + 1);
        RecolectorTarifa testRecolectorTarifa = recolectorTarifaList.get(recolectorTarifaList.size() - 1);
        assertThat(testRecolectorTarifa.getRangoMinimo()).isEqualTo(DEFAULT_RANGO_MINIMO);
        assertThat(testRecolectorTarifa.getRangoMaximo()).isEqualTo(DEFAULT_RANGO_MAXIMO);
        assertThat(testRecolectorTarifa.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createRecolectorTarifaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recolectorTarifaRepository.findAll().size();

        // Create the RecolectorTarifa with an existing ID
        recolectorTarifa.setId(1L);
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecolectorTarifaMockMvc.perform(post("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecolectorTarifa in the database
        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRangoMinimoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recolectorTarifaRepository.findAll().size();
        // set the field null
        recolectorTarifa.setRangoMinimo(null);

        // Create the RecolectorTarifa, which fails.
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);

        restRecolectorTarifaMockMvc.perform(post("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRangoMaximoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recolectorTarifaRepository.findAll().size();
        // set the field null
        recolectorTarifa.setRangoMaximo(null);

        // Create the RecolectorTarifa, which fails.
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);

        restRecolectorTarifaMockMvc.perform(post("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = recolectorTarifaRepository.findAll().size();
        // set the field null
        recolectorTarifa.setPrecio(null);

        // Create the RecolectorTarifa, which fails.
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);

        restRecolectorTarifaMockMvc.perform(post("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecolectorTarifas() throws Exception {
        // Initialize the database
        recolectorTarifaRepository.saveAndFlush(recolectorTarifa);

        // Get all the recolectorTarifaList
        restRecolectorTarifaMockMvc.perform(get("/api/recolector-tarifas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recolectorTarifa.getId().intValue())))
            .andExpect(jsonPath("$.[*].rangoMinimo").value(hasItem(DEFAULT_RANGO_MINIMO.intValue())))
            .andExpect(jsonPath("$.[*].rangoMaximo").value(hasItem(DEFAULT_RANGO_MAXIMO.intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getRecolectorTarifa() throws Exception {
        // Initialize the database
        recolectorTarifaRepository.saveAndFlush(recolectorTarifa);

        // Get the recolectorTarifa
        restRecolectorTarifaMockMvc.perform(get("/api/recolector-tarifas/{id}", recolectorTarifa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recolectorTarifa.getId().intValue()))
            .andExpect(jsonPath("$.rangoMinimo").value(DEFAULT_RANGO_MINIMO.intValue()))
            .andExpect(jsonPath("$.rangoMaximo").value(DEFAULT_RANGO_MAXIMO.intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecolectorTarifa() throws Exception {
        // Get the recolectorTarifa
        restRecolectorTarifaMockMvc.perform(get("/api/recolector-tarifas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecolectorTarifa() throws Exception {
        // Initialize the database
        recolectorTarifaRepository.saveAndFlush(recolectorTarifa);

        int databaseSizeBeforeUpdate = recolectorTarifaRepository.findAll().size();

        // Update the recolectorTarifa
        RecolectorTarifa updatedRecolectorTarifa = recolectorTarifaRepository.findById(recolectorTarifa.getId()).get();
        // Disconnect from session so that the updates on updatedRecolectorTarifa are not directly saved in db
        em.detach(updatedRecolectorTarifa);
        updatedRecolectorTarifa
            .rangoMinimo(UPDATED_RANGO_MINIMO)
            .rangoMaximo(UPDATED_RANGO_MAXIMO)
            .precio(UPDATED_PRECIO);
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(updatedRecolectorTarifa);

        restRecolectorTarifaMockMvc.perform(put("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isOk());

        // Validate the RecolectorTarifa in the database
        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeUpdate);
        RecolectorTarifa testRecolectorTarifa = recolectorTarifaList.get(recolectorTarifaList.size() - 1);
        assertThat(testRecolectorTarifa.getRangoMinimo()).isEqualTo(UPDATED_RANGO_MINIMO);
        assertThat(testRecolectorTarifa.getRangoMaximo()).isEqualTo(UPDATED_RANGO_MAXIMO);
        assertThat(testRecolectorTarifa.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingRecolectorTarifa() throws Exception {
        int databaseSizeBeforeUpdate = recolectorTarifaRepository.findAll().size();

        // Create the RecolectorTarifa
        RecolectorTarifaDTO recolectorTarifaDTO = recolectorTarifaMapper.toDto(recolectorTarifa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecolectorTarifaMockMvc.perform(put("/api/recolector-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecolectorTarifa in the database
        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecolectorTarifa() throws Exception {
        // Initialize the database
        recolectorTarifaRepository.saveAndFlush(recolectorTarifa);

        int databaseSizeBeforeDelete = recolectorTarifaRepository.findAll().size();

        // Delete the recolectorTarifa
        restRecolectorTarifaMockMvc.perform(delete("/api/recolector-tarifas/{id}", recolectorTarifa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecolectorTarifa> recolectorTarifaList = recolectorTarifaRepository.findAll();
        assertThat(recolectorTarifaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecolectorTarifa.class);
        RecolectorTarifa recolectorTarifa1 = new RecolectorTarifa();
        recolectorTarifa1.setId(1L);
        RecolectorTarifa recolectorTarifa2 = new RecolectorTarifa();
        recolectorTarifa2.setId(recolectorTarifa1.getId());
        assertThat(recolectorTarifa1).isEqualTo(recolectorTarifa2);
        recolectorTarifa2.setId(2L);
        assertThat(recolectorTarifa1).isNotEqualTo(recolectorTarifa2);
        recolectorTarifa1.setId(null);
        assertThat(recolectorTarifa1).isNotEqualTo(recolectorTarifa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecolectorTarifaDTO.class);
        RecolectorTarifaDTO recolectorTarifaDTO1 = new RecolectorTarifaDTO();
        recolectorTarifaDTO1.setId(1L);
        RecolectorTarifaDTO recolectorTarifaDTO2 = new RecolectorTarifaDTO();
        assertThat(recolectorTarifaDTO1).isNotEqualTo(recolectorTarifaDTO2);
        recolectorTarifaDTO2.setId(recolectorTarifaDTO1.getId());
        assertThat(recolectorTarifaDTO1).isEqualTo(recolectorTarifaDTO2);
        recolectorTarifaDTO2.setId(2L);
        assertThat(recolectorTarifaDTO1).isNotEqualTo(recolectorTarifaDTO2);
        recolectorTarifaDTO1.setId(null);
        assertThat(recolectorTarifaDTO1).isNotEqualTo(recolectorTarifaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recolectorTarifaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recolectorTarifaMapper.fromId(null)).isNull();
    }
}
