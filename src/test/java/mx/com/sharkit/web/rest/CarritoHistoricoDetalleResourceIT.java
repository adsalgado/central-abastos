package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.CarritoHistoricoDetalle;
import mx.com.sharkit.repository.CarritoHistoricoDetalleRepository;
import mx.com.sharkit.service.CarritoHistoricoDetalleService;
import mx.com.sharkit.service.dto.CarritoHistoricoDetalleDTO;
import mx.com.sharkit.service.mapper.CarritoHistoricoDetalleMapper;
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
 * Integration tests for the {@link CarritoHistoricoDetalleResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class CarritoHistoricoDetalleResourceIT {

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);
    private static final BigDecimal SMALLER_CANTIDAD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRECIO = new BigDecimal(1 - 1);

    @Autowired
    private CarritoHistoricoDetalleRepository carritoHistoricoDetalleRepository;

    @Autowired
    private CarritoHistoricoDetalleMapper carritoHistoricoDetalleMapper;

    @Autowired
    private CarritoHistoricoDetalleService carritoHistoricoDetalleService;

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

    private MockMvc restCarritoHistoricoDetalleMockMvc;

    private CarritoHistoricoDetalle carritoHistoricoDetalle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarritoHistoricoDetalleResource carritoHistoricoDetalleResource = new CarritoHistoricoDetalleResource(carritoHistoricoDetalleService);
        this.restCarritoHistoricoDetalleMockMvc = MockMvcBuilders.standaloneSetup(carritoHistoricoDetalleResource)
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
    public static CarritoHistoricoDetalle createEntity(EntityManager em) {
        CarritoHistoricoDetalle carritoHistoricoDetalle = new CarritoHistoricoDetalle()
            .cantidad(DEFAULT_CANTIDAD)
            .precio(DEFAULT_PRECIO);
        return carritoHistoricoDetalle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarritoHistoricoDetalle createUpdatedEntity(EntityManager em) {
        CarritoHistoricoDetalle carritoHistoricoDetalle = new CarritoHistoricoDetalle()
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);
        return carritoHistoricoDetalle;
    }

    @BeforeEach
    public void initTest() {
        carritoHistoricoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarritoHistoricoDetalle() throws Exception {
        int databaseSizeBeforeCreate = carritoHistoricoDetalleRepository.findAll().size();

        // Create the CarritoHistoricoDetalle
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = carritoHistoricoDetalleMapper.toDto(carritoHistoricoDetalle);
        restCarritoHistoricoDetalleMockMvc.perform(post("/api/carrito-historico-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoHistoricoDetalleDTO)))
            .andExpect(status().isCreated());

        // Validate the CarritoHistoricoDetalle in the database
        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        CarritoHistoricoDetalle testCarritoHistoricoDetalle = carritoHistoricoDetalleList.get(carritoHistoricoDetalleList.size() - 1);
        assertThat(testCarritoHistoricoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testCarritoHistoricoDetalle.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createCarritoHistoricoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carritoHistoricoDetalleRepository.findAll().size();

        // Create the CarritoHistoricoDetalle with an existing ID
        carritoHistoricoDetalle.setId(1L);
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = carritoHistoricoDetalleMapper.toDto(carritoHistoricoDetalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarritoHistoricoDetalleMockMvc.perform(post("/api/carrito-historico-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoHistoricoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarritoHistoricoDetalle in the database
        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = carritoHistoricoDetalleRepository.findAll().size();
        // set the field null
        carritoHistoricoDetalle.setCantidad(null);

        // Create the CarritoHistoricoDetalle, which fails.
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = carritoHistoricoDetalleMapper.toDto(carritoHistoricoDetalle);

        restCarritoHistoricoDetalleMockMvc.perform(post("/api/carrito-historico-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoHistoricoDetalleDTO)))
            .andExpect(status().isBadRequest());

        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarritoHistoricoDetalles() throws Exception {
        // Initialize the database
        carritoHistoricoDetalleRepository.saveAndFlush(carritoHistoricoDetalle);

        // Get all the carritoHistoricoDetalleList
        restCarritoHistoricoDetalleMockMvc.perform(get("/api/carrito-historico-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carritoHistoricoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getCarritoHistoricoDetalle() throws Exception {
        // Initialize the database
        carritoHistoricoDetalleRepository.saveAndFlush(carritoHistoricoDetalle);

        // Get the carritoHistoricoDetalle
        restCarritoHistoricoDetalleMockMvc.perform(get("/api/carrito-historico-detalles/{id}", carritoHistoricoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carritoHistoricoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarritoHistoricoDetalle() throws Exception {
        // Get the carritoHistoricoDetalle
        restCarritoHistoricoDetalleMockMvc.perform(get("/api/carrito-historico-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarritoHistoricoDetalle() throws Exception {
        // Initialize the database
        carritoHistoricoDetalleRepository.saveAndFlush(carritoHistoricoDetalle);

        int databaseSizeBeforeUpdate = carritoHistoricoDetalleRepository.findAll().size();

        // Update the carritoHistoricoDetalle
        CarritoHistoricoDetalle updatedCarritoHistoricoDetalle = carritoHistoricoDetalleRepository.findById(carritoHistoricoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedCarritoHistoricoDetalle are not directly saved in db
        em.detach(updatedCarritoHistoricoDetalle);
        updatedCarritoHistoricoDetalle
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = carritoHistoricoDetalleMapper.toDto(updatedCarritoHistoricoDetalle);

        restCarritoHistoricoDetalleMockMvc.perform(put("/api/carrito-historico-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoHistoricoDetalleDTO)))
            .andExpect(status().isOk());

        // Validate the CarritoHistoricoDetalle in the database
        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeUpdate);
        CarritoHistoricoDetalle testCarritoHistoricoDetalle = carritoHistoricoDetalleList.get(carritoHistoricoDetalleList.size() - 1);
        assertThat(testCarritoHistoricoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testCarritoHistoricoDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCarritoHistoricoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = carritoHistoricoDetalleRepository.findAll().size();

        // Create the CarritoHistoricoDetalle
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = carritoHistoricoDetalleMapper.toDto(carritoHistoricoDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarritoHistoricoDetalleMockMvc.perform(put("/api/carrito-historico-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoHistoricoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarritoHistoricoDetalle in the database
        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarritoHistoricoDetalle() throws Exception {
        // Initialize the database
        carritoHistoricoDetalleRepository.saveAndFlush(carritoHistoricoDetalle);

        int databaseSizeBeforeDelete = carritoHistoricoDetalleRepository.findAll().size();

        // Delete the carritoHistoricoDetalle
        restCarritoHistoricoDetalleMockMvc.perform(delete("/api/carrito-historico-detalles/{id}", carritoHistoricoDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarritoHistoricoDetalle> carritoHistoricoDetalleList = carritoHistoricoDetalleRepository.findAll();
        assertThat(carritoHistoricoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoHistoricoDetalle.class);
        CarritoHistoricoDetalle carritoHistoricoDetalle1 = new CarritoHistoricoDetalle();
        carritoHistoricoDetalle1.setId(1L);
        CarritoHistoricoDetalle carritoHistoricoDetalle2 = new CarritoHistoricoDetalle();
        carritoHistoricoDetalle2.setId(carritoHistoricoDetalle1.getId());
        assertThat(carritoHistoricoDetalle1).isEqualTo(carritoHistoricoDetalle2);
        carritoHistoricoDetalle2.setId(2L);
        assertThat(carritoHistoricoDetalle1).isNotEqualTo(carritoHistoricoDetalle2);
        carritoHistoricoDetalle1.setId(null);
        assertThat(carritoHistoricoDetalle1).isNotEqualTo(carritoHistoricoDetalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoHistoricoDetalleDTO.class);
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO1 = new CarritoHistoricoDetalleDTO();
        carritoHistoricoDetalleDTO1.setId(1L);
        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO2 = new CarritoHistoricoDetalleDTO();
        assertThat(carritoHistoricoDetalleDTO1).isNotEqualTo(carritoHistoricoDetalleDTO2);
        carritoHistoricoDetalleDTO2.setId(carritoHistoricoDetalleDTO1.getId());
        assertThat(carritoHistoricoDetalleDTO1).isEqualTo(carritoHistoricoDetalleDTO2);
        carritoHistoricoDetalleDTO2.setId(2L);
        assertThat(carritoHistoricoDetalleDTO1).isNotEqualTo(carritoHistoricoDetalleDTO2);
        carritoHistoricoDetalleDTO1.setId(null);
        assertThat(carritoHistoricoDetalleDTO1).isNotEqualTo(carritoHistoricoDetalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carritoHistoricoDetalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carritoHistoricoDetalleMapper.fromId(null)).isNull();
    }
}
