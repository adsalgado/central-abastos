package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.TipoOferta;
import mx.com.sharkit.repository.TipoOfertaRepository;
import mx.com.sharkit.service.TipoOfertaService;
import mx.com.sharkit.service.dto.TipoOfertaDTO;
import mx.com.sharkit.service.mapper.TipoOfertaMapper;
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
 * Integration tests for the {@link TipoOfertaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class TipoOfertaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoOfertaRepository tipoOfertaRepository;

    @Autowired
    private TipoOfertaMapper tipoOfertaMapper;

    @Autowired
    private TipoOfertaService tipoOfertaService;

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

    private MockMvc restTipoOfertaMockMvc;

    private TipoOferta tipoOferta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoOfertaResource tipoOfertaResource = new TipoOfertaResource(tipoOfertaService);
        this.restTipoOfertaMockMvc = MockMvcBuilders.standaloneSetup(tipoOfertaResource)
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
    public static TipoOferta createEntity(EntityManager em) {
        TipoOferta tipoOferta = new TipoOferta()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoOferta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoOferta createUpdatedEntity(EntityManager em) {
        TipoOferta tipoOferta = new TipoOferta()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        return tipoOferta;
    }

    @BeforeEach
    public void initTest() {
        tipoOferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoOferta() throws Exception {
        int databaseSizeBeforeCreate = tipoOfertaRepository.findAll().size();

        // Create the TipoOferta
        TipoOfertaDTO tipoOfertaDTO = tipoOfertaMapper.toDto(tipoOferta);
        restTipoOfertaMockMvc.perform(post("/api/tipo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOfertaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoOferta in the database
        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoOferta testTipoOferta = tipoOfertaList.get(tipoOfertaList.size() - 1);
        assertThat(testTipoOferta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoOferta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoOfertaRepository.findAll().size();

        // Create the TipoOferta with an existing ID
        tipoOferta.setId(1L);
        TipoOfertaDTO tipoOfertaDTO = tipoOfertaMapper.toDto(tipoOferta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoOfertaMockMvc.perform(post("/api/tipo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOfertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoOferta in the database
        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoOfertaRepository.findAll().size();
        // set the field null
        tipoOferta.setNombre(null);

        // Create the TipoOferta, which fails.
        TipoOfertaDTO tipoOfertaDTO = tipoOfertaMapper.toDto(tipoOferta);

        restTipoOfertaMockMvc.perform(post("/api/tipo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOfertaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoOfertas() throws Exception {
        // Initialize the database
        tipoOfertaRepository.saveAndFlush(tipoOferta);

        // Get all the tipoOfertaList
        restTipoOfertaMockMvc.perform(get("/api/tipo-ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoOferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoOferta() throws Exception {
        // Initialize the database
        tipoOfertaRepository.saveAndFlush(tipoOferta);

        // Get the tipoOferta
        restTipoOfertaMockMvc.perform(get("/api/tipo-ofertas/{id}", tipoOferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoOferta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoOferta() throws Exception {
        // Get the tipoOferta
        restTipoOfertaMockMvc.perform(get("/api/tipo-ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoOferta() throws Exception {
        // Initialize the database
        tipoOfertaRepository.saveAndFlush(tipoOferta);

        int databaseSizeBeforeUpdate = tipoOfertaRepository.findAll().size();

        // Update the tipoOferta
        TipoOferta updatedTipoOferta = tipoOfertaRepository.findById(tipoOferta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoOferta are not directly saved in db
        em.detach(updatedTipoOferta);
        updatedTipoOferta
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        TipoOfertaDTO tipoOfertaDTO = tipoOfertaMapper.toDto(updatedTipoOferta);

        restTipoOfertaMockMvc.perform(put("/api/tipo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOfertaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoOferta in the database
        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeUpdate);
        TipoOferta testTipoOferta = tipoOfertaList.get(tipoOfertaList.size() - 1);
        assertThat(testTipoOferta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoOferta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoOferta() throws Exception {
        int databaseSizeBeforeUpdate = tipoOfertaRepository.findAll().size();

        // Create the TipoOferta
        TipoOfertaDTO tipoOfertaDTO = tipoOfertaMapper.toDto(tipoOferta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoOfertaMockMvc.perform(put("/api/tipo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOfertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoOferta in the database
        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoOferta() throws Exception {
        // Initialize the database
        tipoOfertaRepository.saveAndFlush(tipoOferta);

        int databaseSizeBeforeDelete = tipoOfertaRepository.findAll().size();

        // Delete the tipoOferta
        restTipoOfertaMockMvc.perform(delete("/api/tipo-ofertas/{id}", tipoOferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoOferta> tipoOfertaList = tipoOfertaRepository.findAll();
        assertThat(tipoOfertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoOferta.class);
        TipoOferta tipoOferta1 = new TipoOferta();
        tipoOferta1.setId(1L);
        TipoOferta tipoOferta2 = new TipoOferta();
        tipoOferta2.setId(tipoOferta1.getId());
        assertThat(tipoOferta1).isEqualTo(tipoOferta2);
        tipoOferta2.setId(2L);
        assertThat(tipoOferta1).isNotEqualTo(tipoOferta2);
        tipoOferta1.setId(null);
        assertThat(tipoOferta1).isNotEqualTo(tipoOferta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoOfertaDTO.class);
        TipoOfertaDTO tipoOfertaDTO1 = new TipoOfertaDTO();
        tipoOfertaDTO1.setId(1L);
        TipoOfertaDTO tipoOfertaDTO2 = new TipoOfertaDTO();
        assertThat(tipoOfertaDTO1).isNotEqualTo(tipoOfertaDTO2);
        tipoOfertaDTO2.setId(tipoOfertaDTO1.getId());
        assertThat(tipoOfertaDTO1).isEqualTo(tipoOfertaDTO2);
        tipoOfertaDTO2.setId(2L);
        assertThat(tipoOfertaDTO1).isNotEqualTo(tipoOfertaDTO2);
        tipoOfertaDTO1.setId(null);
        assertThat(tipoOfertaDTO1).isNotEqualTo(tipoOfertaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoOfertaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoOfertaMapper.fromId(null)).isNull();
    }
}
