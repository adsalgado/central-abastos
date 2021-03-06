package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Seccion;
import mx.com.sharkit.repository.SeccionRepository;
import mx.com.sharkit.service.SeccionService;
import mx.com.sharkit.service.dto.SeccionDTO;
import mx.com.sharkit.service.mapper.SeccionMapper;
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
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SeccionResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class SeccionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private SeccionMapper seccionMapper;

    @Autowired
    private SeccionService seccionService;

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

    private MockMvc restSeccionMockMvc;

    private Seccion seccion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeccionResource seccionResource = new SeccionResource(seccionService);
        this.restSeccionMockMvc = MockMvcBuilders.standaloneSetup(seccionResource)
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
    public static Seccion createEntity(EntityManager em) {
        Seccion seccion = new Seccion()
            .nombre(DEFAULT_NOMBRE);
        return seccion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seccion createUpdatedEntity(EntityManager em) {
        Seccion seccion = new Seccion()
            .nombre(UPDATED_NOMBRE);
        return seccion;
    }

    @BeforeEach
    public void initTest() {
        seccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccion() throws Exception {
        int databaseSizeBeforeCreate = seccionRepository.findAll().size();

        // Create the Seccion
        SeccionDTO seccionDTO = seccionMapper.toDto(seccion);
        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionDTO)))
            .andExpect(status().isCreated());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate + 1);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createSeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionRepository.findAll().size();

        // Create the Seccion with an existing ID
        seccion.setId(1L);
        SeccionDTO seccionDTO = seccionMapper.toDto(seccion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = seccionRepository.findAll().size();
        // set the field null
        seccion.setNombre(null);

        // Create the Seccion, which fails.
        SeccionDTO seccionDTO = seccionMapper.toDto(seccion);

        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionDTO)))
            .andExpect(status().isBadRequest());

        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeccions() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get all the seccionList
        restSeccionMockMvc.perform(get("/api/seccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", seccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seccion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeccion() throws Exception {
        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Update the seccion
        Seccion updatedSeccion = seccionRepository.findById(seccion.getId()).get();
        // Disconnect from session so that the updates on updatedSeccion are not directly saved in db
        em.detach(updatedSeccion);
        updatedSeccion
            .nombre(UPDATED_NOMBRE);
        SeccionDTO seccionDTO = seccionMapper.toDto(updatedSeccion);

        restSeccionMockMvc.perform(put("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionDTO)))
            .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Create the Seccion
        SeccionDTO seccionDTO = seccionMapper.toDto(seccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionMockMvc.perform(put("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeDelete = seccionRepository.findAll().size();

        // Delete the seccion
        restSeccionMockMvc.perform(delete("/api/seccions/{id}", seccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seccion.class);
        Seccion seccion1 = new Seccion();
        seccion1.setId(1L);
        Seccion seccion2 = new Seccion();
        seccion2.setId(seccion1.getId());
        assertThat(seccion1).isEqualTo(seccion2);
        seccion2.setId(2L);
        assertThat(seccion1).isNotEqualTo(seccion2);
        seccion1.setId(null);
        assertThat(seccion1).isNotEqualTo(seccion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionDTO.class);
        SeccionDTO seccionDTO1 = new SeccionDTO();
        seccionDTO1.setId(1L);
        SeccionDTO seccionDTO2 = new SeccionDTO();
        assertThat(seccionDTO1).isNotEqualTo(seccionDTO2);
        seccionDTO2.setId(seccionDTO1.getId());
        assertThat(seccionDTO1).isEqualTo(seccionDTO2);
        seccionDTO2.setId(2L);
        assertThat(seccionDTO1).isNotEqualTo(seccionDTO2);
        seccionDTO1.setId(null);
        assertThat(seccionDTO1).isNotEqualTo(seccionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seccionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seccionMapper.fromId(null)).isNull();
    }
}
