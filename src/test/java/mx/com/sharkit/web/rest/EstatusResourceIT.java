package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.repository.EstatusRepository;
import mx.com.sharkit.service.EstatusService;
import mx.com.sharkit.service.dto.EstatusDTO;
import mx.com.sharkit.service.mapper.EstatusMapper;
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

import mx.com.sharkit.domain.enumeration.TipoEstatus;
/**
 * Integration tests for the {@link EstatusResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class EstatusResourceIT {

    private static final TipoEstatus DEFAULT_TIPO_ESTATUS = TipoEstatus.ESTATUS_PRODUCTO;
    private static final TipoEstatus UPDATED_TIPO_ESTATUS = TipoEstatus.ESTATUS_CLIENTE;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private EstatusMapper estatusMapper;

    @Autowired
    private EstatusService estatusService;

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

    private MockMvc restEstatusMockMvc;

    private Estatus estatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusResource estatusResource = new EstatusResource(estatusService);
        this.restEstatusMockMvc = MockMvcBuilders.standaloneSetup(estatusResource)
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
    public static Estatus createEntity(EntityManager em) {
        Estatus estatus = new Estatus()
            .tipoEstatus(DEFAULT_TIPO_ESTATUS)
            .nombre(DEFAULT_NOMBRE);
        return estatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estatus createUpdatedEntity(EntityManager em) {
        Estatus estatus = new Estatus()
            .tipoEstatus(UPDATED_TIPO_ESTATUS)
            .nombre(UPDATED_NOMBRE);
        return estatus;
    }

    @BeforeEach
    public void initTest() {
        estatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatus() throws Exception {
        int databaseSizeBeforeCreate = estatusRepository.findAll().size();

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);
        restEstatusMockMvc.perform(post("/api/estatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isCreated());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeCreate + 1);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getTipoEstatus()).isEqualTo(DEFAULT_TIPO_ESTATUS);
        assertThat(testEstatus.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createEstatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusRepository.findAll().size();

        // Create the Estatus with an existing ID
        estatus.setId(1L);
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusMockMvc.perform(post("/api/estatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = estatusRepository.findAll().size();
        // set the field null
        estatus.setNombre(null);

        // Create the Estatus, which fails.
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        restEstatusMockMvc.perform(post("/api/estatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isBadRequest());

        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstatuses() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        // Get all the estatusList
        restEstatusMockMvc.perform(get("/api/estatuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEstatus").value(hasItem(DEFAULT_TIPO_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        // Get the estatus
        restEstatusMockMvc.perform(get("/api/estatuses/{id}", estatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatus.getId().intValue()))
            .andExpect(jsonPath("$.tipoEstatus").value(DEFAULT_TIPO_ESTATUS.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstatus() throws Exception {
        // Get the estatus
        restEstatusMockMvc.perform(get("/api/estatuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();

        // Update the estatus
        Estatus updatedEstatus = estatusRepository.findById(estatus.getId()).get();
        // Disconnect from session so that the updates on updatedEstatus are not directly saved in db
        em.detach(updatedEstatus);
        updatedEstatus
            .tipoEstatus(UPDATED_TIPO_ESTATUS)
            .nombre(UPDATED_NOMBRE);
        EstatusDTO estatusDTO = estatusMapper.toDto(updatedEstatus);

        restEstatusMockMvc.perform(put("/api/estatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isOk());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getTipoEstatus()).isEqualTo(UPDATED_TIPO_ESTATUS);
        assertThat(testEstatus.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusMockMvc.perform(put("/api/estatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeDelete = estatusRepository.findAll().size();

        // Delete the estatus
        restEstatusMockMvc.perform(delete("/api/estatuses/{id}", estatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estatus.class);
        Estatus estatus1 = new Estatus();
        estatus1.setId(1L);
        Estatus estatus2 = new Estatus();
        estatus2.setId(estatus1.getId());
        assertThat(estatus1).isEqualTo(estatus2);
        estatus2.setId(2L);
        assertThat(estatus1).isNotEqualTo(estatus2);
        estatus1.setId(null);
        assertThat(estatus1).isNotEqualTo(estatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDTO.class);
        EstatusDTO estatusDTO1 = new EstatusDTO();
        estatusDTO1.setId(1L);
        EstatusDTO estatusDTO2 = new EstatusDTO();
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
        estatusDTO2.setId(estatusDTO1.getId());
        assertThat(estatusDTO1).isEqualTo(estatusDTO2);
        estatusDTO2.setId(2L);
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
        estatusDTO1.setId(null);
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusMapper.fromId(null)).isNull();
    }
}
