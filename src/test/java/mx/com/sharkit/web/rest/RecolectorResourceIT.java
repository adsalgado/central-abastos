package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Recolector;
import mx.com.sharkit.repository.RecolectorRepository;
import mx.com.sharkit.service.RecolectorService;
import mx.com.sharkit.service.dto.RecolectorDTO;
import mx.com.sharkit.service.mapper.RecolectorMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecolectorResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class RecolectorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_ALTA = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_MODIFICACION = Instant.ofEpochMilli(-1L);

    @Autowired
    private RecolectorRepository recolectorRepository;

    @Autowired
    private RecolectorMapper recolectorMapper;

    @Autowired
    private RecolectorService recolectorService;

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

    private MockMvc restRecolectorMockMvc;

    private Recolector recolector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecolectorResource recolectorResource = new RecolectorResource(recolectorService);
        this.restRecolectorMockMvc = MockMvcBuilders.standaloneSetup(recolectorResource)
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
    public static Recolector createEntity(EntityManager em) {
        Recolector recolector = new Recolector()
            .nombre(DEFAULT_NOMBRE)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return recolector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recolector createUpdatedEntity(EntityManager em) {
        Recolector recolector = new Recolector()
            .nombre(UPDATED_NOMBRE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        return recolector;
    }

    @BeforeEach
    public void initTest() {
        recolector = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecolector() throws Exception {
        int databaseSizeBeforeCreate = recolectorRepository.findAll().size();

        // Create the Recolector
        RecolectorDTO recolectorDTO = recolectorMapper.toDto(recolector);
        restRecolectorMockMvc.perform(post("/api/recolectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Recolector in the database
        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeCreate + 1);
        Recolector testRecolector = recolectorList.get(recolectorList.size() - 1);
        assertThat(testRecolector.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRecolector.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testRecolector.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void createRecolectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recolectorRepository.findAll().size();

        // Create the Recolector with an existing ID
        recolector.setId(1L);
        RecolectorDTO recolectorDTO = recolectorMapper.toDto(recolector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecolectorMockMvc.perform(post("/api/recolectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recolector in the database
        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = recolectorRepository.findAll().size();
        // set the field null
        recolector.setNombre(null);

        // Create the Recolector, which fails.
        RecolectorDTO recolectorDTO = recolectorMapper.toDto(recolector);

        restRecolectorMockMvc.perform(post("/api/recolectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorDTO)))
            .andExpect(status().isBadRequest());

        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecolectors() throws Exception {
        // Initialize the database
        recolectorRepository.saveAndFlush(recolector);

        // Get all the recolectorList
        restRecolectorMockMvc.perform(get("/api/recolectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recolector.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getRecolector() throws Exception {
        // Initialize the database
        recolectorRepository.saveAndFlush(recolector);

        // Get the recolector
        restRecolectorMockMvc.perform(get("/api/recolectors/{id}", recolector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recolector.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecolector() throws Exception {
        // Get the recolector
        restRecolectorMockMvc.perform(get("/api/recolectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecolector() throws Exception {
        // Initialize the database
        recolectorRepository.saveAndFlush(recolector);

        int databaseSizeBeforeUpdate = recolectorRepository.findAll().size();

        // Update the recolector
        Recolector updatedRecolector = recolectorRepository.findById(recolector.getId()).get();
        // Disconnect from session so that the updates on updatedRecolector are not directly saved in db
        em.detach(updatedRecolector);
        updatedRecolector
            .nombre(UPDATED_NOMBRE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        RecolectorDTO recolectorDTO = recolectorMapper.toDto(updatedRecolector);

        restRecolectorMockMvc.perform(put("/api/recolectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorDTO)))
            .andExpect(status().isOk());

        // Validate the Recolector in the database
        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeUpdate);
        Recolector testRecolector = recolectorList.get(recolectorList.size() - 1);
        assertThat(testRecolector.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecolector.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testRecolector.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingRecolector() throws Exception {
        int databaseSizeBeforeUpdate = recolectorRepository.findAll().size();

        // Create the Recolector
        RecolectorDTO recolectorDTO = recolectorMapper.toDto(recolector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecolectorMockMvc.perform(put("/api/recolectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recolectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recolector in the database
        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecolector() throws Exception {
        // Initialize the database
        recolectorRepository.saveAndFlush(recolector);

        int databaseSizeBeforeDelete = recolectorRepository.findAll().size();

        // Delete the recolector
        restRecolectorMockMvc.perform(delete("/api/recolectors/{id}", recolector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recolector> recolectorList = recolectorRepository.findAll();
        assertThat(recolectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recolector.class);
        Recolector recolector1 = new Recolector();
        recolector1.setId(1L);
        Recolector recolector2 = new Recolector();
        recolector2.setId(recolector1.getId());
        assertThat(recolector1).isEqualTo(recolector2);
        recolector2.setId(2L);
        assertThat(recolector1).isNotEqualTo(recolector2);
        recolector1.setId(null);
        assertThat(recolector1).isNotEqualTo(recolector2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecolectorDTO.class);
        RecolectorDTO recolectorDTO1 = new RecolectorDTO();
        recolectorDTO1.setId(1L);
        RecolectorDTO recolectorDTO2 = new RecolectorDTO();
        assertThat(recolectorDTO1).isNotEqualTo(recolectorDTO2);
        recolectorDTO2.setId(recolectorDTO1.getId());
        assertThat(recolectorDTO1).isEqualTo(recolectorDTO2);
        recolectorDTO2.setId(2L);
        assertThat(recolectorDTO1).isNotEqualTo(recolectorDTO2);
        recolectorDTO1.setId(null);
        assertThat(recolectorDTO1).isNotEqualTo(recolectorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recolectorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recolectorMapper.fromId(null)).isNull();
    }
}
