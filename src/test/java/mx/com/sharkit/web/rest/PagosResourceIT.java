package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Pagos;
import mx.com.sharkit.repository.PagosRepository;
import mx.com.sharkit.service.PagosService;
import mx.com.sharkit.service.dto.PagosDTO;
import mx.com.sharkit.service.mapper.PagosMapper;
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
 * Integration tests for the {@link PagosResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class PagosResourceIT {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private PagosMapper pagosMapper;

    @Autowired
    private PagosService pagosService;

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

    private MockMvc restPagosMockMvc;

    private Pagos pagos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagosResource pagosResource = new PagosResource(pagosService);
        this.restPagosMockMvc = MockMvcBuilders.standaloneSetup(pagosResource)
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
    public static Pagos createEntity(EntityManager em) {
        Pagos pagos = new Pagos();
        return pagos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagos createUpdatedEntity(EntityManager em) {
        Pagos pagos = new Pagos();
        return pagos;
    }

    @BeforeEach
    public void initTest() {
        pagos = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagos() throws Exception {
        int databaseSizeBeforeCreate = pagosRepository.findAll().size();

        // Create the Pagos
        PagosDTO pagosDTO = pagosMapper.toDto(pagos);
        restPagosMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagosDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagos in the database
        List<Pagos> pagosList = pagosRepository.findAll();
        assertThat(pagosList).hasSize(databaseSizeBeforeCreate + 1);
        Pagos testPagos = pagosList.get(pagosList.size() - 1);
    }

    @Test
    @Transactional
    public void createPagosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagosRepository.findAll().size();

        // Create the Pagos with an existing ID
        pagos.setId(1L);
        PagosDTO pagosDTO = pagosMapper.toDto(pagos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagosMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagos in the database
        List<Pagos> pagosList = pagosRepository.findAll();
        assertThat(pagosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPagos() throws Exception {
        // Initialize the database
        pagosRepository.saveAndFlush(pagos);

        // Get all the pagosList
        restPagosMockMvc.perform(get("/api/pagos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagos.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPagos() throws Exception {
        // Initialize the database
        pagosRepository.saveAndFlush(pagos);

        // Get the pagos
        restPagosMockMvc.perform(get("/api/pagos/{id}", pagos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagos.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPagos() throws Exception {
        // Get the pagos
        restPagosMockMvc.perform(get("/api/pagos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagos() throws Exception {
        // Initialize the database
        pagosRepository.saveAndFlush(pagos);

        int databaseSizeBeforeUpdate = pagosRepository.findAll().size();

        // Update the pagos
        Pagos updatedPagos = pagosRepository.findById(pagos.getId()).get();
        // Disconnect from session so that the updates on updatedPagos are not directly saved in db
        em.detach(updatedPagos);
        PagosDTO pagosDTO = pagosMapper.toDto(updatedPagos);

        restPagosMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagosDTO)))
            .andExpect(status().isOk());

        // Validate the Pagos in the database
        List<Pagos> pagosList = pagosRepository.findAll();
        assertThat(pagosList).hasSize(databaseSizeBeforeUpdate);
        Pagos testPagos = pagosList.get(pagosList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPagos() throws Exception {
        int databaseSizeBeforeUpdate = pagosRepository.findAll().size();

        // Create the Pagos
        PagosDTO pagosDTO = pagosMapper.toDto(pagos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagosMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagos in the database
        List<Pagos> pagosList = pagosRepository.findAll();
        assertThat(pagosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePagos() throws Exception {
        // Initialize the database
        pagosRepository.saveAndFlush(pagos);

        int databaseSizeBeforeDelete = pagosRepository.findAll().size();

        // Delete the pagos
        restPagosMockMvc.perform(delete("/api/pagos/{id}", pagos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagos> pagosList = pagosRepository.findAll();
        assertThat(pagosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagos.class);
        Pagos pagos1 = new Pagos();
        pagos1.setId(1L);
        Pagos pagos2 = new Pagos();
        pagos2.setId(pagos1.getId());
        assertThat(pagos1).isEqualTo(pagos2);
        pagos2.setId(2L);
        assertThat(pagos1).isNotEqualTo(pagos2);
        pagos1.setId(null);
        assertThat(pagos1).isNotEqualTo(pagos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagosDTO.class);
        PagosDTO pagosDTO1 = new PagosDTO();
        pagosDTO1.setId(1L);
        PagosDTO pagosDTO2 = new PagosDTO();
        assertThat(pagosDTO1).isNotEqualTo(pagosDTO2);
        pagosDTO2.setId(pagosDTO1.getId());
        assertThat(pagosDTO1).isEqualTo(pagosDTO2);
        pagosDTO2.setId(2L);
        assertThat(pagosDTO1).isNotEqualTo(pagosDTO2);
        pagosDTO1.setId(null);
        assertThat(pagosDTO1).isNotEqualTo(pagosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pagosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pagosMapper.fromId(null)).isNull();
    }
}
