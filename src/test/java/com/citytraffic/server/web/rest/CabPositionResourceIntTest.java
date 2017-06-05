package com.citytraffic.server.web.rest;

import com.citytraffic.server.CityTrafficServerApp;

import com.citytraffic.server.domain.CabPosition;
import com.citytraffic.server.repository.CabPositionRepository;
import com.citytraffic.server.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CabPositionResource REST controller.
 *
 * @see CabPositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityTrafficServerApp.class)
public class CabPositionResourceIntTest {

    private static final String DEFAULT_QR = "AAAAAAAAAA";
    private static final String UPDATED_QR = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCURACY = "AAAAAAAAAA";
    private static final String UPDATED_ACCURACY = "BBBBBBBBBB";

    private static final String DEFAULT_SPEED = "AAAAAAAAAA";
    private static final String UPDATED_SPEED = "BBBBBBBBBB";

    @Autowired
    private CabPositionRepository cabPositionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCabPositionMockMvc;

    private CabPosition cabPosition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CabPositionResource cabPositionResource = new CabPositionResource(cabPositionRepository);
        this.restCabPositionMockMvc = MockMvcBuilders.standaloneSetup(cabPositionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CabPosition createEntity(EntityManager em) {
        CabPosition cabPosition = new CabPosition()
            .qr(DEFAULT_QR)
            .time(DEFAULT_TIME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .accuracy(DEFAULT_ACCURACY)
            .speed(DEFAULT_SPEED);
        return cabPosition;
    }

    @Before
    public void initTest() {
        cabPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCabPosition() throws Exception {
        int databaseSizeBeforeCreate = cabPositionRepository.findAll().size();

        // Create the CabPosition
        restCabPositionMockMvc.perform(post("/api/cab-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabPosition)))
            .andExpect(status().isCreated());

        // Validate the CabPosition in the database
        List<CabPosition> cabPositionList = cabPositionRepository.findAll();
        assertThat(cabPositionList).hasSize(databaseSizeBeforeCreate + 1);
        CabPosition testCabPosition = cabPositionList.get(cabPositionList.size() - 1);
        assertThat(testCabPosition.getQr()).isEqualTo(DEFAULT_QR);
        assertThat(testCabPosition.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCabPosition.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCabPosition.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCabPosition.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
        assertThat(testCabPosition.getSpeed()).isEqualTo(DEFAULT_SPEED);
    }

    @Test
    @Transactional
    public void createCabPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cabPositionRepository.findAll().size();

        // Create the CabPosition with an existing ID
        cabPosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCabPositionMockMvc.perform(post("/api/cab-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabPosition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CabPosition> cabPositionList = cabPositionRepository.findAll();
        assertThat(cabPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCabPositions() throws Exception {
        // Initialize the database
        cabPositionRepository.saveAndFlush(cabPosition);

        // Get all the cabPositionList
        restCabPositionMockMvc.perform(get("/api/cab-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cabPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].qr").value(hasItem(DEFAULT_QR.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY.toString())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.toString())));
    }

    @Test
    @Transactional
    public void getCabPosition() throws Exception {
        // Initialize the database
        cabPositionRepository.saveAndFlush(cabPosition);

        // Get the cabPosition
        restCabPositionMockMvc.perform(get("/api/cab-positions/{id}", cabPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cabPosition.getId().intValue()))
            .andExpect(jsonPath("$.qr").value(DEFAULT_QR.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.accuracy").value(DEFAULT_ACCURACY.toString()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCabPosition() throws Exception {
        // Get the cabPosition
        restCabPositionMockMvc.perform(get("/api/cab-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCabPosition() throws Exception {
        // Initialize the database
        cabPositionRepository.saveAndFlush(cabPosition);
        int databaseSizeBeforeUpdate = cabPositionRepository.findAll().size();

        // Update the cabPosition
        CabPosition updatedCabPosition = cabPositionRepository.findOne(cabPosition.getId());
        updatedCabPosition
            .qr(UPDATED_QR)
            .time(UPDATED_TIME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .accuracy(UPDATED_ACCURACY)
            .speed(UPDATED_SPEED);

        restCabPositionMockMvc.perform(put("/api/cab-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCabPosition)))
            .andExpect(status().isOk());

        // Validate the CabPosition in the database
        List<CabPosition> cabPositionList = cabPositionRepository.findAll();
        assertThat(cabPositionList).hasSize(databaseSizeBeforeUpdate);
        CabPosition testCabPosition = cabPositionList.get(cabPositionList.size() - 1);
        assertThat(testCabPosition.getQr()).isEqualTo(UPDATED_QR);
        assertThat(testCabPosition.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCabPosition.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCabPosition.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCabPosition.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testCabPosition.getSpeed()).isEqualTo(UPDATED_SPEED);
    }

    @Test
    @Transactional
    public void updateNonExistingCabPosition() throws Exception {
        int databaseSizeBeforeUpdate = cabPositionRepository.findAll().size();

        // Create the CabPosition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCabPositionMockMvc.perform(put("/api/cab-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabPosition)))
            .andExpect(status().isCreated());

        // Validate the CabPosition in the database
        List<CabPosition> cabPositionList = cabPositionRepository.findAll();
        assertThat(cabPositionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCabPosition() throws Exception {
        // Initialize the database
        cabPositionRepository.saveAndFlush(cabPosition);
        int databaseSizeBeforeDelete = cabPositionRepository.findAll().size();

        // Get the cabPosition
        restCabPositionMockMvc.perform(delete("/api/cab-positions/{id}", cabPosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CabPosition> cabPositionList = cabPositionRepository.findAll();
        assertThat(cabPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CabPosition.class);
    }
}
