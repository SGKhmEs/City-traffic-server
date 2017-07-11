package com.citytraffic.server.web.rest;

import com.citytraffic.server.CityTrafficServerApp;

import com.citytraffic.server.domain.GPS;
import com.citytraffic.server.repository.GPSRepository;
import com.citytraffic.server.service.GPSService;
import com.citytraffic.server.service.dto.GPSDTO;
import com.citytraffic.server.service.mapper.GPSMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GPSResource REST controller.
 *
 * @see GPSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityTrafficServerApp.class)
public class GPSResourceIntTest {

    private static final LocalDate DEFAULT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCURANCY = 1;
    private static final Integer UPDATED_ACCURANCY = 2;

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer UPDATED_SPEED = 2;

    @Autowired
    private GPSRepository gPSRepository;

    @Autowired
    private GPSMapper gPSMapper;

    @Autowired
    private GPSService gPSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGPSMockMvc;

    private GPS gPS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GPSResource gPSResource = new GPSResource(gPSService);
        this.restGPSMockMvc = MockMvcBuilders.standaloneSetup(gPSResource)
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
    public static GPS createEntity(EntityManager em) {
        GPS gPS = new GPS()
            .time(DEFAULT_TIME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .accurancy(DEFAULT_ACCURANCY)
            .speed(DEFAULT_SPEED);
        return gPS;
    }

    @Before
    public void initTest() {
        gPS = createEntity(em);
    }

    @Test
    @Transactional
    public void createGPS() throws Exception {
        int databaseSizeBeforeCreate = gPSRepository.findAll().size();

        // Create the GPS
        GPSDTO gPSDTO = gPSMapper.toDto(gPS);
        restGPSMockMvc.perform(post("/api/g-ps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSDTO)))
            .andExpect(status().isCreated());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeCreate + 1);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testGPS.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGPS.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGPS.getAccurancy()).isEqualTo(DEFAULT_ACCURANCY);
        assertThat(testGPS.getSpeed()).isEqualTo(DEFAULT_SPEED);
    }

    @Test
    @Transactional
    public void createGPSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gPSRepository.findAll().size();

        // Create the GPS with an existing ID
        gPS.setId(1L);
        GPSDTO gPSDTO = gPSMapper.toDto(gPS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGPSMockMvc.perform(post("/api/g-ps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        // Get all the gPSList
        restGPSMockMvc.perform(get("/api/g-ps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].accurancy").value(hasItem(DEFAULT_ACCURANCY)))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)));
    }

    @Test
    @Transactional
    public void getGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        // Get the gPS
        restGPSMockMvc.perform(get("/api/g-ps/{id}", gPS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gPS.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.accurancy").value(DEFAULT_ACCURANCY))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED));
    }

    @Test
    @Transactional
    public void getNonExistingGPS() throws Exception {
        // Get the gPS
        restGPSMockMvc.perform(get("/api/g-ps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();

        // Update the gPS
        GPS updatedGPS = gPSRepository.findOne(gPS.getId());
        updatedGPS
            .time(UPDATED_TIME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .accurancy(UPDATED_ACCURANCY)
            .speed(UPDATED_SPEED);
        GPSDTO gPSDTO = gPSMapper.toDto(updatedGPS);

        restGPSMockMvc.perform(put("/api/g-ps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSDTO)))
            .andExpect(status().isOk());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testGPS.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGPS.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGPS.getAccurancy()).isEqualTo(UPDATED_ACCURANCY);
        assertThat(testGPS.getSpeed()).isEqualTo(UPDATED_SPEED);
    }

    @Test
    @Transactional
    public void updateNonExistingGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();

        // Create the GPS
        GPSDTO gPSDTO = gPSMapper.toDto(gPS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGPSMockMvc.perform(put("/api/g-ps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSDTO)))
            .andExpect(status().isCreated());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);
        int databaseSizeBeforeDelete = gPSRepository.findAll().size();

        // Get the gPS
        restGPSMockMvc.perform(delete("/api/g-ps/{id}", gPS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPS.class);
        GPS gPS1 = new GPS();
        gPS1.setId(1L);
        GPS gPS2 = new GPS();
        gPS2.setId(gPS1.getId());
        assertThat(gPS1).isEqualTo(gPS2);
        gPS2.setId(2L);
        assertThat(gPS1).isNotEqualTo(gPS2);
        gPS1.setId(null);
        assertThat(gPS1).isNotEqualTo(gPS2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPSDTO.class);
        GPSDTO gPSDTO1 = new GPSDTO();
        gPSDTO1.setId(1L);
        GPSDTO gPSDTO2 = new GPSDTO();
        assertThat(gPSDTO1).isNotEqualTo(gPSDTO2);
        gPSDTO2.setId(gPSDTO1.getId());
        assertThat(gPSDTO1).isEqualTo(gPSDTO2);
        gPSDTO2.setId(2L);
        assertThat(gPSDTO1).isNotEqualTo(gPSDTO2);
        gPSDTO1.setId(null);
        assertThat(gPSDTO1).isNotEqualTo(gPSDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gPSMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gPSMapper.fromId(null)).isNull();
    }
}
