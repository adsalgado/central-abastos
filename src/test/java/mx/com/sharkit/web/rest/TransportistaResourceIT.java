package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.repository.TransportistaRepository;
import mx.com.sharkit.service.TransportistaService;
import mx.com.sharkit.service.dto.TransportistaDTO;
import mx.com.sharkit.service.mapper.TransportistaMapper;
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
 * Integration tests for the {@link TransportistaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class TransportistaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_ALTA = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_MODIFICACION = Instant.ofEpochMilli(-1L);

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private TransportistaMapper transportistaMapper;

    @Autowired
    private TransportistaService transportistaService;

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

    private MockMvc restTransportistaMockMvc;

    private Transportista transportista;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportistaResource transportistaResource = new TransportistaResource(transportistaService);
        this.restTransportistaMockMvc = MockMvcBuilders.standaloneSetup(transportistaResource)
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
    public static Transportista createEntity(EntityManager em) {
        Transportista transportista = new Transportista()
            .nombre(DEFAULT_NOMBRE)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return transportista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transportista createUpdatedEntity(EntityManager em) {
        Transportista transportista = new Transportista()
            .nombre(UPDATED_NOMBRE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        return transportista;
    }

    @BeforeEach
    public void initTest() {
        transportista = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportista() throws Exception {
        int databaseSizeBeforeCreate = transportistaRepository.findAll().size();

        // Create the Transportista
        TransportistaDTO transportistaDTO = transportistaMapper.toDto(transportista);
        restTransportistaMockMvc.perform(post("/api/transportistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Transportista in the database
        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeCreate + 1);
        Transportista testTransportista = transportistaList.get(transportistaList.size() - 1);
        assertThat(testTransportista.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTransportista.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testTransportista.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void createTransportistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportistaRepository.findAll().size();

        // Create the Transportista with an existing ID
        transportista.setId(1L);
        TransportistaDTO transportistaDTO = transportistaMapper.toDto(transportista);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportistaMockMvc.perform(post("/api/transportistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transportista in the database
        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportistaRepository.findAll().size();
        // set the field null
        transportista.setNombre(null);

        // Create the Transportista, which fails.
        TransportistaDTO transportistaDTO = transportistaMapper.toDto(transportista);

        restTransportistaMockMvc.perform(post("/api/transportistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaDTO)))
            .andExpect(status().isBadRequest());

        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportistas() throws Exception {
        // Initialize the database
        transportistaRepository.saveAndFlush(transportista);

        // Get all the transportistaList
        restTransportistaMockMvc.perform(get("/api/transportistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getTransportista() throws Exception {
        // Initialize the database
        transportistaRepository.saveAndFlush(transportista);

        // Get the transportista
        restTransportistaMockMvc.perform(get("/api/transportistas/{id}", transportista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transportista.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransportista() throws Exception {
        // Get the transportista
        restTransportistaMockMvc.perform(get("/api/transportistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportista() throws Exception {
        // Initialize the database
        transportistaRepository.saveAndFlush(transportista);

        int databaseSizeBeforeUpdate = transportistaRepository.findAll().size();

        // Update the transportista
        Transportista updatedTransportista = transportistaRepository.findById(transportista.getId()).get();
        // Disconnect from session so that the updates on updatedTransportista are not directly saved in db
        em.detach(updatedTransportista);
        updatedTransportista
            .nombre(UPDATED_NOMBRE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        TransportistaDTO transportistaDTO = transportistaMapper.toDto(updatedTransportista);

        restTransportistaMockMvc.perform(put("/api/transportistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaDTO)))
            .andExpect(status().isOk());

        // Validate the Transportista in the database
        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeUpdate);
        Transportista testTransportista = transportistaList.get(transportistaList.size() - 1);
        assertThat(testTransportista.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTransportista.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testTransportista.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportista() throws Exception {
        int databaseSizeBeforeUpdate = transportistaRepository.findAll().size();

        // Create the Transportista
        TransportistaDTO transportistaDTO = transportistaMapper.toDto(transportista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportistaMockMvc.perform(put("/api/transportistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transportista in the database
        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportista() throws Exception {
        // Initialize the database
        transportistaRepository.saveAndFlush(transportista);

        int databaseSizeBeforeDelete = transportistaRepository.findAll().size();

        // Delete the transportista
        restTransportistaMockMvc.perform(delete("/api/transportistas/{id}", transportista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transportista> transportistaList = transportistaRepository.findAll();
        assertThat(transportistaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transportista.class);
        Transportista transportista1 = new Transportista();
        transportista1.setId(1L);
        Transportista transportista2 = new Transportista();
        transportista2.setId(transportista1.getId());
        assertThat(transportista1).isEqualTo(transportista2);
        transportista2.setId(2L);
        assertThat(transportista1).isNotEqualTo(transportista2);
        transportista1.setId(null);
        assertThat(transportista1).isNotEqualTo(transportista2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportistaDTO.class);
        TransportistaDTO transportistaDTO1 = new TransportistaDTO();
        transportistaDTO1.setId(1L);
        TransportistaDTO transportistaDTO2 = new TransportistaDTO();
        assertThat(transportistaDTO1).isNotEqualTo(transportistaDTO2);
        transportistaDTO2.setId(transportistaDTO1.getId());
        assertThat(transportistaDTO1).isEqualTo(transportistaDTO2);
        transportistaDTO2.setId(2L);
        assertThat(transportistaDTO1).isNotEqualTo(transportistaDTO2);
        transportistaDTO1.setId(null);
        assertThat(transportistaDTO1).isNotEqualTo(transportistaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transportistaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transportistaMapper.fromId(null)).isNull();
    }
}
