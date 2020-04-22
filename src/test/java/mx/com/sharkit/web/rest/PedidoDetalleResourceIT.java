package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.PedidoDetalle;
import mx.com.sharkit.repository.PedidoDetalleRepository;
import mx.com.sharkit.service.PedidoDetalleService;
import mx.com.sharkit.service.dto.PedidoDetalleDTO;
import mx.com.sharkit.service.mapper.PedidoDetalleMapper;
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
 * Integration tests for the {@link PedidoDetalleResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class PedidoDetalleResourceIT {

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);
    private static final BigDecimal SMALLER_CANTIDAD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SIN_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SIN_IVA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SIN_IVA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL = new BigDecimal(1 - 1);

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoDetalleMapper pedidoDetalleMapper;

    @Autowired
    private PedidoDetalleService pedidoDetalleService;

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

    private MockMvc restPedidoDetalleMockMvc;

    private PedidoDetalle pedidoDetalle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoDetalleResource pedidoDetalleResource = new PedidoDetalleResource(pedidoDetalleService);
        this.restPedidoDetalleMockMvc = MockMvcBuilders.standaloneSetup(pedidoDetalleResource)
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
    public static PedidoDetalle createEntity(EntityManager em) {
        PedidoDetalle pedidoDetalle = new PedidoDetalle()
            .cantidad(DEFAULT_CANTIDAD)
            .totalSinIva(DEFAULT_TOTAL_SIN_IVA)
            .total(DEFAULT_TOTAL);
        return pedidoDetalle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoDetalle createUpdatedEntity(EntityManager em) {
        PedidoDetalle pedidoDetalle = new PedidoDetalle()
            .cantidad(UPDATED_CANTIDAD)
            .totalSinIva(UPDATED_TOTAL_SIN_IVA)
            .total(UPDATED_TOTAL);
        return pedidoDetalle;
    }

    @BeforeEach
    public void initTest() {
        pedidoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedidoDetalle() throws Exception {
        int databaseSizeBeforeCreate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle
        PedidoDetalleDTO pedidoDetalleDTO = pedidoDetalleMapper.toDto(pedidoDetalle);
        restPedidoDetalleMockMvc.perform(post("/api/pedido-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalleDTO)))
            .andExpect(status().isCreated());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        PedidoDetalle testPedidoDetalle = pedidoDetalleList.get(pedidoDetalleList.size() - 1);
        assertThat(testPedidoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testPedidoDetalle.getTotalSinIva()).isEqualTo(DEFAULT_TOTAL_SIN_IVA);
        assertThat(testPedidoDetalle.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createPedidoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle with an existing ID
        pedidoDetalle.setId(1L);
        PedidoDetalleDTO pedidoDetalleDTO = pedidoDetalleMapper.toDto(pedidoDetalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoDetalleMockMvc.perform(post("/api/pedido-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPedidoDetalles() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        // Get all the pedidoDetalleList
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedidoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].totalSinIva").value(hasItem(DEFAULT_TOTAL_SIN_IVA.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getPedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        // Get the pedidoDetalle
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles/{id}", pedidoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedidoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.totalSinIva").value(DEFAULT_TOTAL_SIN_IVA.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPedidoDetalle() throws Exception {
        // Get the pedidoDetalle
        restPedidoDetalleMockMvc.perform(get("/api/pedido-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        int databaseSizeBeforeUpdate = pedidoDetalleRepository.findAll().size();

        // Update the pedidoDetalle
        PedidoDetalle updatedPedidoDetalle = pedidoDetalleRepository.findById(pedidoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedPedidoDetalle are not directly saved in db
        em.detach(updatedPedidoDetalle);
        updatedPedidoDetalle
            .cantidad(UPDATED_CANTIDAD)
            .totalSinIva(UPDATED_TOTAL_SIN_IVA)
            .total(UPDATED_TOTAL);
        PedidoDetalleDTO pedidoDetalleDTO = pedidoDetalleMapper.toDto(updatedPedidoDetalle);

        restPedidoDetalleMockMvc.perform(put("/api/pedido-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalleDTO)))
            .andExpect(status().isOk());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeUpdate);
        PedidoDetalle testPedidoDetalle = pedidoDetalleList.get(pedidoDetalleList.size() - 1);
        assertThat(testPedidoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPedidoDetalle.getTotalSinIva()).isEqualTo(UPDATED_TOTAL_SIN_IVA);
        assertThat(testPedidoDetalle.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPedidoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = pedidoDetalleRepository.findAll().size();

        // Create the PedidoDetalle
        PedidoDetalleDTO pedidoDetalleDTO = pedidoDetalleMapper.toDto(pedidoDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoDetalleMockMvc.perform(put("/api/pedido-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoDetalle in the database
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedidoDetalle() throws Exception {
        // Initialize the database
        pedidoDetalleRepository.saveAndFlush(pedidoDetalle);

        int databaseSizeBeforeDelete = pedidoDetalleRepository.findAll().size();

        // Delete the pedidoDetalle
        restPedidoDetalleMockMvc.perform(delete("/api/pedido-detalles/{id}", pedidoDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PedidoDetalle> pedidoDetalleList = pedidoDetalleRepository.findAll();
        assertThat(pedidoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoDetalle.class);
        PedidoDetalle pedidoDetalle1 = new PedidoDetalle();
        pedidoDetalle1.setId(1L);
        PedidoDetalle pedidoDetalle2 = new PedidoDetalle();
        pedidoDetalle2.setId(pedidoDetalle1.getId());
        assertThat(pedidoDetalle1).isEqualTo(pedidoDetalle2);
        pedidoDetalle2.setId(2L);
        assertThat(pedidoDetalle1).isNotEqualTo(pedidoDetalle2);
        pedidoDetalle1.setId(null);
        assertThat(pedidoDetalle1).isNotEqualTo(pedidoDetalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoDetalleDTO.class);
        PedidoDetalleDTO pedidoDetalleDTO1 = new PedidoDetalleDTO();
        pedidoDetalleDTO1.setId(1L);
        PedidoDetalleDTO pedidoDetalleDTO2 = new PedidoDetalleDTO();
        assertThat(pedidoDetalleDTO1).isNotEqualTo(pedidoDetalleDTO2);
        pedidoDetalleDTO2.setId(pedidoDetalleDTO1.getId());
        assertThat(pedidoDetalleDTO1).isEqualTo(pedidoDetalleDTO2);
        pedidoDetalleDTO2.setId(2L);
        assertThat(pedidoDetalleDTO1).isNotEqualTo(pedidoDetalleDTO2);
        pedidoDetalleDTO1.setId(null);
        assertThat(pedidoDetalleDTO1).isNotEqualTo(pedidoDetalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pedidoDetalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pedidoDetalleMapper.fromId(null)).isNull();
    }
}
