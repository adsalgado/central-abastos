package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.Pedido;
import mx.com.sharkit.repository.PedidoRepository;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.dto.PedidoDTO;
import mx.com.sharkit.service.mapper.PedidoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mx.com.sharkit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PedidoResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
public class PedidoResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_SIN_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SIN_IVA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SIN_IVA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_COMISION_TRANSPORTISTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_COMISION_TRANSPORTISTA = new BigDecimal(2);
    private static final BigDecimal SMALLER_COMISION_TRANSPORTISTA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_COMISION_PREPARADOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_COMISION_PREPARADOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_COMISION_PREPARADOR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_FECHA_PEDIDO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PEDIDO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_PEDIDO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_PREPARACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PREPARACION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_PREPARACION = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_COBRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COBRO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_COBRO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ENTREGA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoService pedidoService;

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

    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoResource pedidoResource = new PedidoResource(pedidoService);
        this.restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
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
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .totalSinIva(DEFAULT_TOTAL_SIN_IVA)
            .comisionTransportista(DEFAULT_COMISION_TRANSPORTISTA)
            .comisionPreparador(DEFAULT_COMISION_PREPARADOR)
            .total(DEFAULT_TOTAL)
            .fechaPedido(DEFAULT_FECHA_PEDIDO)
            .fechaPreparacion(DEFAULT_FECHA_PREPARACION)
            .fechaCobro(DEFAULT_FECHA_COBRO)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA);
        return pedido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createUpdatedEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .totalSinIva(UPDATED_TOTAL_SIN_IVA)
            .comisionTransportista(UPDATED_COMISION_TRANSPORTISTA)
            .comisionPreparador(UPDATED_COMISION_PREPARADOR)
            .total(UPDATED_TOTAL)
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaPreparacion(UPDATED_FECHA_PREPARACION)
            .fechaCobro(UPDATED_FECHA_COBRO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA);
        return pedido;
    }

    @BeforeEach
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getTotalSinIva()).isEqualTo(DEFAULT_TOTAL_SIN_IVA);
        assertThat(testPedido.getComisionTransportista()).isEqualTo(DEFAULT_COMISION_TRANSPORTISTA);
        assertThat(testPedido.getComisionPreparador()).isEqualTo(DEFAULT_COMISION_PREPARADOR);
        assertThat(testPedido.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPedido.getFechaPedido()).isEqualTo(DEFAULT_FECHA_PEDIDO);
        assertThat(testPedido.getFechaPreparacion()).isEqualTo(DEFAULT_FECHA_PREPARACION);
        assertThat(testPedido.getFechaCobro()).isEqualTo(DEFAULT_FECHA_COBRO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    public void createPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido with an existing ID
        pedido.setId(1L);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalSinIva").value(hasItem(DEFAULT_TOTAL_SIN_IVA.intValue())))
            .andExpect(jsonPath("$.[*].comisionTransportista").value(hasItem(DEFAULT_COMISION_TRANSPORTISTA.intValue())))
            .andExpect(jsonPath("$.[*].comisionPreparador").value(hasItem(DEFAULT_COMISION_PREPARADOR.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].fechaPedido").value(hasItem(DEFAULT_FECHA_PEDIDO.toString())))
            .andExpect(jsonPath("$.[*].fechaPreparacion").value(hasItem(DEFAULT_FECHA_PREPARACION.toString())))
            .andExpect(jsonPath("$.[*].fechaCobro").value(hasItem(DEFAULT_FECHA_COBRO.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())));
    }
    
    @Test
    @Transactional
    public void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().intValue()))
            .andExpect(jsonPath("$.totalSinIva").value(DEFAULT_TOTAL_SIN_IVA.intValue()))
            .andExpect(jsonPath("$.comisionTransportista").value(DEFAULT_COMISION_TRANSPORTISTA.intValue()))
            .andExpect(jsonPath("$.comisionPreparador").value(DEFAULT_COMISION_PREPARADOR.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.fechaPedido").value(DEFAULT_FECHA_PEDIDO.toString()))
            .andExpect(jsonPath("$.fechaPreparacion").value(DEFAULT_FECHA_PREPARACION.toString()))
            .andExpect(jsonPath("$.fechaCobro").value(DEFAULT_FECHA_COBRO.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .totalSinIva(UPDATED_TOTAL_SIN_IVA)
            .comisionTransportista(UPDATED_COMISION_TRANSPORTISTA)
            .comisionPreparador(UPDATED_COMISION_PREPARADOR)
            .total(UPDATED_TOTAL)
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaPreparacion(UPDATED_FECHA_PREPARACION)
            .fechaCobro(UPDATED_FECHA_COBRO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(updatedPedido);

        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getTotalSinIva()).isEqualTo(UPDATED_TOTAL_SIN_IVA);
        assertThat(testPedido.getComisionTransportista()).isEqualTo(UPDATED_COMISION_TRANSPORTISTA);
        assertThat(testPedido.getComisionPreparador()).isEqualTo(UPDATED_COMISION_PREPARADOR);
        assertThat(testPedido.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPedido.getFechaPedido()).isEqualTo(UPDATED_FECHA_PEDIDO);
        assertThat(testPedido.getFechaPreparacion()).isEqualTo(UPDATED_FECHA_PREPARACION);
        assertThat(testPedido.getFechaCobro()).isEqualTo(UPDATED_FECHA_COBRO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Delete the pedido
        restPedidoMockMvc.perform(delete("/api/pedidos/{id}", pedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pedido.class);
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Pedido pedido2 = new Pedido();
        pedido2.setId(pedido1.getId());
        assertThat(pedido1).isEqualTo(pedido2);
        pedido2.setId(2L);
        assertThat(pedido1).isNotEqualTo(pedido2);
        pedido1.setId(null);
        assertThat(pedido1).isNotEqualTo(pedido2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoDTO.class);
        PedidoDTO pedidoDTO1 = new PedidoDTO();
        pedidoDTO1.setId(1L);
        PedidoDTO pedidoDTO2 = new PedidoDTO();
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO2.setId(pedidoDTO1.getId());
        assertThat(pedidoDTO1).isEqualTo(pedidoDTO2);
        pedidoDTO2.setId(2L);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO1.setId(null);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pedidoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pedidoMapper.fromId(null)).isNull();
    }
}
