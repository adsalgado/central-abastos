package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Queja;
import mx.com.sharkit.repository.QuejaRepository;
import mx.com.sharkit.service.QuejaService;
import mx.com.sharkit.service.dto.QuejaDTO;
import mx.com.sharkit.service.mapper.QuejaMapper;
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
 * Integration tests for the {@link QuejaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class QuejaResourceIT {

    @Autowired
    private QuejaRepository quejaRepository;

    @Autowired
    private QuejaMapper quejaMapper;

    @Autowired
    private QuejaService quejaService;

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

    private MockMvc restQuejaMockMvc;

    private Queja queja;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuejaResource quejaResource = new QuejaResource(quejaService);
        this.restQuejaMockMvc = MockMvcBuilders.standaloneSetup(quejaResource)
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
    public static Queja createEntity(EntityManager em) {
        Queja queja = new Queja();
        return queja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Queja createUpdatedEntity(EntityManager em) {
        Queja queja = new Queja();
        return queja;
    }

    @BeforeEach
    public void initTest() {
        queja = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueja() throws Exception {
        int databaseSizeBeforeCreate = quejaRepository.findAll().size();

        // Create the Queja
        QuejaDTO quejaDTO = quejaMapper.toDto(queja);
        restQuejaMockMvc.perform(post("/api/quejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quejaDTO)))
            .andExpect(status().isCreated());

        // Validate the Queja in the database
        List<Queja> quejaList = quejaRepository.findAll();
        assertThat(quejaList).hasSize(databaseSizeBeforeCreate + 1);
        Queja testQueja = quejaList.get(quejaList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuejaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quejaRepository.findAll().size();

        // Create the Queja with an existing ID
        queja.setId(1L);
        QuejaDTO quejaDTO = quejaMapper.toDto(queja);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuejaMockMvc.perform(post("/api/quejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quejaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Queja in the database
        List<Queja> quejaList = quejaRepository.findAll();
        assertThat(quejaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuejas() throws Exception {
        // Initialize the database
        quejaRepository.saveAndFlush(queja);

        // Get all the quejaList
        restQuejaMockMvc.perform(get("/api/quejas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queja.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getQueja() throws Exception {
        // Initialize the database
        quejaRepository.saveAndFlush(queja);

        // Get the queja
        restQuejaMockMvc.perform(get("/api/quejas/{id}", queja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queja.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQueja() throws Exception {
        // Get the queja
        restQuejaMockMvc.perform(get("/api/quejas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueja() throws Exception {
        // Initialize the database
        quejaRepository.saveAndFlush(queja);

        int databaseSizeBeforeUpdate = quejaRepository.findAll().size();

        // Update the queja
        Queja updatedQueja = quejaRepository.findById(queja.getId()).get();
        // Disconnect from session so that the updates on updatedQueja are not directly saved in db
        em.detach(updatedQueja);
        QuejaDTO quejaDTO = quejaMapper.toDto(updatedQueja);

        restQuejaMockMvc.perform(put("/api/quejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quejaDTO)))
            .andExpect(status().isOk());

        // Validate the Queja in the database
        List<Queja> quejaList = quejaRepository.findAll();
        assertThat(quejaList).hasSize(databaseSizeBeforeUpdate);
        Queja testQueja = quejaList.get(quejaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQueja() throws Exception {
        int databaseSizeBeforeUpdate = quejaRepository.findAll().size();

        // Create the Queja
        QuejaDTO quejaDTO = quejaMapper.toDto(queja);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuejaMockMvc.perform(put("/api/quejas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quejaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Queja in the database
        List<Queja> quejaList = quejaRepository.findAll();
        assertThat(quejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQueja() throws Exception {
        // Initialize the database
        quejaRepository.saveAndFlush(queja);

        int databaseSizeBeforeDelete = quejaRepository.findAll().size();

        // Delete the queja
        restQuejaMockMvc.perform(delete("/api/quejas/{id}", queja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Queja> quejaList = quejaRepository.findAll();
        assertThat(quejaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Queja.class);
        Queja queja1 = new Queja();
        queja1.setId(1L);
        Queja queja2 = new Queja();
        queja2.setId(queja1.getId());
        assertThat(queja1).isEqualTo(queja2);
        queja2.setId(2L);
        assertThat(queja1).isNotEqualTo(queja2);
        queja1.setId(null);
        assertThat(queja1).isNotEqualTo(queja2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuejaDTO.class);
        QuejaDTO quejaDTO1 = new QuejaDTO();
        quejaDTO1.setId(1L);
        QuejaDTO quejaDTO2 = new QuejaDTO();
        assertThat(quejaDTO1).isNotEqualTo(quejaDTO2);
        quejaDTO2.setId(quejaDTO1.getId());
        assertThat(quejaDTO1).isEqualTo(quejaDTO2);
        quejaDTO2.setId(2L);
        assertThat(quejaDTO1).isNotEqualTo(quejaDTO2);
        quejaDTO1.setId(null);
        assertThat(quejaDTO1).isNotEqualTo(quejaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quejaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quejaMapper.fromId(null)).isNull();
    }
}
