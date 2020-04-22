package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.UsuarioImagen;
import mx.com.sharkit.repository.UsuarioImagenRepository;
import mx.com.sharkit.service.UsuarioImagenService;
import mx.com.sharkit.service.dto.UsuarioImagenDTO;
import mx.com.sharkit.service.mapper.UsuarioImagenMapper;
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
 * Integration tests for the {@link UsuarioImagenResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class UsuarioImagenResourceIT {

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_ALTA = Instant.ofEpochMilli(-1L);

    @Autowired
    private UsuarioImagenRepository usuarioImagenRepository;

    @Autowired
    private UsuarioImagenMapper usuarioImagenMapper;

    @Autowired
    private UsuarioImagenService usuarioImagenService;

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

    private MockMvc restUsuarioImagenMockMvc;

    private UsuarioImagen usuarioImagen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioImagenResource usuarioImagenResource = new UsuarioImagenResource(usuarioImagenService);
        this.restUsuarioImagenMockMvc = MockMvcBuilders.standaloneSetup(usuarioImagenResource)
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
    public static UsuarioImagen createEntity(EntityManager em) {
        UsuarioImagen usuarioImagen = new UsuarioImagen()
            .fechaAlta(DEFAULT_FECHA_ALTA);
        return usuarioImagen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioImagen createUpdatedEntity(EntityManager em) {
        UsuarioImagen usuarioImagen = new UsuarioImagen()
            .fechaAlta(UPDATED_FECHA_ALTA);
        return usuarioImagen;
    }

    @BeforeEach
    public void initTest() {
        usuarioImagen = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioImagen() throws Exception {
        int databaseSizeBeforeCreate = usuarioImagenRepository.findAll().size();

        // Create the UsuarioImagen
        UsuarioImagenDTO usuarioImagenDTO = usuarioImagenMapper.toDto(usuarioImagen);
        restUsuarioImagenMockMvc.perform(post("/api/usuario-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioImagenDTO)))
            .andExpect(status().isCreated());

        // Validate the UsuarioImagen in the database
        List<UsuarioImagen> usuarioImagenList = usuarioImagenRepository.findAll();
        assertThat(usuarioImagenList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioImagen testUsuarioImagen = usuarioImagenList.get(usuarioImagenList.size() - 1);
        assertThat(testUsuarioImagen.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void createUsuarioImagenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioImagenRepository.findAll().size();

        // Create the UsuarioImagen with an existing ID
        usuarioImagen.setId(1L);
        UsuarioImagenDTO usuarioImagenDTO = usuarioImagenMapper.toDto(usuarioImagen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioImagenMockMvc.perform(post("/api/usuario-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioImagen in the database
        List<UsuarioImagen> usuarioImagenList = usuarioImagenRepository.findAll();
        assertThat(usuarioImagenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsuarioImagens() throws Exception {
        // Initialize the database
        usuarioImagenRepository.saveAndFlush(usuarioImagen);

        // Get all the usuarioImagenList
        restUsuarioImagenMockMvc.perform(get("/api/usuario-imagens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuarioImagen() throws Exception {
        // Initialize the database
        usuarioImagenRepository.saveAndFlush(usuarioImagen);

        // Get the usuarioImagen
        restUsuarioImagenMockMvc.perform(get("/api/usuario-imagens/{id}", usuarioImagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioImagen.getId().intValue()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarioImagen() throws Exception {
        // Get the usuarioImagen
        restUsuarioImagenMockMvc.perform(get("/api/usuario-imagens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioImagen() throws Exception {
        // Initialize the database
        usuarioImagenRepository.saveAndFlush(usuarioImagen);

        int databaseSizeBeforeUpdate = usuarioImagenRepository.findAll().size();

        // Update the usuarioImagen
        UsuarioImagen updatedUsuarioImagen = usuarioImagenRepository.findById(usuarioImagen.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioImagen are not directly saved in db
        em.detach(updatedUsuarioImagen);
        updatedUsuarioImagen
            .fechaAlta(UPDATED_FECHA_ALTA);
        UsuarioImagenDTO usuarioImagenDTO = usuarioImagenMapper.toDto(updatedUsuarioImagen);

        restUsuarioImagenMockMvc.perform(put("/api/usuario-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioImagenDTO)))
            .andExpect(status().isOk());

        // Validate the UsuarioImagen in the database
        List<UsuarioImagen> usuarioImagenList = usuarioImagenRepository.findAll();
        assertThat(usuarioImagenList).hasSize(databaseSizeBeforeUpdate);
        UsuarioImagen testUsuarioImagen = usuarioImagenList.get(usuarioImagenList.size() - 1);
        assertThat(testUsuarioImagen.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioImagen() throws Exception {
        int databaseSizeBeforeUpdate = usuarioImagenRepository.findAll().size();

        // Create the UsuarioImagen
        UsuarioImagenDTO usuarioImagenDTO = usuarioImagenMapper.toDto(usuarioImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioImagenMockMvc.perform(put("/api/usuario-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioImagen in the database
        List<UsuarioImagen> usuarioImagenList = usuarioImagenRepository.findAll();
        assertThat(usuarioImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarioImagen() throws Exception {
        // Initialize the database
        usuarioImagenRepository.saveAndFlush(usuarioImagen);

        int databaseSizeBeforeDelete = usuarioImagenRepository.findAll().size();

        // Delete the usuarioImagen
        restUsuarioImagenMockMvc.perform(delete("/api/usuario-imagens/{id}", usuarioImagen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioImagen> usuarioImagenList = usuarioImagenRepository.findAll();
        assertThat(usuarioImagenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioImagen.class);
        UsuarioImagen usuarioImagen1 = new UsuarioImagen();
        usuarioImagen1.setId(1L);
        UsuarioImagen usuarioImagen2 = new UsuarioImagen();
        usuarioImagen2.setId(usuarioImagen1.getId());
        assertThat(usuarioImagen1).isEqualTo(usuarioImagen2);
        usuarioImagen2.setId(2L);
        assertThat(usuarioImagen1).isNotEqualTo(usuarioImagen2);
        usuarioImagen1.setId(null);
        assertThat(usuarioImagen1).isNotEqualTo(usuarioImagen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioImagenDTO.class);
        UsuarioImagenDTO usuarioImagenDTO1 = new UsuarioImagenDTO();
        usuarioImagenDTO1.setId(1L);
        UsuarioImagenDTO usuarioImagenDTO2 = new UsuarioImagenDTO();
        assertThat(usuarioImagenDTO1).isNotEqualTo(usuarioImagenDTO2);
        usuarioImagenDTO2.setId(usuarioImagenDTO1.getId());
        assertThat(usuarioImagenDTO1).isEqualTo(usuarioImagenDTO2);
        usuarioImagenDTO2.setId(2L);
        assertThat(usuarioImagenDTO1).isNotEqualTo(usuarioImagenDTO2);
        usuarioImagenDTO1.setId(null);
        assertThat(usuarioImagenDTO1).isNotEqualTo(usuarioImagenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(usuarioImagenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(usuarioImagenMapper.fromId(null)).isNull();
    }
}
