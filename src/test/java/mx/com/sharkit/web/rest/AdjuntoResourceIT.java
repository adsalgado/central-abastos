package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.service.AdjuntoService;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.mapper.AdjuntoMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdjuntoResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class AdjuntoResourceIT {

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;
    private static final Long SMALLER_SIZE = 1L - 1L;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private AdjuntoRepository adjuntoRepository;

    @Autowired
    private AdjuntoMapper adjuntoMapper;

    @Autowired
    private AdjuntoService adjuntoService;

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

    private MockMvc restAdjuntoMockMvc;

    private Adjunto adjunto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdjuntoResource adjuntoResource = new AdjuntoResource(adjuntoService);
        this.restAdjuntoMockMvc = MockMvcBuilders.standaloneSetup(adjuntoResource)
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
    public static Adjunto createEntity(EntityManager em) {
        Adjunto adjunto = new Adjunto()
            .contentType(DEFAULT_CONTENT_TYPE)
            .size(DEFAULT_SIZE)
            .fileName(DEFAULT_FILE_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return adjunto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adjunto createUpdatedEntity(EntityManager em) {
        Adjunto adjunto = new Adjunto()
            .contentType(UPDATED_CONTENT_TYPE)
            .size(UPDATED_SIZE)
            .fileName(UPDATED_FILE_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return adjunto;
    }

    @BeforeEach
    public void initTest() {
        adjunto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdjunto() throws Exception {
        int databaseSizeBeforeCreate = adjuntoRepository.findAll().size();

        // Create the Adjunto
        AdjuntoDTO adjuntoDTO = adjuntoMapper.toDto(adjunto);
        restAdjuntoMockMvc.perform(post("/api/adjuntos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjuntoDTO)))
            .andExpect(status().isCreated());

        // Validate the Adjunto in the database
        List<Adjunto> adjuntoList = adjuntoRepository.findAll();
        assertThat(adjuntoList).hasSize(databaseSizeBeforeCreate + 1);
        Adjunto testAdjunto = adjuntoList.get(adjuntoList.size() - 1);
        assertThat(testAdjunto.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testAdjunto.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testAdjunto.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAdjunto.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAdjunto.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAdjuntoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjuntoRepository.findAll().size();

        // Create the Adjunto with an existing ID
        adjunto.setId(1L);
        AdjuntoDTO adjuntoDTO = adjuntoMapper.toDto(adjunto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjuntoMockMvc.perform(post("/api/adjuntos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adjunto in the database
        List<Adjunto> adjuntoList = adjuntoRepository.findAll();
        assertThat(adjuntoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdjuntos() throws Exception {
        // Initialize the database
        adjuntoRepository.saveAndFlush(adjunto);

        // Get all the adjuntoList
        restAdjuntoMockMvc.perform(get("/api/adjuntos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjunto.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getAdjunto() throws Exception {
        // Initialize the database
        adjuntoRepository.saveAndFlush(adjunto);

        // Get the adjunto
        restAdjuntoMockMvc.perform(get("/api/adjuntos/{id}", adjunto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adjunto.getId().intValue()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingAdjunto() throws Exception {
        // Get the adjunto
        restAdjuntoMockMvc.perform(get("/api/adjuntos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdjunto() throws Exception {
        // Initialize the database
        adjuntoRepository.saveAndFlush(adjunto);

        int databaseSizeBeforeUpdate = adjuntoRepository.findAll().size();

        // Update the adjunto
        Adjunto updatedAdjunto = adjuntoRepository.findById(adjunto.getId()).get();
        // Disconnect from session so that the updates on updatedAdjunto are not directly saved in db
        em.detach(updatedAdjunto);
        updatedAdjunto
            .contentType(UPDATED_CONTENT_TYPE)
            .size(UPDATED_SIZE)
            .fileName(UPDATED_FILE_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        AdjuntoDTO adjuntoDTO = adjuntoMapper.toDto(updatedAdjunto);

        restAdjuntoMockMvc.perform(put("/api/adjuntos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjuntoDTO)))
            .andExpect(status().isOk());

        // Validate the Adjunto in the database
        List<Adjunto> adjuntoList = adjuntoRepository.findAll();
        assertThat(adjuntoList).hasSize(databaseSizeBeforeUpdate);
        Adjunto testAdjunto = adjuntoList.get(adjuntoList.size() - 1);
        assertThat(testAdjunto.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAdjunto.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testAdjunto.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAdjunto.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAdjunto.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdjunto() throws Exception {
        int databaseSizeBeforeUpdate = adjuntoRepository.findAll().size();

        // Create the Adjunto
        AdjuntoDTO adjuntoDTO = adjuntoMapper.toDto(adjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjuntoMockMvc.perform(put("/api/adjuntos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adjunto in the database
        List<Adjunto> adjuntoList = adjuntoRepository.findAll();
        assertThat(adjuntoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdjunto() throws Exception {
        // Initialize the database
        adjuntoRepository.saveAndFlush(adjunto);

        int databaseSizeBeforeDelete = adjuntoRepository.findAll().size();

        // Delete the adjunto
        restAdjuntoMockMvc.perform(delete("/api/adjuntos/{id}", adjunto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adjunto> adjuntoList = adjuntoRepository.findAll();
        assertThat(adjuntoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adjunto.class);
        Adjunto adjunto1 = new Adjunto();
        adjunto1.setId(1L);
        Adjunto adjunto2 = new Adjunto();
        adjunto2.setId(adjunto1.getId());
        assertThat(adjunto1).isEqualTo(adjunto2);
        adjunto2.setId(2L);
        assertThat(adjunto1).isNotEqualTo(adjunto2);
        adjunto1.setId(null);
        assertThat(adjunto1).isNotEqualTo(adjunto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjuntoDTO.class);
        AdjuntoDTO adjuntoDTO1 = new AdjuntoDTO();
        adjuntoDTO1.setId(1L);
        AdjuntoDTO adjuntoDTO2 = new AdjuntoDTO();
        assertThat(adjuntoDTO1).isNotEqualTo(adjuntoDTO2);
        adjuntoDTO2.setId(adjuntoDTO1.getId());
        assertThat(adjuntoDTO1).isEqualTo(adjuntoDTO2);
        adjuntoDTO2.setId(2L);
        assertThat(adjuntoDTO1).isNotEqualTo(adjuntoDTO2);
        adjuntoDTO1.setId(null);
        assertThat(adjuntoDTO1).isNotEqualTo(adjuntoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adjuntoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adjuntoMapper.fromId(null)).isNull();
    }
}
