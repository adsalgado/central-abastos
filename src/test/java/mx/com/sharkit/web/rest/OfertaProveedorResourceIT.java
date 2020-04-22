package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.OfertaProveedor;
import mx.com.sharkit.repository.OfertaProveedorRepository;
import mx.com.sharkit.service.OfertaProveedorService;
import mx.com.sharkit.service.dto.OfertaProveedorDTO;
import mx.com.sharkit.service.mapper.OfertaProveedorMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OfertaProveedorResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class OfertaProveedorResourceIT {

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_INICIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_FIN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private OfertaProveedorRepository ofertaProveedorRepository;

    @Autowired
    private OfertaProveedorMapper ofertaProveedorMapper;

    @Autowired
    private OfertaProveedorService ofertaProveedorService;

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

    private MockMvc restOfertaProveedorMockMvc;

    private OfertaProveedor ofertaProveedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfertaProveedorResource ofertaProveedorResource = new OfertaProveedorResource(ofertaProveedorService);
        this.restOfertaProveedorMockMvc = MockMvcBuilders.standaloneSetup(ofertaProveedorResource)
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
    public static OfertaProveedor createEntity(EntityManager em) {
        OfertaProveedor ofertaProveedor = new OfertaProveedor()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        return ofertaProveedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfertaProveedor createUpdatedEntity(EntityManager em) {
        OfertaProveedor ofertaProveedor = new OfertaProveedor()
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        return ofertaProveedor;
    }

    @BeforeEach
    public void initTest() {
        ofertaProveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfertaProveedor() throws Exception {
        int databaseSizeBeforeCreate = ofertaProveedorRepository.findAll().size();

        // Create the OfertaProveedor
        OfertaProveedorDTO ofertaProveedorDTO = ofertaProveedorMapper.toDto(ofertaProveedor);
        restOfertaProveedorMockMvc.perform(post("/api/oferta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaProveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the OfertaProveedor in the database
        List<OfertaProveedor> ofertaProveedorList = ofertaProveedorRepository.findAll();
        assertThat(ofertaProveedorList).hasSize(databaseSizeBeforeCreate + 1);
        OfertaProveedor testOfertaProveedor = ofertaProveedorList.get(ofertaProveedorList.size() - 1);
        assertThat(testOfertaProveedor.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testOfertaProveedor.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    public void createOfertaProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ofertaProveedorRepository.findAll().size();

        // Create the OfertaProveedor with an existing ID
        ofertaProveedor.setId(1L);
        OfertaProveedorDTO ofertaProveedorDTO = ofertaProveedorMapper.toDto(ofertaProveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfertaProveedorMockMvc.perform(post("/api/oferta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfertaProveedor in the database
        List<OfertaProveedor> ofertaProveedorList = ofertaProveedorRepository.findAll();
        assertThat(ofertaProveedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOfertaProveedors() throws Exception {
        // Initialize the database
        ofertaProveedorRepository.saveAndFlush(ofertaProveedor);

        // Get all the ofertaProveedorList
        restOfertaProveedorMockMvc.perform(get("/api/oferta-proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ofertaProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getOfertaProveedor() throws Exception {
        // Initialize the database
        ofertaProveedorRepository.saveAndFlush(ofertaProveedor);

        // Get the ofertaProveedor
        restOfertaProveedorMockMvc.perform(get("/api/oferta-proveedors/{id}", ofertaProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ofertaProveedor.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfertaProveedor() throws Exception {
        // Get the ofertaProveedor
        restOfertaProveedorMockMvc.perform(get("/api/oferta-proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfertaProveedor() throws Exception {
        // Initialize the database
        ofertaProveedorRepository.saveAndFlush(ofertaProveedor);

        int databaseSizeBeforeUpdate = ofertaProveedorRepository.findAll().size();

        // Update the ofertaProveedor
        OfertaProveedor updatedOfertaProveedor = ofertaProveedorRepository.findById(ofertaProveedor.getId()).get();
        // Disconnect from session so that the updates on updatedOfertaProveedor are not directly saved in db
        em.detach(updatedOfertaProveedor);
        updatedOfertaProveedor
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        OfertaProveedorDTO ofertaProveedorDTO = ofertaProveedorMapper.toDto(updatedOfertaProveedor);

        restOfertaProveedorMockMvc.perform(put("/api/oferta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaProveedorDTO)))
            .andExpect(status().isOk());

        // Validate the OfertaProveedor in the database
        List<OfertaProveedor> ofertaProveedorList = ofertaProveedorRepository.findAll();
        assertThat(ofertaProveedorList).hasSize(databaseSizeBeforeUpdate);
        OfertaProveedor testOfertaProveedor = ofertaProveedorList.get(ofertaProveedorList.size() - 1);
        assertThat(testOfertaProveedor.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testOfertaProveedor.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingOfertaProveedor() throws Exception {
        int databaseSizeBeforeUpdate = ofertaProveedorRepository.findAll().size();

        // Create the OfertaProveedor
        OfertaProveedorDTO ofertaProveedorDTO = ofertaProveedorMapper.toDto(ofertaProveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfertaProveedorMockMvc.perform(put("/api/oferta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfertaProveedor in the database
        List<OfertaProveedor> ofertaProveedorList = ofertaProveedorRepository.findAll();
        assertThat(ofertaProveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfertaProveedor() throws Exception {
        // Initialize the database
        ofertaProveedorRepository.saveAndFlush(ofertaProveedor);

        int databaseSizeBeforeDelete = ofertaProveedorRepository.findAll().size();

        // Delete the ofertaProveedor
        restOfertaProveedorMockMvc.perform(delete("/api/oferta-proveedors/{id}", ofertaProveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfertaProveedor> ofertaProveedorList = ofertaProveedorRepository.findAll();
        assertThat(ofertaProveedorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfertaProveedor.class);
        OfertaProveedor ofertaProveedor1 = new OfertaProveedor();
        ofertaProveedor1.setId(1L);
        OfertaProveedor ofertaProveedor2 = new OfertaProveedor();
        ofertaProveedor2.setId(ofertaProveedor1.getId());
        assertThat(ofertaProveedor1).isEqualTo(ofertaProveedor2);
        ofertaProveedor2.setId(2L);
        assertThat(ofertaProveedor1).isNotEqualTo(ofertaProveedor2);
        ofertaProveedor1.setId(null);
        assertThat(ofertaProveedor1).isNotEqualTo(ofertaProveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfertaProveedorDTO.class);
        OfertaProveedorDTO ofertaProveedorDTO1 = new OfertaProveedorDTO();
        ofertaProveedorDTO1.setId(1L);
        OfertaProveedorDTO ofertaProveedorDTO2 = new OfertaProveedorDTO();
        assertThat(ofertaProveedorDTO1).isNotEqualTo(ofertaProveedorDTO2);
        ofertaProveedorDTO2.setId(ofertaProveedorDTO1.getId());
        assertThat(ofertaProveedorDTO1).isEqualTo(ofertaProveedorDTO2);
        ofertaProveedorDTO2.setId(2L);
        assertThat(ofertaProveedorDTO1).isNotEqualTo(ofertaProveedorDTO2);
        ofertaProveedorDTO1.setId(null);
        assertThat(ofertaProveedorDTO1).isNotEqualTo(ofertaProveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ofertaProveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ofertaProveedorMapper.fromId(null)).isNull();
    }
}
