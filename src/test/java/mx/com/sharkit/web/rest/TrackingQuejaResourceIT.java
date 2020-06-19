package mx.com.sharkit.web.rest;

import mx.com.sharkit.AbastosApp;
import mx.com.sharkit.domain.TrackingQueja;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.repository.TrackingQuejaRepository;
import mx.com.sharkit.service.TrackingQuejaService;
import mx.com.sharkit.service.dto.TrackingQuejaDTO;
import mx.com.sharkit.service.mapper.TrackingQuejaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrackingQuejaResource} REST controller.
 */
@SpringBootTest(classes = AbastosApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrackingQuejaResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_QUEJA_ID = 1L;
    private static final Long UPDATED_QUEJA_ID = 2L;

    private static final String DEFAULT_TRACKING_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRACKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRACKING_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TrackingQuejaRepository trackingQuejaRepository;

    @Autowired
    private TrackingQuejaMapper trackingQuejaMapper;

    @Autowired
    private TrackingQuejaService trackingQuejaService;

    @Autowired
    private EntityManager em;
    
   // private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc restTrackingQuejaMockMvc;

    private TrackingQueja trackingQueja;


    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackingQueja createEntity(EntityManager em) {
        TrackingQueja trackingQueja = new TrackingQueja();
//            .userId(DEFAULT_USER_ID)
//            .quejaId(DEFAULT_QUEJA_ID)
//            .trackingMessage(DEFAULT_TRACKING_MESSAGE)
//            .trackingDate(DEFAULT_TRACKING_DATE);
        // Add required entity
      //  User user = UserResourceIT.createEntity(em);
       // em.persist(user);
       // em.flush();
       // trackingQueja.setUser(user);
        return trackingQueja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackingQueja createUpdatedEntity(EntityManager em) {
        TrackingQueja trackingQueja = new TrackingQueja();
//            .userId(UPDATED_USER_ID)
//            .quejaId(UPDATED_QUEJA_ID)
//            .trackingMessage(UPDATED_TRACKING_MESSAGE)
//            .trackingDate(UPDATED_TRACKING_DATE);
        // Add required entity
        
//        User user = UserResourceIT.createEntity(em);
//        em.persist(user);
//        em.flush();
//        trackingQueja.setUser(user);
        return trackingQueja;
    }

    @BeforeEach
    public void initTest() {
        trackingQueja = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrackingQueja() throws Exception {
        int databaseSizeBeforeCreate = trackingQuejaRepository.findAll().size();
        // Create the TrackingQueja
        TrackingQuejaDTO trackingQuejaDTO = trackingQuejaMapper.toDto(trackingQueja);
        restTrackingQuejaMockMvc.perform(post("/api/tracking-quejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingQuejaDTO)))
            .andExpect(status().isCreated());

        // Validate the TrackingQueja in the database
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeCreate + 1);
        TrackingQueja testTrackingQueja = trackingQuejaList.get(trackingQuejaList.size() - 1);
        assertThat(testTrackingQueja.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTrackingQueja.getQuejaId()).isEqualTo(DEFAULT_QUEJA_ID);
        assertThat(testTrackingQueja.getTrackingMessage()).isEqualTo(DEFAULT_TRACKING_MESSAGE);
        assertThat(testTrackingQueja.getTrackingDate()).isEqualTo(DEFAULT_TRACKING_DATE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testTrackingQueja.getId()).isEqualTo(testTrackingQueja.getUser().getId());
    }

    @Test
    @Transactional
    public void createTrackingQuejaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackingQuejaRepository.findAll().size();

        // Create the TrackingQueja with an existing ID
        trackingQueja.setId(1L);
        TrackingQuejaDTO trackingQuejaDTO = trackingQuejaMapper.toDto(trackingQueja);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackingQuejaMockMvc.perform(post("/api/tracking-quejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingQuejaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingQueja in the database
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateTrackingQuejaMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        trackingQuejaRepository.saveAndFlush(trackingQueja);
        int databaseSizeBeforeCreate = trackingQuejaRepository.findAll().size();

        // Add a new parent entity
        //User user = UserResourceIT.createEntity(em);
       // em.persist(user);
        em.flush();

        // Load the trackingQueja
        TrackingQueja updatedTrackingQueja = trackingQuejaRepository.findById(trackingQueja.getId()).get();
        // Disconnect from session so that the updates on updatedTrackingQueja are not directly saved in db
        em.detach(updatedTrackingQueja);

        // Update the User with new association value
     //   updatedTrackingQueja.setUser(user);
        TrackingQuejaDTO updatedTrackingQuejaDTO = trackingQuejaMapper.toDto(updatedTrackingQueja);

        // Update the entity
        restTrackingQuejaMockMvc.perform(put("/api/tracking-quejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrackingQuejaDTO)))
            .andExpect(status().isOk());

        // Validate the TrackingQueja in the database
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeCreate);
        TrackingQueja testTrackingQueja = trackingQuejaList.get(trackingQuejaList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testTrackingQueja.getId()).isEqualTo(testTrackingQueja.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllTrackingQuejas() throws Exception {
        // Initialize the database
        trackingQuejaRepository.saveAndFlush(trackingQueja);

        // Get all the trackingQuejaList
        restTrackingQuejaMockMvc.perform(get("/api/tracking-quejas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackingQueja.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].quejaId").value(hasItem(DEFAULT_QUEJA_ID.intValue())))
            .andExpect(jsonPath("$.[*].trackingMessage").value(hasItem(DEFAULT_TRACKING_MESSAGE)))
            .andExpect(jsonPath("$.[*].trackingDate").value(hasItem(DEFAULT_TRACKING_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTrackingQueja() throws Exception {
        // Initialize the database
        trackingQuejaRepository.saveAndFlush(trackingQueja);

        // Get the trackingQueja
        restTrackingQuejaMockMvc.perform(get("/api/tracking-quejas/{id}", trackingQueja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trackingQueja.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.quejaId").value(DEFAULT_QUEJA_ID.intValue()))
            .andExpect(jsonPath("$.trackingMessage").value(DEFAULT_TRACKING_MESSAGE))
            .andExpect(jsonPath("$.trackingDate").value(DEFAULT_TRACKING_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTrackingQueja() throws Exception {
        // Get the trackingQueja
        restTrackingQuejaMockMvc.perform(get("/api/tracking-quejas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrackingQueja() throws Exception {
        // Initialize the database
        trackingQuejaRepository.saveAndFlush(trackingQueja);

        int databaseSizeBeforeUpdate = trackingQuejaRepository.findAll().size();

        // Update the trackingQueja
        TrackingQueja updatedTrackingQueja = trackingQuejaRepository.findById(trackingQueja.getId()).get();
        // Disconnect from session so that the updates on updatedTrackingQueja are not directly saved in db
        em.detach(updatedTrackingQueja);
//        updatedTrackingQueja
//            .userId(UPDATED_USER_ID)
//            .quejaId(UPDATED_QUEJA_ID)
//            .trackingMessage(UPDATED_TRACKING_MESSAGE)
//            .trackingDate(UPDATED_TRACKING_DATE);
        TrackingQuejaDTO trackingQuejaDTO = trackingQuejaMapper.toDto(updatedTrackingQueja);

        restTrackingQuejaMockMvc.perform(put("/api/tracking-quejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingQuejaDTO)))
            .andExpect(status().isOk());

        // Validate the TrackingQueja in the database
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeUpdate);
        TrackingQueja testTrackingQueja = trackingQuejaList.get(trackingQuejaList.size() - 1);
        assertThat(testTrackingQueja.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTrackingQueja.getQuejaId()).isEqualTo(UPDATED_QUEJA_ID);
        assertThat(testTrackingQueja.getTrackingMessage()).isEqualTo(UPDATED_TRACKING_MESSAGE);
        assertThat(testTrackingQueja.getTrackingDate()).isEqualTo(UPDATED_TRACKING_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrackingQueja() throws Exception {
        int databaseSizeBeforeUpdate = trackingQuejaRepository.findAll().size();

        // Create the TrackingQueja
        TrackingQuejaDTO trackingQuejaDTO = trackingQuejaMapper.toDto(trackingQueja);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackingQuejaMockMvc.perform(put("/api/tracking-quejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingQuejaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingQueja in the database
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrackingQueja() throws Exception {
        // Initialize the database
        trackingQuejaRepository.saveAndFlush(trackingQueja);

        int databaseSizeBeforeDelete = trackingQuejaRepository.findAll().size();

        // Delete the trackingQueja
        restTrackingQuejaMockMvc.perform(delete("/api/tracking-quejas/{id}", trackingQueja.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrackingQueja> trackingQuejaList = trackingQuejaRepository.findAll();
        assertThat(trackingQuejaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
