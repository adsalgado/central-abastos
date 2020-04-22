package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.TipoArticulo;
import mx.com.sharkit.repository.TipoArticuloRepository;
import mx.com.sharkit.service.TipoArticuloService;
import mx.com.sharkit.service.dto.TipoArticuloDTO;
import mx.com.sharkit.service.mapper.TipoArticuloMapper;
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
 * Integration tests for the {@link TipoArticuloResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class TipoArticuloResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoArticuloRepository tipoArticuloRepository;

    @Autowired
    private TipoArticuloMapper tipoArticuloMapper;

    @Autowired
    private TipoArticuloService tipoArticuloService;

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

    private MockMvc restTipoArticuloMockMvc;

    private TipoArticulo tipoArticulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoArticuloResource tipoArticuloResource = new TipoArticuloResource(tipoArticuloService);
        this.restTipoArticuloMockMvc = MockMvcBuilders.standaloneSetup(tipoArticuloResource)
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
    public static TipoArticulo createEntity(EntityManager em) {
        TipoArticulo tipoArticulo = new TipoArticulo()
            .nombre(DEFAULT_NOMBRE);
        return tipoArticulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoArticulo createUpdatedEntity(EntityManager em) {
        TipoArticulo tipoArticulo = new TipoArticulo()
            .nombre(UPDATED_NOMBRE);
        return tipoArticulo;
    }

    @BeforeEach
    public void initTest() {
        tipoArticulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoArticulo() throws Exception {
        int databaseSizeBeforeCreate = tipoArticuloRepository.findAll().size();

        // Create the TipoArticulo
        TipoArticuloDTO tipoArticuloDTO = tipoArticuloMapper.toDto(tipoArticulo);
        restTipoArticuloMockMvc.perform(post("/api/tipo-articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoArticuloDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoArticulo in the database
        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeCreate + 1);
        TipoArticulo testTipoArticulo = tipoArticuloList.get(tipoArticuloList.size() - 1);
        assertThat(testTipoArticulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoArticuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoArticuloRepository.findAll().size();

        // Create the TipoArticulo with an existing ID
        tipoArticulo.setId(1L);
        TipoArticuloDTO tipoArticuloDTO = tipoArticuloMapper.toDto(tipoArticulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoArticuloMockMvc.perform(post("/api/tipo-articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoArticuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoArticulo in the database
        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoArticuloRepository.findAll().size();
        // set the field null
        tipoArticulo.setNombre(null);

        // Create the TipoArticulo, which fails.
        TipoArticuloDTO tipoArticuloDTO = tipoArticuloMapper.toDto(tipoArticulo);

        restTipoArticuloMockMvc.perform(post("/api/tipo-articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoArticuloDTO)))
            .andExpect(status().isBadRequest());

        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoArticulos() throws Exception {
        // Initialize the database
        tipoArticuloRepository.saveAndFlush(tipoArticulo);

        // Get all the tipoArticuloList
        restTipoArticuloMockMvc.perform(get("/api/tipo-articulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoArticulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoArticulo() throws Exception {
        // Initialize the database
        tipoArticuloRepository.saveAndFlush(tipoArticulo);

        // Get the tipoArticulo
        restTipoArticuloMockMvc.perform(get("/api/tipo-articulos/{id}", tipoArticulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoArticulo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoArticulo() throws Exception {
        // Get the tipoArticulo
        restTipoArticuloMockMvc.perform(get("/api/tipo-articulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoArticulo() throws Exception {
        // Initialize the database
        tipoArticuloRepository.saveAndFlush(tipoArticulo);

        int databaseSizeBeforeUpdate = tipoArticuloRepository.findAll().size();

        // Update the tipoArticulo
        TipoArticulo updatedTipoArticulo = tipoArticuloRepository.findById(tipoArticulo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoArticulo are not directly saved in db
        em.detach(updatedTipoArticulo);
        updatedTipoArticulo
            .nombre(UPDATED_NOMBRE);
        TipoArticuloDTO tipoArticuloDTO = tipoArticuloMapper.toDto(updatedTipoArticulo);

        restTipoArticuloMockMvc.perform(put("/api/tipo-articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoArticuloDTO)))
            .andExpect(status().isOk());

        // Validate the TipoArticulo in the database
        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeUpdate);
        TipoArticulo testTipoArticulo = tipoArticuloList.get(tipoArticuloList.size() - 1);
        assertThat(testTipoArticulo.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoArticulo() throws Exception {
        int databaseSizeBeforeUpdate = tipoArticuloRepository.findAll().size();

        // Create the TipoArticulo
        TipoArticuloDTO tipoArticuloDTO = tipoArticuloMapper.toDto(tipoArticulo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoArticuloMockMvc.perform(put("/api/tipo-articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoArticuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoArticulo in the database
        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoArticulo() throws Exception {
        // Initialize the database
        tipoArticuloRepository.saveAndFlush(tipoArticulo);

        int databaseSizeBeforeDelete = tipoArticuloRepository.findAll().size();

        // Delete the tipoArticulo
        restTipoArticuloMockMvc.perform(delete("/api/tipo-articulos/{id}", tipoArticulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoArticulo> tipoArticuloList = tipoArticuloRepository.findAll();
        assertThat(tipoArticuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoArticulo.class);
        TipoArticulo tipoArticulo1 = new TipoArticulo();
        tipoArticulo1.setId(1L);
        TipoArticulo tipoArticulo2 = new TipoArticulo();
        tipoArticulo2.setId(tipoArticulo1.getId());
        assertThat(tipoArticulo1).isEqualTo(tipoArticulo2);
        tipoArticulo2.setId(2L);
        assertThat(tipoArticulo1).isNotEqualTo(tipoArticulo2);
        tipoArticulo1.setId(null);
        assertThat(tipoArticulo1).isNotEqualTo(tipoArticulo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoArticuloDTO.class);
        TipoArticuloDTO tipoArticuloDTO1 = new TipoArticuloDTO();
        tipoArticuloDTO1.setId(1L);
        TipoArticuloDTO tipoArticuloDTO2 = new TipoArticuloDTO();
        assertThat(tipoArticuloDTO1).isNotEqualTo(tipoArticuloDTO2);
        tipoArticuloDTO2.setId(tipoArticuloDTO1.getId());
        assertThat(tipoArticuloDTO1).isEqualTo(tipoArticuloDTO2);
        tipoArticuloDTO2.setId(2L);
        assertThat(tipoArticuloDTO1).isNotEqualTo(tipoArticuloDTO2);
        tipoArticuloDTO1.setId(null);
        assertThat(tipoArticuloDTO1).isNotEqualTo(tipoArticuloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoArticuloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoArticuloMapper.fromId(null)).isNull();
    }
}
