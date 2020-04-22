package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.ProductoImagen;
import mx.com.sharkit.repository.ProductoImagenRepository;
import mx.com.sharkit.service.ProductoImagenService;
import mx.com.sharkit.service.dto.ProductoImagenDTO;
import mx.com.sharkit.service.mapper.ProductoImagenMapper;
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
 * Integration tests for the {@link ProductoImagenResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class ProductoImagenResourceIT {

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_ALTA = Instant.ofEpochMilli(-1L);

    @Autowired
    private ProductoImagenRepository productoImagenRepository;

    @Autowired
    private ProductoImagenMapper productoImagenMapper;

    @Autowired
    private ProductoImagenService productoImagenService;

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

    private MockMvc restProductoImagenMockMvc;

    private ProductoImagen productoImagen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoImagenResource productoImagenResource = new ProductoImagenResource(productoImagenService);
        this.restProductoImagenMockMvc = MockMvcBuilders.standaloneSetup(productoImagenResource)
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
    public static ProductoImagen createEntity(EntityManager em) {
        ProductoImagen productoImagen = new ProductoImagen()
            .fechaAlta(DEFAULT_FECHA_ALTA);
        return productoImagen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoImagen createUpdatedEntity(EntityManager em) {
        ProductoImagen productoImagen = new ProductoImagen()
            .fechaAlta(UPDATED_FECHA_ALTA);
        return productoImagen;
    }

    @BeforeEach
    public void initTest() {
        productoImagen = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoImagen() throws Exception {
        int databaseSizeBeforeCreate = productoImagenRepository.findAll().size();

        // Create the ProductoImagen
        ProductoImagenDTO productoImagenDTO = productoImagenMapper.toDto(productoImagen);
        restProductoImagenMockMvc.perform(post("/api/producto-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoImagen in the database
        List<ProductoImagen> productoImagenList = productoImagenRepository.findAll();
        assertThat(productoImagenList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoImagen testProductoImagen = productoImagenList.get(productoImagenList.size() - 1);
        assertThat(testProductoImagen.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void createProductoImagenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoImagenRepository.findAll().size();

        // Create the ProductoImagen with an existing ID
        productoImagen.setId(1L);
        ProductoImagenDTO productoImagenDTO = productoImagenMapper.toDto(productoImagen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoImagenMockMvc.perform(post("/api/producto-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImagen in the database
        List<ProductoImagen> productoImagenList = productoImagenRepository.findAll();
        assertThat(productoImagenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductoImagens() throws Exception {
        // Initialize the database
        productoImagenRepository.saveAndFlush(productoImagen);

        // Get all the productoImagenList
        restProductoImagenMockMvc.perform(get("/api/producto-imagens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())));
    }
    
    @Test
    @Transactional
    public void getProductoImagen() throws Exception {
        // Initialize the database
        productoImagenRepository.saveAndFlush(productoImagen);

        // Get the productoImagen
        restProductoImagenMockMvc.perform(get("/api/producto-imagens/{id}", productoImagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoImagen.getId().intValue()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductoImagen() throws Exception {
        // Get the productoImagen
        restProductoImagenMockMvc.perform(get("/api/producto-imagens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoImagen() throws Exception {
        // Initialize the database
        productoImagenRepository.saveAndFlush(productoImagen);

        int databaseSizeBeforeUpdate = productoImagenRepository.findAll().size();

        // Update the productoImagen
        ProductoImagen updatedProductoImagen = productoImagenRepository.findById(productoImagen.getId()).get();
        // Disconnect from session so that the updates on updatedProductoImagen are not directly saved in db
        em.detach(updatedProductoImagen);
        updatedProductoImagen
            .fechaAlta(UPDATED_FECHA_ALTA);
        ProductoImagenDTO productoImagenDTO = productoImagenMapper.toDto(updatedProductoImagen);

        restProductoImagenMockMvc.perform(put("/api/producto-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoImagen in the database
        List<ProductoImagen> productoImagenList = productoImagenRepository.findAll();
        assertThat(productoImagenList).hasSize(databaseSizeBeforeUpdate);
        ProductoImagen testProductoImagen = productoImagenList.get(productoImagenList.size() - 1);
        assertThat(testProductoImagen.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoImagen() throws Exception {
        int databaseSizeBeforeUpdate = productoImagenRepository.findAll().size();

        // Create the ProductoImagen
        ProductoImagenDTO productoImagenDTO = productoImagenMapper.toDto(productoImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoImagenMockMvc.perform(put("/api/producto-imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImagen in the database
        List<ProductoImagen> productoImagenList = productoImagenRepository.findAll();
        assertThat(productoImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoImagen() throws Exception {
        // Initialize the database
        productoImagenRepository.saveAndFlush(productoImagen);

        int databaseSizeBeforeDelete = productoImagenRepository.findAll().size();

        // Delete the productoImagen
        restProductoImagenMockMvc.perform(delete("/api/producto-imagens/{id}", productoImagen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoImagen> productoImagenList = productoImagenRepository.findAll();
        assertThat(productoImagenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImagen.class);
        ProductoImagen productoImagen1 = new ProductoImagen();
        productoImagen1.setId(1L);
        ProductoImagen productoImagen2 = new ProductoImagen();
        productoImagen2.setId(productoImagen1.getId());
        assertThat(productoImagen1).isEqualTo(productoImagen2);
        productoImagen2.setId(2L);
        assertThat(productoImagen1).isNotEqualTo(productoImagen2);
        productoImagen1.setId(null);
        assertThat(productoImagen1).isNotEqualTo(productoImagen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImagenDTO.class);
        ProductoImagenDTO productoImagenDTO1 = new ProductoImagenDTO();
        productoImagenDTO1.setId(1L);
        ProductoImagenDTO productoImagenDTO2 = new ProductoImagenDTO();
        assertThat(productoImagenDTO1).isNotEqualTo(productoImagenDTO2);
        productoImagenDTO2.setId(productoImagenDTO1.getId());
        assertThat(productoImagenDTO1).isEqualTo(productoImagenDTO2);
        productoImagenDTO2.setId(2L);
        assertThat(productoImagenDTO1).isNotEqualTo(productoImagenDTO2);
        productoImagenDTO1.setId(null);
        assertThat(productoImagenDTO1).isNotEqualTo(productoImagenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoImagenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoImagenMapper.fromId(null)).isNull();
    }
}
