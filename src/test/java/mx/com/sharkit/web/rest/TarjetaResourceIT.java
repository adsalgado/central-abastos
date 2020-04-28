package mx.com.sharkit.web.rest;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Tarjeta;
import mx.com.sharkit.repository.TarjetaRepository;
import mx.com.sharkit.service.TarjetaService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.TarjetaDTO;
import mx.com.sharkit.service.mapper.TarjetaMapper;
import mx.com.sharkit.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link TarjetaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class TarjetaResourceIT {

    private static final String DEFAULT_NUMERO_TARJETA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TARJETA = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_CADUCIDAD = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_CADUCIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SEGURIDAD = "AAA";
    private static final String UPDATED_NUMERO_SEGURIDAD = "BBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ALTA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TarjetaMapper tarjetaMapper;

    @Autowired
    private TarjetaService tarjetaService;
    
    @Autowired
    private UserService userService;

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

    private MockMvc restTarjetaMockMvc;

    private Tarjeta tarjeta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarjetaResource tarjetaResource = new TarjetaResource(tarjetaService, userService);
        this.restTarjetaMockMvc = MockMvcBuilders.standaloneSetup(tarjetaResource)
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
    public static Tarjeta createEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta();
        return tarjeta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarjeta createUpdatedEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta();
        return tarjeta;
    }

    @BeforeEach
    public void initTest() {
        tarjeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarjeta() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumeroTarjeta()).isEqualTo(DEFAULT_NUMERO_TARJETA);
        assertThat(testTarjeta.getFechaCaducidad()).isEqualTo(DEFAULT_FECHA_CADUCIDAD);
        assertThat(testTarjeta.getNumeroSeguridad()).isEqualTo(DEFAULT_NUMERO_SEGURIDAD);
        assertThat(testTarjeta.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void createTarjetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta with an existing ID
        tarjeta.setId(1L);
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroTarjetaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setNumeroTarjeta(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaCaducidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setFechaCaducidad(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroSeguridadIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setNumeroSeguridad(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setFechaAlta(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarjetas() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get all the tarjetaList
        restTarjetaMockMvc.perform(get("/api/tarjetas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarjeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTarjeta").value(hasItem(DEFAULT_NUMERO_TARJETA.toString())))
            .andExpect(jsonPath("$.[*].fechaCaducidad").value(hasItem(DEFAULT_FECHA_CADUCIDAD.toString())))
            .andExpect(jsonPath("$.[*].numeroSeguridad").value(hasItem(DEFAULT_NUMERO_SEGURIDAD.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())));
    }
    
    @Test
    @Transactional
    public void getTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", tarjeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarjeta.getId().intValue()))
            .andExpect(jsonPath("$.numeroTarjeta").value(DEFAULT_NUMERO_TARJETA.toString()))
            .andExpect(jsonPath("$.fechaCaducidad").value(DEFAULT_FECHA_CADUCIDAD.toString()))
            .andExpect(jsonPath("$.numeroSeguridad").value(DEFAULT_NUMERO_SEGURIDAD.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTarjeta() throws Exception {
        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta
        Tarjeta updatedTarjeta = tarjetaRepository.findById(tarjeta.getId()).get();
        // Disconnect from session so that the updates on updatedTarjeta are not directly saved in db
        em.detach(updatedTarjeta);
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(updatedTarjeta);

        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumeroTarjeta()).isEqualTo(UPDATED_NUMERO_TARJETA);
        assertThat(testTarjeta.getFechaCaducidad()).isEqualTo(UPDATED_FECHA_CADUCIDAD);
        assertThat(testTarjeta.getNumeroSeguridad()).isEqualTo(UPDATED_NUMERO_SEGURIDAD);
        assertThat(testTarjeta.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void updateNonExistingTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Create the Tarjeta
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeDelete = tarjetaRepository.findAll().size();

        // Delete the tarjeta
        restTarjetaMockMvc.perform(delete("/api/tarjetas/{id}", tarjeta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarjeta.class);
        Tarjeta tarjeta1 = new Tarjeta();
        tarjeta1.setId(1L);
        Tarjeta tarjeta2 = new Tarjeta();
        tarjeta2.setId(tarjeta1.getId());
        assertThat(tarjeta1).isEqualTo(tarjeta2);
        tarjeta2.setId(2L);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
        tarjeta1.setId(null);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarjetaDTO.class);
        TarjetaDTO tarjetaDTO1 = new TarjetaDTO();
        tarjetaDTO1.setId(1L);
        TarjetaDTO tarjetaDTO2 = new TarjetaDTO();
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
        tarjetaDTO2.setId(tarjetaDTO1.getId());
        assertThat(tarjetaDTO1).isEqualTo(tarjetaDTO2);
        tarjetaDTO2.setId(2L);
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
        tarjetaDTO1.setId(null);
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tarjetaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tarjetaMapper.fromId(null)).isNull();
    }
}
