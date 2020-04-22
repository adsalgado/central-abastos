package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.TransportistaTarifa;
import mx.com.sharkit.repository.TransportistaTarifaRepository;
import mx.com.sharkit.service.TransportistaTarifaService;
import mx.com.sharkit.service.dto.TransportistaTarifaDTO;
import mx.com.sharkit.service.mapper.TransportistaTarifaMapper;
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
 * Integration tests for the {@link TransportistaTarifaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class TransportistaTarifaResourceIT {

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
    private TransportistaTarifaRepository transportistaTarifaRepository;

    @Autowired
    private TransportistaTarifaMapper transportistaTarifaMapper;

    @Autowired
    private TransportistaTarifaService transportistaTarifaService;

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

    private MockMvc restTransportistaTarifaMockMvc;

    private TransportistaTarifa transportistaTarifa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportistaTarifaResource transportistaTarifaResource = new TransportistaTarifaResource(transportistaTarifaService);
        this.restTransportistaTarifaMockMvc = MockMvcBuilders.standaloneSetup(transportistaTarifaResource)
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
    public static TransportistaTarifa createEntity(EntityManager em) {
        TransportistaTarifa transportistaTarifa = new TransportistaTarifa()
            .rangoMinimo(DEFAULT_RANGO_MINIMO)
            .rangoMaximo(DEFAULT_RANGO_MAXIMO)
            .precio(DEFAULT_PRECIO);
        return transportistaTarifa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransportistaTarifa createUpdatedEntity(EntityManager em) {
        TransportistaTarifa transportistaTarifa = new TransportistaTarifa()
            .rangoMinimo(UPDATED_RANGO_MINIMO)
            .rangoMaximo(UPDATED_RANGO_MAXIMO)
            .precio(UPDATED_PRECIO);
        return transportistaTarifa;
    }

    @BeforeEach
    public void initTest() {
        transportistaTarifa = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportistaTarifa() throws Exception {
        int databaseSizeBeforeCreate = transportistaTarifaRepository.findAll().size();

        // Create the TransportistaTarifa
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);
        restTransportistaTarifaMockMvc.perform(post("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isCreated());

        // Validate the TransportistaTarifa in the database
        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeCreate + 1);
        TransportistaTarifa testTransportistaTarifa = transportistaTarifaList.get(transportistaTarifaList.size() - 1);
        assertThat(testTransportistaTarifa.getRangoMinimo()).isEqualTo(DEFAULT_RANGO_MINIMO);
        assertThat(testTransportistaTarifa.getRangoMaximo()).isEqualTo(DEFAULT_RANGO_MAXIMO);
        assertThat(testTransportistaTarifa.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createTransportistaTarifaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportistaTarifaRepository.findAll().size();

        // Create the TransportistaTarifa with an existing ID
        transportistaTarifa.setId(1L);
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportistaTarifaMockMvc.perform(post("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransportistaTarifa in the database
        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRangoMinimoIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportistaTarifaRepository.findAll().size();
        // set the field null
        transportistaTarifa.setRangoMinimo(null);

        // Create the TransportistaTarifa, which fails.
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);

        restTransportistaTarifaMockMvc.perform(post("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRangoMaximoIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportistaTarifaRepository.findAll().size();
        // set the field null
        transportistaTarifa.setRangoMaximo(null);

        // Create the TransportistaTarifa, which fails.
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);

        restTransportistaTarifaMockMvc.perform(post("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportistaTarifaRepository.findAll().size();
        // set the field null
        transportistaTarifa.setPrecio(null);

        // Create the TransportistaTarifa, which fails.
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);

        restTransportistaTarifaMockMvc.perform(post("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isBadRequest());

        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportistaTarifas() throws Exception {
        // Initialize the database
        transportistaTarifaRepository.saveAndFlush(transportistaTarifa);

        // Get all the transportistaTarifaList
        restTransportistaTarifaMockMvc.perform(get("/api/transportista-tarifas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportistaTarifa.getId().intValue())))
            .andExpect(jsonPath("$.[*].rangoMinimo").value(hasItem(DEFAULT_RANGO_MINIMO.intValue())))
            .andExpect(jsonPath("$.[*].rangoMaximo").value(hasItem(DEFAULT_RANGO_MAXIMO.intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getTransportistaTarifa() throws Exception {
        // Initialize the database
        transportistaTarifaRepository.saveAndFlush(transportistaTarifa);

        // Get the transportistaTarifa
        restTransportistaTarifaMockMvc.perform(get("/api/transportista-tarifas/{id}", transportistaTarifa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transportistaTarifa.getId().intValue()))
            .andExpect(jsonPath("$.rangoMinimo").value(DEFAULT_RANGO_MINIMO.intValue()))
            .andExpect(jsonPath("$.rangoMaximo").value(DEFAULT_RANGO_MAXIMO.intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransportistaTarifa() throws Exception {
        // Get the transportistaTarifa
        restTransportistaTarifaMockMvc.perform(get("/api/transportista-tarifas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportistaTarifa() throws Exception {
        // Initialize the database
        transportistaTarifaRepository.saveAndFlush(transportistaTarifa);

        int databaseSizeBeforeUpdate = transportistaTarifaRepository.findAll().size();

        // Update the transportistaTarifa
        TransportistaTarifa updatedTransportistaTarifa = transportistaTarifaRepository.findById(transportistaTarifa.getId()).get();
        // Disconnect from session so that the updates on updatedTransportistaTarifa are not directly saved in db
        em.detach(updatedTransportistaTarifa);
        updatedTransportistaTarifa
            .rangoMinimo(UPDATED_RANGO_MINIMO)
            .rangoMaximo(UPDATED_RANGO_MAXIMO)
            .precio(UPDATED_PRECIO);
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(updatedTransportistaTarifa);

        restTransportistaTarifaMockMvc.perform(put("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isOk());

        // Validate the TransportistaTarifa in the database
        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeUpdate);
        TransportistaTarifa testTransportistaTarifa = transportistaTarifaList.get(transportistaTarifaList.size() - 1);
        assertThat(testTransportistaTarifa.getRangoMinimo()).isEqualTo(UPDATED_RANGO_MINIMO);
        assertThat(testTransportistaTarifa.getRangoMaximo()).isEqualTo(UPDATED_RANGO_MAXIMO);
        assertThat(testTransportistaTarifa.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportistaTarifa() throws Exception {
        int databaseSizeBeforeUpdate = transportistaTarifaRepository.findAll().size();

        // Create the TransportistaTarifa
        TransportistaTarifaDTO transportistaTarifaDTO = transportistaTarifaMapper.toDto(transportistaTarifa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportistaTarifaMockMvc.perform(put("/api/transportista-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransportistaTarifa in the database
        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportistaTarifa() throws Exception {
        // Initialize the database
        transportistaTarifaRepository.saveAndFlush(transportistaTarifa);

        int databaseSizeBeforeDelete = transportistaTarifaRepository.findAll().size();

        // Delete the transportistaTarifa
        restTransportistaTarifaMockMvc.perform(delete("/api/transportista-tarifas/{id}", transportistaTarifa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransportistaTarifa> transportistaTarifaList = transportistaTarifaRepository.findAll();
        assertThat(transportistaTarifaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportistaTarifa.class);
        TransportistaTarifa transportistaTarifa1 = new TransportistaTarifa();
        transportistaTarifa1.setId(1L);
        TransportistaTarifa transportistaTarifa2 = new TransportistaTarifa();
        transportistaTarifa2.setId(transportistaTarifa1.getId());
        assertThat(transportistaTarifa1).isEqualTo(transportistaTarifa2);
        transportistaTarifa2.setId(2L);
        assertThat(transportistaTarifa1).isNotEqualTo(transportistaTarifa2);
        transportistaTarifa1.setId(null);
        assertThat(transportistaTarifa1).isNotEqualTo(transportistaTarifa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportistaTarifaDTO.class);
        TransportistaTarifaDTO transportistaTarifaDTO1 = new TransportistaTarifaDTO();
        transportistaTarifaDTO1.setId(1L);
        TransportistaTarifaDTO transportistaTarifaDTO2 = new TransportistaTarifaDTO();
        assertThat(transportistaTarifaDTO1).isNotEqualTo(transportistaTarifaDTO2);
        transportistaTarifaDTO2.setId(transportistaTarifaDTO1.getId());
        assertThat(transportistaTarifaDTO1).isEqualTo(transportistaTarifaDTO2);
        transportistaTarifaDTO2.setId(2L);
        assertThat(transportistaTarifaDTO1).isNotEqualTo(transportistaTarifaDTO2);
        transportistaTarifaDTO1.setId(null);
        assertThat(transportistaTarifaDTO1).isNotEqualTo(transportistaTarifaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transportistaTarifaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transportistaTarifaMapper.fromId(null)).isNull();
    }
}
